 private static final class Reference {


 private final Tree tree;
 private final PropertyState property;


 private Reference(Tree tree, String propertyName) {
 this.tree = tree;
 this.property = tree.getProperty(propertyName);
        }


 private boolean isMultiple() {
 return property.isArray();
        }


 private void setProperty(String newValue) {
 PropertyState prop = PropertyStates.createProperty(property.getName(), newValue, property.getType().tag());
 tree.setProperty(prop);
        }


 private void setProperty(Iterable<String> newValues) {
 PropertyState prop = PropertyStates.createProperty(property.getName(), newValues, property.getType());
 tree.setProperty(prop);
        }
    }