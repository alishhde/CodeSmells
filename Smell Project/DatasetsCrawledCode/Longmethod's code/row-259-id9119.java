 @Check
 public void checkNoForwardReferences(XExpression fieldInitializer) {
 JvmIdentifiableElement container = logicalContainerProvider.getLogicalContainer(fieldInitializer);
 if (container instanceof JvmField) {
 JvmField field = (JvmField) container;
 boolean staticField = field.isStatic();
 JvmDeclaredType declaredType = field.getDeclaringType();
 if (declaredType == null) {
 return;
			}
 Collection<JvmField> illegalFields = Sets.newHashSet();
 for(int i = declaredType.getMembers().size() - 1; i>=0; i--) {
 JvmMember member = declaredType.getMembers().get(i);
 if (member instanceof JvmField) {
 if (((JvmField) member).isStatic() == staticField) {
 illegalFields.add((JvmField) member);
					}
				}
 if (member == field)
 break;
			}
 TreeIterator<EObject> iterator = EcoreUtil2.eAll(fieldInitializer);
 while(iterator.hasNext()) {
 EObject object = iterator.next();
 if (object instanceof XFeatureCall) {
 JvmIdentifiableElement feature = ((XFeatureCall) object).getFeature();
 if (illegalFields.contains(((XFeatureCall) object).getFeature())) {
 error("Cannot reference the field '" + feature.getSimpleName() + "' before it is defined", 
 object, null, INSIGNIFICANT_INDEX, ILLEGAL_FORWARD_REFERENCE);
					}
				} else if (isLocalClassSemantics(object)) {
 iterator.prune();
				}
			}
		}
	}