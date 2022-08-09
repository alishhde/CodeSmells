 class PushCommand extends KeyCommand {


 private List<ByteBuffer> values;
 private boolean upsert;
 private Direction direction;


 private PushCommand(@Nullable ByteBuffer key, List<ByteBuffer> values, Direction direction, boolean upsert) {


 super(key);


 this.values = values;
 this.upsert = upsert;
 this.direction = direction;
		}


 /**
		 * Creates a new {@link PushCommand} for right push ({@literal RPUSH}).
		 *
		 * @return a new {@link PushCommand} for right push ({@literal RPUSH}).
		 */
 public static PushCommand right() {
 return new PushCommand(null, Collections.emptyList(), Direction.RIGHT, true);
		}


 /**
		 * Creates a new {@link PushCommand} for left push ({@literal LPUSH}).
		 *
		 * @return a new {@link PushCommand} for left push ({@literal LPUSH}).
		 */
 public static PushCommand left() {
 return new PushCommand(null, Collections.emptyList(), Direction.LEFT, true);
		}


 /**
		 * Applies the {@literal value}. Constructs a new command instance with all previously configured properties.
		 *
		 * @param value must not be {@literal null}.
		 * @return a new {@link PushCommand} with {@literal value} applied.
		 */
 public PushCommand value(ByteBuffer value) {


 Assert.notNull(value, "Value must not be null!");


 return new PushCommand(null, Collections.singletonList(value), direction, upsert);
		}


 /**
		 * Applies a {@link List} of {@literal values}.
		 *
		 * @param values must not be {@literal null}.
		 * @return a new {@link PushCommand} with {@literal values} applied.
		 */
 public PushCommand values(List<ByteBuffer> values) {


 Assert.notNull(values, "Values must not be null!");


 return new PushCommand(null, new ArrayList<>(values), direction, upsert);
		}


 /**
		 * Applies the {@literal key}. Constructs a new command instance with all previously configured properties.
		 *
		 * @param key must not be {@literal null}.
		 * @return a new {@link PushCommand} with {@literal key} applied.
		 */
 public PushCommand to(ByteBuffer key) {


 Assert.notNull(key, "Key must not be null!");


 return new PushCommand(key, values, direction, upsert);
		}


 /**
		 * Disable upsert. Constructs a new command instance with all previously configured properties.
		 *
		 * @return a new {@link PushCommand} with upsert disabled.
		 */
 public PushCommand ifExists() {
 return new PushCommand(getKey(), values, direction, false);
		}


 /**
		 * @return never {@literal null}.
		 */
 public List<ByteBuffer> getValues() {
 return values;
		}


 /**
		 * @return
		 */
 public boolean getUpsert() {
 return upsert;
		}


 /**
		 * @return never {@literal null}.
		 */
 public Direction getDirection() {
 return direction;
		}
	}