 static class ModifierOp extends ChildOp {
 final int v1;
 final int v2;
 ModifierOp(int type, int v1, int v2) {
 super(type);
 this.v1 = v1;
 this.v2 = v2;
        }
 int getData() {
 return this.v1;
        }
 int getData2() {
 return this.v2;
        }
    }