 public int compareTo(FactPartition o) {
 int colComp = this.partCol.compareTo(o.partCol);
 if (colComp == 0) {
 int partComp = 0;
 if (this.partSpec != null) {
 if (o.partSpec == null) {
 partComp = 1;
        } else {
 partComp = this.partSpec.compareTo(o.partSpec);
        }
      } else {
 if (o.partSpec != null) {
 partComp = -1;
        } else {
 partComp = 0;
        }
      }
 if (partComp == 0) {
 int upComp = 0;
 if (this.period != null && o.period != null) {
 upComp = this.period.compareTo(o.period);
        } else if (this.period == null && o.period == null) {
 upComp = 0;
        } else if (this.period == null) {
 upComp = -1;
        } else {
 upComp = 1;
        }
 if (upComp == 0) {
 if (this.containingPart != null) {
 if (o.containingPart == null) {
 return 1;
            }
 return this.containingPart.compareTo(o.containingPart);
          } else {
 if (o.containingPart != null) {
 return -1;
            } else {
 return 0;
            }
          }
        }
 return upComp;
      }
 return partComp;
    }
 return colComp;
  }