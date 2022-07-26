 @VisibleForTesting
 static class LogStream implements org.apache.aurora.scheduler.log.Log.Stream {
 @VisibleForTesting
 static final class OpStats {
 private final String opName;
 private final SlidingStats timing;
 private final AtomicLong timeouts;
 private final AtomicLong failures;


 OpStats(String opName) {
 this.opName = MorePreconditions.checkNotBlank(opName);
 timing = new SlidingStats("scheduler_log_native_" + opName, "nanos");
 timeouts = exportLongStat("scheduler_log_native_%s_timeouts", opName);
 failures = exportLongStat("scheduler_log_native_%s_failures", opName);
      }


 private static AtomicLong exportLongStat(String template, Object... args) {
 return Stats.exportLong(String.format(template, args));
      }
    }


 private static final Function<Log.Entry, LogEntry> MESOS_ENTRY_TO_ENTRY =
 LogEntry::new;


 private final OpStats readStats = new OpStats("read");
 private final OpStats appendStats = new OpStats("append");
 private final OpStats truncateStats = new OpStats("truncate");
 private final AtomicLong entriesSkipped =
 Stats.exportLong("scheduler_log_native_native_entries_skipped");


 private final LogInterface log;


 private final ReaderInterface reader;
 private final long readTimeout;
 private final TimeUnit readTimeUnit;


 private final Provider<WriterInterface> writerFactory;
 private final long writeTimeout;
 private final TimeUnit writeTimeUnit;


 private final byte[] noopEntry;


 private final Lifecycle lifecycle;


 /**
     * The underlying writer to use for mutation operations.  This field has three states:
     * <ul>
     *   <li>present: the writer is active and available for use</li>
     *   <li>absent: the writer has not yet been initialized (initialization is lazy)</li>
     *   <li>{@code null}: the writer has suffered a fatal error and no further operations may
     *       be performed.</li>
     * </ul>
     * When {@code true}, indicates that the log has suffered a fatal error and no further
     * operations may be performed.
     */
 @Nullable private Optional<WriterInterface> writer = Optional.empty();


 LogStream(
 LogInterface log,
 ReaderInterface reader,
 Amount<Long, Time> readTimeout,
 Provider<WriterInterface> writerFactory,
 Amount<Long, Time> writeTimeout,
 byte[] noopEntry,
 Lifecycle lifecycle) {


 this.log = log;


 this.reader = reader;
 this.readTimeout = readTimeout.getValue();
 this.readTimeUnit = readTimeout.getUnit().getTimeUnit();


 this.writerFactory = writerFactory;
 this.writeTimeout = writeTimeout.getValue();
 this.writeTimeUnit = writeTimeout.getUnit().getTimeUnit();


 this.noopEntry = noopEntry;


 this.lifecycle = lifecycle;
    }


 @Override
 public Iterator<Entry> readAll() throws StreamAccessException {
 // TODO(John Sirois): Currently we must be the coordinator to ensure we get the 'full read'
 // of log entries expected by the users of the org.apache.aurora.scheduler.log.Log interface.
 // Switch to another method of ensuring this when it becomes available in mesos' log
 // interface.
 try {
 append(noopEntry);
      } catch (StreamAccessException e) {
 throw new StreamAccessException("Error writing noop prior to a read", e);
      }


 final Log.Position from = reader.beginning();
 final Log.Position to = end().unwrap();


 // Reading all the entries at once may cause large garbage collections. Instead, we
 // lazily read the entries one by one as they are requested.
 // TODO(Benjamin Hindman): Eventually replace this functionality with functionality
 // from the Mesos Log.
 return new UnmodifiableIterator<Entry>() {
 private long position = Longs.fromByteArray(from.identity());
 private final long endPosition = Longs.fromByteArray(to.identity());
 private Entry entry = null;


 @Override
 public boolean hasNext() {
 if (entry != null) {
 return true;
          }


 while (position <= endPosition) {
 long start = System.nanoTime();
 try {
 Log.Position p = log.position(Longs.toByteArray(position));
 LOG.debug("Reading position {} from the log", position);
 List<Log.Entry> entries = reader.read(p, p, readTimeout, readTimeUnit);


 // N.B. HACK! There is currently no way to "increment" a position. Until the Mesos
 // Log actually provides a way to "stream" the log, we approximate as much by
 // using longs via Log.Position.identity and Log.position.
 position++;


 // Reading positions in this way means it's possible that we get an "invalid" entry
 // (e.g., in the underlying log terminology this would be anything but an append)
 // which will be removed from the returned entries resulting in an empty list.
 // We skip these.
 if (entries.isEmpty()) {
 entriesSkipped.getAndIncrement();
              } else {
 entry = MESOS_ENTRY_TO_ENTRY.apply(Iterables.getOnlyElement(entries));
 return true;
              }
            } catch (TimeoutException e) {
 readStats.timeouts.getAndIncrement();
 throw new StreamAccessException("Timeout reading from log.", e);
            } catch (Log.OperationFailedException e) {
 readStats.failures.getAndIncrement();
 throw new StreamAccessException("Problem reading from log", e);
            } finally {
 readStats.timing.accumulate(System.nanoTime() - start);
            }
          }
 return false;
        }


 @Override
 public Entry next() {
 if (entry == null && !hasNext()) {
 throw new NoSuchElementException();
          }


 Entry result = requireNonNull(entry);
 entry = null;
 return result;
        }
      };
    }


 @Override
 public LogPosition append(final byte[] contents) throws StreamAccessException {
 requireNonNull(contents);


 Log.Position position = mutate(
 appendStats,
 logWriter -> logWriter.append(contents, writeTimeout, writeTimeUnit));
 return LogPosition.wrap(position);
    }


 @Timed("scheduler_log_native_truncate_before")
 @Override
 public void truncateBefore(org.apache.aurora.scheduler.log.Log.Position position)
 throws StreamAccessException {


 Preconditions.checkArgument(position instanceof LogPosition);


 final Log.Position before = ((LogPosition) position).unwrap();
 mutate(truncateStats, logWriter -> {
 logWriter.truncate(before, writeTimeout, writeTimeUnit);
 return null;
      });
    }


 private interface Mutation<T> {
 T apply(WriterInterface writer) throws TimeoutException, Log.WriterFailedException;
    }


 private StreamAccessException disableLog(AtomicLong stat, String message, Throwable cause) {
 stat.incrementAndGet();
 writer = null;
 lifecycle.shutdown();


 throw new StreamAccessException(message, cause);
    }


 private synchronized <T> T mutate(OpStats stats, Mutation<T> mutation) {
 if (writer == null) {
 throw new IllegalStateException("The log has encountered an error and cannot be used.");
      }


 long start = System.nanoTime();
 if (!writer.isPresent()) {
 writer = Optional.of(writerFactory.get());
      }
 try {
 return mutation.apply(writer.get());
      } catch (TimeoutException e) {
 throw disableLog(stats.timeouts, "Timeout performing log " + stats.opName, e);
      } catch (Log.WriterFailedException e) {
 throw disableLog(stats.failures, "Problem performing log" + stats.opName, e);
      } finally {
 stats.timing.accumulate(System.nanoTime() - start);
      }
    }


 private LogPosition end() {
 return LogPosition.wrap(reader.ending());
    }


 @VisibleForTesting
 static class LogPosition implements org.apache.aurora.scheduler.log.Log.Position {
 private final Log.Position underlying;


 LogPosition(Log.Position underlying) {
 this.underlying = underlying;
      }


 static LogPosition wrap(Log.Position position) {
 return new LogPosition(position);
      }


 Log.Position unwrap() {
 return underlying;
      }
    }


 private static class LogEntry implements org.apache.aurora.scheduler.log.Log.Entry {
 private final Log.Entry underlying;


 LogEntry(Log.Entry entry) {
 this.underlying = entry;
      }


 @Override
 public byte[] contents() {
 return underlying.data;
      }
    }
  }