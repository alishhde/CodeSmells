 public static class PutAllEntryData {


 final Object key;


 private Object value;


 private final Object oldValue;


 private final Operation op;


 private EventID eventID;


 transient EntryEventImpl event;


 private Integer bucketId = Integer.valueOf(-1);


 protected transient boolean callbacksInvoked = false;


 public FilterRoutingInfo filterRouting;


 // One flags byte for all booleans
 protected byte flags = 0x00;


 // TODO: Yogesh, this should be intialized and sent on wire only when
 // parallel wan is enabled
 private Long tailKey = 0L;


 public VersionTag versionTag;


 transient boolean inhibitDistribution;


 /**
     * Constructor to use when preparing to send putall data out
     */
 public PutAllEntryData(EntryEventImpl event) {


 this.key = event.getKey();
 this.value = event.getRawNewValueAsHeapObject();
 Object oldValue = event.getRawOldValueAsHeapObject();


 if (oldValue == Token.NOT_AVAILABLE || Token.isRemoved(oldValue)) {
 this.oldValue = null;
      } else {
 this.oldValue = oldValue;
      }


 this.op = event.getOperation();
 this.eventID = event.getEventId();
 this.tailKey = event.getTailKey();
 this.versionTag = event.getVersionTag();


 setNotifyOnly(!event.getInvokePRCallbacks());
 setCallbacksInvoked(event.callbacksInvoked());
 setPossibleDuplicate(event.isPossibleDuplicate());
 setInhibitDistribution(event.getInhibitDistribution());
    }


 /**
     * Constructor to use when receiving a putall from someone else
     */
 public PutAllEntryData(DataInput in, EventID baseEventID, int idx, Version version,
 ByteArrayDataInput bytesIn) throws IOException, ClassNotFoundException {
 this.key = DataSerializer.readObject(in);
 byte flgs = in.readByte();
 if ((flgs & IS_OBJECT) != 0) {
 this.value = DataSerializer.readObject(in);
      } else {
 byte[] bb = DataSerializer.readByteArray(in);
 if ((flgs & IS_CACHED_DESER) != 0) {
 this.value = new FutureCachedDeserializable(bb);
        } else {
 this.value = bb;
        }
      }
 this.oldValue = null;
 this.op = Operation.fromOrdinal(in.readByte());
 this.flags = in.readByte();
 if ((this.flags & FILTER_ROUTING) != 0) {
 this.filterRouting = (FilterRoutingInfo) DataSerializer.readObject(in);
      }
 if ((this.flags & VERSION_TAG) != 0) {
 boolean persistentTag = (this.flags & PERSISTENT_TAG) != 0;
 this.versionTag = VersionTag.create(persistentTag, in);
      }
 if (isUsedFakeEventId()) {
 this.eventID = new EventID();
 InternalDataSerializer.invokeFromData(this.eventID, in);
      } else {
 this.eventID = new EventID(baseEventID, idx);
      }
 if ((this.flags & HAS_TAILKEY) != 0) {
 this.tailKey = DataSerializer.readLong(in);
      }
    }


 @Override
 public String toString() {
 StringBuilder sb = new StringBuilder(50);
 sb.append("(").append(getKey()).append(",").append(this.value).append(",")
          .append(getOldValue());
 if (this.bucketId > 0) {
 sb.append(", b").append(this.bucketId);
      }
 if (versionTag != null) {
 sb.append(versionTag);
 // sb.append(",v").append(versionTag.getEntryVersion()).append(",rv"+versionTag.getRegionVersion());
      }
 if (filterRouting != null) {
 sb.append(", ").append(filterRouting);
      }
 sb.append(")");
 return sb.toString();
    }


 void setSender(InternalDistributedMember sender) {
 if (this.versionTag != null) {
 this.versionTag.replaceNullIDs(sender);
      }
    }


 /**
     * Used to serialize this instances data to <code>out</code>. If changes are made to this method
     * make sure that it is backwards compatible by creating toDataPreXX methods. Also make sure
     * that the callers to this method are backwards compatible by creating toDataPreXX methods for
     * them even if they are not changed. <br>
     * Callers for this method are: <br>
     * {@link PutAllMessage#toData(DataOutput)} <br>
     * {@link PutAllPRMessage#toData(DataOutput)} <br>
     * {@link RemotePutAllMessage#toData(DataOutput)} <br>
     */
 public void toData(final DataOutput out) throws IOException {
 Object key = this.key;
 final Object v = this.value;
 DataSerializer.writeObject(key, out);


 if (v instanceof byte[] || v == null) {
 out.writeByte(0);
 DataSerializer.writeByteArray((byte[]) v, out);
      } else if (v instanceof CachedDeserializable) {
 CachedDeserializable cd = (CachedDeserializable) v;
 out.writeByte(IS_CACHED_DESER);
 DataSerializer.writeByteArray(cd.getSerializedValue(), out);
      } else {
 out.writeByte(IS_CACHED_DESER);
 DataSerializer.writeObjectAsByteArray(v, out);
      }
 out.writeByte(this.op.ordinal);
 byte bits = this.flags;
 if (this.filterRouting != null)
 bits |= FILTER_ROUTING;
 if (this.versionTag != null) {
 bits |= VERSION_TAG;
 if (this.versionTag instanceof DiskVersionTag) {
 bits |= PERSISTENT_TAG;
        }
      }
 // TODO: Yogesh, this should be conditional,
 // make sure that we sent it on wire only
 // when parallel wan is enabled
 bits |= HAS_TAILKEY;
 out.writeByte(bits);


 if (this.filterRouting != null) {
 DataSerializer.writeObject(this.filterRouting, out);
      }
 if (this.versionTag != null) {
 InternalDataSerializer.invokeToData(this.versionTag, out);
      }
 if (isUsedFakeEventId()) {
 // fake event id should be serialized
 InternalDataSerializer.invokeToData(this.eventID, out);
      }
 // TODO: Yogesh, this should be conditional,
 // make sure that we sent it on wire only
 // when parallel wan is enabled
 DataSerializer.writeLong(this.tailKey, out);
    }


 /**
     * Returns the key
     */
 public Object getKey() {
 return this.key;
    }


 /**
     * Returns the value
     */
 public Object getValue(InternalCache cache) {
 Object result = this.value;
 if (result instanceof FutureCachedDeserializable) {
 FutureCachedDeserializable future = (FutureCachedDeserializable) result;
 result = future.create(cache);
 this.value = result;
      }
 return result;
    }


 /**
     * Returns the old value
     */
 public Object getOldValue() {
 return this.oldValue;
    }


 public Long getTailKey() {
 return this.tailKey;
    }


 public void setTailKey(Long key) {
 this.tailKey = key;
    }


 /**
     * Returns the operation
     */
 public Operation getOp() {
 return this.op;
    }


 public EventID getEventID() {
 return this.eventID;
    }


 /**
     * change event id for the entry
     *
     * @param eventId new event id
     */
 public void setEventId(EventID eventId) {
 this.eventID = eventId;
    }


 /**
     * change bucket id for the entry
     *
     * @param bucketId new bucket id
     */
 public void setBucketId(Integer bucketId) {
 this.bucketId = bucketId;
    }


 /**
     * get bucket id for the entry
     *
     * @return bucket id
     */
 public Integer getBucketId() {
 return this.bucketId;
    }


 /**
     * change event id into fake event id The algorithm is to change the threadid into
     * bucketid*MAX_THREAD_PER_CLIENT+oldthreadid. So from the log, we can derive the original
     * thread id.
     *
     * @return wether current event id is fake or not new bucket id
     */
 public boolean setFakeEventID() {
 if (bucketId.intValue() < 0) {
 return false;
      }


 if (!isUsedFakeEventId()) {
 // assign a fake big thread id. bucket id starts from 0. In order to distinguish
 // with other read thread, let bucket id starts from 1 in fake thread id
 long threadId = ThreadIdentifier.createFakeThreadIDForBulkOp(bucketId.intValue(),
 eventID.getThreadID());
 this.eventID = new EventID(eventID.getMembershipID(), threadId, eventID.getSequenceID());
 this.setUsedFakeEventId(true);
      }
 return true;
    }


 public boolean isUsedFakeEventId() {
 return (flags & USED_FAKE_EVENT_ID) != 0;
    }


 public void setUsedFakeEventId(boolean usedFakeEventId) {
 if (usedFakeEventId) {
 flags |= USED_FAKE_EVENT_ID;
      } else {
 flags &= ~(USED_FAKE_EVENT_ID);
      }
    }


 public boolean isNotifyOnly() {
 return (flags & NOTIFY_ONLY) != 0;
    }


 public void setNotifyOnly(boolean notifyOnly) {
 if (notifyOnly) {
 flags |= NOTIFY_ONLY;
      } else {
 flags &= ~(NOTIFY_ONLY);
      }
    }


 boolean isPossibleDuplicate() {
 return (this.flags & POSDUP) != 0;
    }


 public void setPossibleDuplicate(boolean possibleDuplicate) {
 if (possibleDuplicate) {
 flags |= POSDUP;
      } else {
 flags &= ~(POSDUP);
      }
    }


 public boolean isInhibitDistribution() {
 return this.inhibitDistribution;
    }


 public void setInhibitDistribution(boolean inhibitDistribution) {
 this.inhibitDistribution = inhibitDistribution;
    }


 public boolean isCallbacksInvoked() {
 return this.callbacksInvoked;
    }


 public void setCallbacksInvoked(boolean callbacksInvoked) {
 this.callbacksInvoked = callbacksInvoked;
    }
  }