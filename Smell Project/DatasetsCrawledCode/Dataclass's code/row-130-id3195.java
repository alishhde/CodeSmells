 public final static class Builder<A> {


 private Supplier<A> initialValue;
 private UnaryOperator<A> splitOperator = null;
 private BinaryOperator<A> mergeOperator = null;


 private Builder() {
        }


 public Builder initialValue(final Supplier<A> initialValue) {
 this.initialValue = initialValue;
 return this;
        }


 public Builder splitOperator(final UnaryOperator<A> splitOperator) {
 this.splitOperator = splitOperator;
 return this;
        }


 public Builder mergeOperator(final BinaryOperator<A> mergeOperator) {
 this.mergeOperator = mergeOperator;
 return this;
        }


 public SackStrategy create() {
 return new SackStrategy(this.initialValue, this.splitOperator, this.mergeOperator);
        }
    }