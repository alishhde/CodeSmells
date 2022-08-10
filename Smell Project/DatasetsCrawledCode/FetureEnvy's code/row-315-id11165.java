 protected static boolean typeCheckMethodsWithGenerics(ClassNode receiver, ClassNode[] arguments, MethodNode candidateMethod) {
 if (isUsingUncheckedGenerics(receiver)) {
 return true;
        }
 if (CLASS_Type.equals(receiver)
                && receiver.isUsingGenerics()
                && !candidateMethod.getDeclaringClass().equals(receiver)
                && !(candidateMethod instanceof ExtensionMethodNode)) {
 return typeCheckMethodsWithGenerics(receiver.getGenericsTypes()[0].getType(), arguments, candidateMethod);
        }
 // both candidate method and receiver have generic information so a check is possible
 GenericsType[] genericsTypes = candidateMethod.getGenericsTypes();
 boolean methodUsesGenerics = (genericsTypes != null && genericsTypes.length > 0);
 boolean isExtensionMethod = candidateMethod instanceof ExtensionMethodNode;
 if (isExtensionMethod && methodUsesGenerics) {
 ClassNode[] dgmArgs = new ClassNode[arguments.length + 1];
 dgmArgs[0] = receiver;
 System.arraycopy(arguments, 0, dgmArgs, 1, arguments.length);
 MethodNode extensionMethodNode = ((ExtensionMethodNode) candidateMethod).getExtensionMethodNode();
 return typeCheckMethodsWithGenerics(extensionMethodNode.getDeclaringClass(), dgmArgs, extensionMethodNode, true);
        } else {
 return typeCheckMethodsWithGenerics(receiver, arguments, candidateMethod, false);
        }
    }