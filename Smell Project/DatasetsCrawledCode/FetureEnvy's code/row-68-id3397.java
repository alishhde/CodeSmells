 public Expression setUpper(Bound newUpper)
    {
 upper = newUpper == null ? null : new Bound(newUpper.value, newUpper.inclusive);
 return this;
    }