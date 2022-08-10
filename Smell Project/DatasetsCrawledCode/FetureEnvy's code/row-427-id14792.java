 boolean increaseConnectionWindow(int amount) {
 List<Stream<?>> candidates = null;
 controllerLock.lock();
 try {
 int size = connectionWindowSize;
 size += amount;
 if (size < 0)
 return false;
 connectionWindowSize = size;
 if (debug.on())
 debug.log("Connection window size is now %d (amount added %d)",
 size, amount);


 // Notify waiting streams, until the new increased window size is
 // effectively exhausted.
 Iterator<Map.Entry<Integer,Map.Entry<Stream<?>,Integer>>> iter =
 pending.entrySet().iterator();


 while (iter.hasNext() && size > 0) {
 Map.Entry<Integer,Map.Entry<Stream<?>,Integer>> item = iter.next();
 Integer streamSize = streams.get(item.getKey());
 if (streamSize == null) {
 iter.remove();
                } else {
 Map.Entry<Stream<?>,Integer> e = item.getValue();
 int requestedAmount = e.getValue();
 // only wakes up the pending streams for which there is
 // at least 1 byte of space in both windows
 int minAmount = 1;
 if (size >= minAmount && streamSize >= minAmount) {
 size -= Math.min(streamSize, requestedAmount);
 iter.remove();
 if (candidates == null)
 candidates = new ArrayList<>();
 candidates.add(e.getKey());
                    }
                }
            }
        } finally {
 controllerLock.unlock();
        }
 if (candidates != null) {
 candidates.forEach(Stream::signalWindowUpdate);
        }
 return true;
    }