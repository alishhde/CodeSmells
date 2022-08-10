 protected float removeInstanceTime(InstanceTime time, boolean isBegin) {
 // Trace.enter(this, "removeInstanceTime", new Object[] { time, new Boolean(isBegin) } ); try {
 hasPropagated = true;
 List instanceTimes = isBegin ? beginInstanceTimes : endInstanceTimes;
 int index = Collections.binarySearch(instanceTimes, time);
 for (int i = index; i >= 0; i--) {
 InstanceTime it = (InstanceTime) instanceTimes.get(i);
 if (it == time) {
 instanceTimes.remove(i);
 break;
            }
 if (it.compareTo(time) != 0) {
 break;
            }
        }
 int len = instanceTimes.size();
 for (int i = index + 1; i < len; i++) {
 InstanceTime it = (InstanceTime) instanceTimes.get(i);
 if (it == time) {
 instanceTimes.remove(i);
 break;
            }
 if (it.compareTo(time) != 0) {
 break;
            }
        }
 shouldUpdateCurrentInterval = true;
 float ret;
 if (root.isSampling() && !isSampling) {
 ret = sampleAt(root.getCurrentTime(), root.isHyperlinking());
        } else {
 ret = Float.POSITIVE_INFINITY;
        }
 hasPropagated = false;
 root.currentIntervalWillUpdate();
 return ret;
 // } finally { Trace.exit(); }
    }