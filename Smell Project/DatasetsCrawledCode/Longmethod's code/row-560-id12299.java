 TreeNodeChildren(final TreeNode parent, final Object metadata, final PropertyAccessor accessor) {
 this.parent   = parent;
 this.metadata = metadata;
 this.accessor = accessor;
 this.children = new TreeNode[accessor.count()];
 /*
         * Search for something that looks like the main property, to be associated with the parent node
         * instead than provided as a child. The intent is to have more compact and easy to read trees.
         * That property shall be a singleton for a simple value (not another metadata object).
         */
 if (parent.table.valuePolicy == ValueExistencePolicy.COMPACT) {
 TitleProperty an = accessor.implementation.getAnnotation(TitleProperty.class);
 if (an == null) {
 Class<?> implementation = parent.table.standard.getImplementation(accessor.type);
 if (implementation != null) {
 an = implementation.getAnnotation(TitleProperty.class);
                }
            }
 if (an != null) {
 final int index = accessor.indexOf(an.name(), false);
 final Class<?> type = accessor.type(index, TypeValuePolicy.ELEMENT_TYPE);
 if (type != null && !parent.isMetadata(type) && type == accessor.type(index, TypeValuePolicy.PROPERTY_TYPE)) {
 titleProperty = index;
 return;
                }
            }
        }
 titleProperty = -1;
    }