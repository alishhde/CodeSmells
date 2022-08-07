public class TraversalFieldAccessorFactory implements FieldAccessorFactory<NodeBacked> {
 @Override
 public boolean accept(final Neo4JPersistentProperty f) {
 final GraphTraversal graphEntityTraversal = f.getAnnotation(GraphTraversal.class);
 return graphEntityTraversal != null
                && graphEntityTraversal.traversalBuilder() != FieldTraversalDescriptionBuilder.class
                && f.getType().equals(Iterable.class);
    }




 @Override
 public FieldAccessor<NodeBacked> forField(final Neo4JPersistentProperty property) {
 return new TraversalFieldAccessor(property);
    }


 /**
	 * @author Michael Hunger
	 * @since 12.09.2010
	 */
 public static class TraversalFieldAccessor implements FieldAccessor<NodeBacked> {
 protected final Neo4JPersistentProperty property;
 private final FieldTraversalDescriptionBuilder fieldTraversalDescriptionBuilder;
 private Class<?> target;
 protected String[] params;


 public TraversalFieldAccessor(final Neo4JPersistentProperty property) {
 this.property = property;
 final GraphTraversal graphEntityTraversal = property.getAnnotation(GraphTraversal.class);
 this.target = resolveTarget(graphEntityTraversal,property);
 this.params = graphEntityTraversal.params();
 this.fieldTraversalDescriptionBuilder = createTraversalDescription(graphEntityTraversal);
	    }


 private Class<?> resolveTarget(GraphTraversal graphTraversal, Neo4JPersistentProperty property) {
 if (!graphTraversal.elementClass().equals(NodeBacked.class)) return graphTraversal.elementClass();
 final Class<?> result = property.getTypeInformation().getActualType().getType();
 Class<?>[] allowedTypes={NodeBacked.class,RelationshipBacked.class,Node.class,Relationship.class, Path.class};
 if (!checkTypes(result,allowedTypes)) throw new IllegalArgumentException("The target result type "+result+" of the traversal is no subclass of the allowed types: "+property+" "+allowedTypes);
 return result;
        }


 private boolean checkTypes(Class<?> target, Class<?>...allowedTypes) {
 for (Class<?> type : allowedTypes) {
 if (type.isAssignableFrom(target)) return true;
            }
 return false;
        }




 @Override
 public boolean isWriteable(NodeBacked nodeBacked) {
 return false;
	    }


 @Override
 public Object setValue(final NodeBacked nodeBacked, final Object newVal) {
 throw new InvalidDataAccessApiUsageException("Cannot set readonly traversal description field " + property);
	    }


 @Override
 public Object getValue(final NodeBacked nodeBacked) {
 final TraversalDescription traversalDescription = fieldTraversalDescriptionBuilder.build(nodeBacked, property,params);
 return doReturn(nodeBacked.findAllByTraversal(target, traversalDescription));
	    }




 private FieldTraversalDescriptionBuilder createTraversalDescription(final GraphTraversal graphEntityTraversal) {
 try {
 final Class<? extends FieldTraversalDescriptionBuilder> traversalDescriptionClass = graphEntityTraversal.traversalBuilder();
 final Constructor<? extends FieldTraversalDescriptionBuilder> constructor = traversalDescriptionClass.getDeclaredConstructor();
 constructor.setAccessible(true);
 return constructor.newInstance();
	        } catch (Exception e) {
 throw new RuntimeException("Error creating TraversalDescription from " + property,e);
	        }
	    }


 @Override
 public Object getDefaultImplementation() {
 return null;
		}


	}
}