 public String toCommandLine() {
 return preparer.map(it -> it.apply(value)).orElseGet(() -> value.toString());
			}