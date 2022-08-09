 public static class DCSerialField extends DCBlockTag implements SerialFieldTree {
 public final DCIdentifier name;
 public final DCReference type;
 public final List<DCTree> description;


 DCSerialField(DCIdentifier name, DCReference type, List<DCTree> description) {
 this.description = description;
 this.name = name;
 this.type = type;
        }


 @Override @DefinedBy(Api.COMPILER_TREE)
 public Kind getKind() {
 return Kind.SERIAL_FIELD;
        }


 @Override @DefinedBy(Api.COMPILER_TREE)
 public <R, D> R accept(DocTreeVisitor<R, D> v, D d) {
 return v.visitSerialField(this, d);
        }


 @Override @DefinedBy(Api.COMPILER_TREE)
 public List<? extends DocTree> getDescription() {
 return description;
        }


 @Override @DefinedBy(Api.COMPILER_TREE)
 public IdentifierTree getName() {
 return name;
        }


 @Override @DefinedBy(Api.COMPILER_TREE)
 public ReferenceTree getType() {
 return type;
        }
    }