 class LastAck {
 long lastAckedSequence;
 byte priority;


 public LastAck(LastAck source) {
 this.lastAckedSequence = source.lastAckedSequence;
 this.priority = source.priority;
        }


 public LastAck() {
 this.priority = MessageOrderIndex.HI;
        }


 public LastAck(long ackLocation) {
 this.lastAckedSequence = ackLocation;
 this.priority = MessageOrderIndex.LO;
        }


 public LastAck(long ackLocation, byte priority) {
 this.lastAckedSequence = ackLocation;
 this.priority = priority;
        }


 @Override
 public String toString() {
 return "[" + lastAckedSequence + ":" + priority + "]";
        }
    }