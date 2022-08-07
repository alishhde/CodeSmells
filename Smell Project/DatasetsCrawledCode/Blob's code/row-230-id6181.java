public interface DbAction<T> {


 Class<T> getEntityType();


 /**
	 * Executing this DbAction with the given {@link Interpreter}.
	 * <p>
	 * The default implementation just performs exception handling and delegates to {@link #doExecuteWith(Interpreter)}.
	 *
	 * @param interpreter the {@link Interpreter} responsible for actually executing the {@link DbAction}.Must not be
	 *          {@code null}.
	 */
 default void executeWith(Interpreter interpreter) {


 try {
 doExecuteWith(interpreter);
		} catch (Exception e) {
 throw new DbActionExecutionException(this, e);
		}
	}


 /**
	 * Executing this DbAction with the given {@link Interpreter} without any exception handling.
	 *
	 * @param interpreter the {@link Interpreter} responsible for actually executing the {@link DbAction}.
	 */
 void doExecuteWith(Interpreter interpreter);


 /**
	 * Represents an insert statement for a single entity that is not the root of an aggregate.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Data
 class Insert<T> implements WithGeneratedId<T>, WithDependingOn<T> {


 @NonNull final T entity;
 @NonNull final PersistentPropertyPath<RelationalPersistentProperty> propertyPath;
 @NonNull final WithEntity<?> dependingOn;


 Map<PersistentPropertyPath<RelationalPersistentProperty>, Object> qualifiers = new HashMap<>();


 private Object generatedId;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}


 @Override
 public Class<T> getEntityType() {
 return WithDependingOn.super.getEntityType();
		}
	}


 /**
	 * Represents an insert statement for the root of an aggregate.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Data
 @RequiredArgsConstructor
 class InsertRoot<T> implements WithEntity<T>, WithGeneratedId<T> {


 @NonNull private final T entity;


 private Object generatedId;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * Represents an update statement for a single entity that is not the root of an aggregate.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Value
 class Update<T> implements WithEntity<T> {


 @NonNull T entity;
 @NonNull PersistentPropertyPath<RelationalPersistentProperty> propertyPath;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * Represents an insert statement for the root of an aggregate.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Value
 class UpdateRoot<T> implements WithEntity<T> {


 @NonNull private final T entity;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * Represents a merge statement for a single entity that is not the root of an aggregate.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Value
 class Merge<T> implements WithDependingOn<T>, WithPropertyPath<T> {


 @NonNull T entity;
 @NonNull PersistentPropertyPath<RelationalPersistentProperty> propertyPath;
 @NonNull WithEntity<?> dependingOn;


 Map<PersistentPropertyPath<RelationalPersistentProperty>, Object> qualifiers = new HashMap<>();


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * Represents a delete statement for all entities that that a reachable via a give path from the aggregate root.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Value
 class Delete<T> implements WithPropertyPath<T> {


 @NonNull Object rootId;
 @NonNull PersistentPropertyPath<RelationalPersistentProperty> propertyPath;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * Represents a delete statement for a aggregate root.
	 * <p>
	 * Note that deletes for contained entities that reference the root are to be represented by separate
	 * {@link DbAction}s.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Value
 class DeleteRoot<T> implements DbAction<T> {


 @NonNull Class<T> entityType;
 @NonNull Object rootId;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * Represents an delete statement for all entities that that a reachable via a give path from any aggregate root of a
	 * given type.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Value
 class DeleteAll<T> implements WithPropertyPath<T> {


 @NonNull PersistentPropertyPath<RelationalPersistentProperty> propertyPath;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * Represents a delete statement for all aggregate roots of a given type.
	 * <p>
	 * Note that deletes for contained entities that reference the root are to be represented by separate
	 * {@link DbAction}s.
	 *
	 * @param <T> type of the entity for which this represents a database interaction.
	 */
 @Value
 class DeleteAllRoot<T> implements DbAction<T> {


 @NonNull private final Class<T> entityType;


 @Override
 public void doExecuteWith(Interpreter interpreter) {
 interpreter.interpret(this);
		}
	}


 /**
	 * An action depending on another action for providing additional information like the id of a parent entity.
	 *
	 * @author Jens Schauder
	 */
 interface WithDependingOn<T> extends WithPropertyPath<T>, WithEntity<T> {


 /**
		 * The {@link DbAction} of a parent entity, possibly the aggregate root. This is used to obtain values needed to
		 * persist the entity, that are not part of the current entity, especially the id of the parent, which might only
		 * become available once the parent entity got persisted.
		 *
		 * @return Guaranteed to be not {@code null}.
		 * @see #getQualifiers()
		 */
 WithEntity<?> getDependingOn();


 /**
		 * Additional values to be set during insert or update statements.
		 * <p>
		 * Values come from parent entities but one might also add values manually.
		 *
		 * @return Guaranteed to be not {@code null}.
		 */
 Map<PersistentPropertyPath<RelationalPersistentProperty>, Object> getQualifiers();


 @Override
 default Class<T> getEntityType() {
 return WithEntity.super.getEntityType();
		}
	}


 /**
	 * A {@link DbAction} that stores the information of a single entity in the database.
	 *
	 * @author Jens Schauder
	 */
 interface WithEntity<T> extends DbAction<T> {


 /**
		 * @return the entity to persist. Guaranteed to be not {@code null}.
		 */
 T getEntity();


 @SuppressWarnings("unchecked")
 @Override
 default Class<T> getEntityType() {
 return (Class<T>) getEntity().getClass();
		}
	}


 /**
	 * A {@link DbAction} that may "update" its entity. In order to support immutable entities this requires at least
	 * potentially creating a new instance, which this interface makes available.
	 *
	 * @author Jens Schauder
	 */
 interface WithGeneratedId<T> extends WithEntity<T> {


 /**
		 * @return the entity to persist. Guaranteed to be not {@code null}.
		 */
 @Nullable
 Object getGeneratedId();


 @SuppressWarnings("unchecked")
 @Override
 default Class<T> getEntityType() {
 return (Class<T>) getEntity().getClass();
		}
	}


 /**
	 * A {@link DbAction} not operation on the root of an aggregate but on its contained entities.
	 *
	 * @author Jens Schauder
	 */
 interface WithPropertyPath<T> extends DbAction<T> {


 /**
		 * @return the path from the aggregate root to the affected entity
		 */
 PersistentPropertyPath<RelationalPersistentProperty> getPropertyPath();


 @SuppressWarnings("unchecked")
 @Override
 default Class<T> getEntityType() {
 return (Class<T>) getPropertyPath().getRequiredLeafProperty().getActualType();
		}
	}
}