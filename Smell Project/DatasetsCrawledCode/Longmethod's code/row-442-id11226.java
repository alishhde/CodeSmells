 public boolean matchesAllInstances(SequenceType testST) {
 Quantifier stq = sequenceType.getQuantifier();
 ItemType it = sequenceType.getItemType();
 if (stq.isSubQuantifier(testST.getQuantifier())) {
 if (it instanceof AnyItemType) {
 return true;
            } else if (it.isAtomicType() && testST.getItemType().isAtomicType()) {
 AtomicType ait = (AtomicType) it;
 AtomicType testIT = (AtomicType) testST.getItemType();
 if (BuiltinTypeRegistry.INSTANCE.isBuiltinTypeId(testIT.getTypeId())) {
 SchemaType vType = BuiltinTypeRegistry.INSTANCE.getSchemaTypeById(testIT.getTypeId());
 while (vType != null && vType.getTypeId() != ait.getTypeId()) {
 vType = vType.getBaseType();
                    }
 return vType != null;
                }
            } else if (it instanceof NodeType && testST.getItemType() instanceof NodeType) {
 NodeType nt = (NodeType) it;
 NodeKind kind = nt.getNodeKind();
 NodeType testNT = (NodeType) testST.getItemType();
 NodeKind testKind = testNT.getNodeKind();
 if (kind == NodeKind.ANY || kind == testKind) {
 return true;
                }
            }
 return false;
        }
 return false;
    }