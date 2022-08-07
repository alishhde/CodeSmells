 @Value
 @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
 public static class Argument {


 public static Argument SKIP_TESTS = Argument.arg("skipTests");


 @NonNull String name;
 @NonNull Optional<ArgumentValue<?>> value;


 private Argument(String name, ArgumentValue<?> value) {
 this(name, Optional.of(value));
		}


 static Argument of(String name) {
 return new Argument(name, Optional.empty());
		}


 /**
		 * Enables the given comma-separated profiles for the {@link CommandLine}.
		 * 
		 * @param name must not be {@literal null} or empty.
		 * @return
		 */
 public static Argument profile(String name, String... others) {


 Assert.hasText(name, "Profiles must not be null or empty!");
 Assert.notNull(others, "Other profiles must not be null!");


 String profiles = Stream.concat(Stream.of(name), Arrays.stream(others)).collect(Collectors.joining(","));


 return Argument.of("-P".concat(profiles));
		}


 public static Argument arg(String name) {
 return Argument.of("-D".concat(name));
		}


 public static Argument debug() {
 return Argument.of("-X");
		}


 public Argument withValue(Object value) {
 return new Argument(name, ArgumentValue.of(value));
		}


 public Argument withQuotedValue(Object value) {
 return new Argument(name, ArgumentValue.of(value, it -> String.format("\"%s\"", it.toString())));
		}


 public Argument withValue(Masked masked) {
 return new Argument(name, ArgumentValue.of(masked));
		}


 public String toCommandLineArgument() {
 return toNameValuePair(value.map(ArgumentValue::toCommandLine));
		}


 /* 
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
 @Override
 public String toString() {
 return toNameValuePair(value.map(Object::toString));
		}


 private String toNameValuePair(Optional<String> source) {


 return source//
					.map(it -> String.format("%s=%s", name, it))//
					.orElse(name);
		}


 @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
 private static class ArgumentValue<T> {


 private final @NonNull T value;
 private final @NonNull Optional<Function<T, String>> preparer;
 private final @NonNull Optional<Function<T, String>> toString;


 public static <T> ArgumentValue<T> of(T value) {
 return new ArgumentValue<>(value, Optional.empty(), Optional.empty());
			}


 public static <T> ArgumentValue<T> of(T value, Function<T, String> preparer) {
 return new ArgumentValue<>(value, Optional.of(preparer), Optional.empty());
			}


 /**
			 * Returns an {@link ArgumentValue} for the given {@link Masked} value.
			 * 
			 * @param masked must not be {@literal null}.
			 * @return
			 */
 public static <T extends Masked> ArgumentValue<T> of(T masked) {
 return new ArgumentValue<>(masked, Optional.empty(), Optional.of(it -> it.masked()));
			}


 /**
			 * Returns the {@link String} variant of the argument value.
			 * 
			 * @return
			 */
 public String toCommandLine() {
 return preparer.map(it -> it.apply(value)).orElseGet(() -> value.toString());
			}


 /*
			 * (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
 public String toString() {
 return toString.map(it -> it.apply(value)).orElseGet(() -> toCommandLine());
			}
		}
	}