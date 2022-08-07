 private final class SymbolProcessor implements FunctionVisitor, InstructionVisitorAdapter {


 private final SourceFunction function;
 private final LinkedList<Integer> removeFromBlock = new LinkedList<>();


 private int blockInstIndex = 0;
 private DbgValueInstruction lastDbgValue = null;
 private InstructionBlock currentBlock = null;


 private SymbolProcessor(SourceFunction function) {
 this.function = function;
        }


 @Override
 public void visit(InstructionBlock block) {
 currentBlock = block;
 lastDbgValue = null;
 for (blockInstIndex = 0; blockInstIndex < block.getInstructionCount(); blockInstIndex++) {
 block.getInstruction(blockInstIndex).accept(this);
            }
 if (!removeFromBlock.isEmpty()) {
 for (int i : removeFromBlock) {
 currentBlock.remove(i);
                }
 removeFromBlock.clear();
            }
        }


 @Override
 public void visitInstruction(Instruction instruction) {
 final MDLocation loc = instruction.getDebugLocation();
 if (loc != null) {
 final LLVMSourceLocation scope = cache.buildLocation(loc);
 if (scope != null) {
 instruction.setSourceLocation(scope);
                }
            }
        }


 @Override
 public void visit(VoidCallInstruction call) {
 final SymbolImpl callTarget = call.getCallTarget();
 if (callTarget instanceof FunctionDeclaration) {
 switch (((FunctionDeclaration) callTarget).getName()) {
 case LLVM_DBG_DECLARE_NAME:
 handleDebugIntrinsic(call, true);
 return;


 case LLVM_DBG_ADDR_NAME:
 // dbg.declare and dbg.addr have the same interface and, for our purposes,
 // the same semantics
 handleDebugIntrinsic(call, true);
 return;


 case LLVM_DBG_VALUE_NAME:
 handleDebugIntrinsic(call, false);
 return;


 case LLVM_DEBUGTRAP_NAME:
 visitDebugTrap(call);
 return;
                }
            }


 visitInstruction(call);
        }


 private void visitDebugTrap(VoidCallInstruction call) {
 final DebugTrapInstruction trap = DebugTrapInstruction.create(call);
 currentBlock.set(blockInstIndex, trap);
 visitInstruction(trap);
        }


 private SourceVariable getVariable(VoidCallInstruction call, int index) {
 final SymbolImpl varSymbol = getArg(call, index);
 if (varSymbol instanceof MetadataSymbol) {
 final MDBaseNode mdLocal = ((MetadataSymbol) varSymbol).getNode();


 final LLVMSourceSymbol symbol = cache.getSourceSymbol(mdLocal, false);
 return function.getLocal(symbol);
            }


 return null;
        }


 private void handleDebugIntrinsic(VoidCallInstruction call, boolean isDeclaration) {
 SymbolImpl value = getArg(call, LLVM_DBG_INTRINSICS_VALUE_ARGINDEX);
 if (value instanceof MetadataSymbol) {
 value = MDSymbolExtractor.getSymbol(((MetadataSymbol) value).getNode());
            }


 if (value == null) {
 // this may happen if llvm optimizations removed a variable
 value = new NullConstant(MetaType.DEBUG);


            } else if (value instanceof ValueInstruction) {
                ((ValueInstruction) value).setSourceVariable(true);


            } else if (value instanceof FunctionParameter) {
                ((FunctionParameter) value).setSourceVariable(true);
            }


 int mdLocalArgIndex;
 int mdExprArgIndex;
 if (isDeclaration) {
 mdLocalArgIndex = LLVM_DBG_DECLARE_LOCALREF_ARGINDEX;
 mdExprArgIndex = LLVM_DBG_DECLARE_EXPR_ARGINDEX;


            } else if (call.getArgumentCount() == LLVM_DBG_VALUE_LOCALREF_ARGSIZE_NEW) {
 mdLocalArgIndex = LLVM_DBG_VALUE_LOCALREF_ARGINDEX_NEW;
 mdExprArgIndex = LLVM_DBG_VALUE_EXPR_ARGINDEX_NEW;


            } else if (call.getArgumentCount() == LLVM_DBG_VALUE_LOCALREF_ARGSIZE_OLD) {
 mdLocalArgIndex = LLVM_DBG_VALUE_LOCALREF_ARGINDEX_OLD;
 mdExprArgIndex = LLVM_DBG_VALUE_EXPR_ARGINDEX_OLD;


            } else {
 return;
            }


 final SourceVariable variable = getVariable(call, mdLocalArgIndex);
 if (variable == null) {
 // invalid or unsupported debug information
 // remove upper indices so we do not need to update the later ones
 removeFromBlock.addFirst(blockInstIndex);
 return;
            }


 final MDExpression expression = getExpression(call, mdExprArgIndex);
 if (ValueFragment.describesFragment(expression)) {
 variable.addFragment(ValueFragment.parse(expression));
            } else {
 variable.addFullDefinition();
            }


 if (isDeclaration) {
 final DbgDeclareInstruction dbgDeclare = new DbgDeclareInstruction(value, variable, expression);
 variable.addDeclaration(dbgDeclare);
 currentBlock.set(blockInstIndex, dbgDeclare);


            } else {
 long index = 0;
 if (call.getArgumentCount() == LLVM_DBG_VALUE_LOCALREF_ARGSIZE_OLD) {
 final SymbolImpl indexSymbol = call.getArgument(LLVM_DBG_VALUE_INDEX_ARGINDEX_OLD);
 final Long l = LLVMSymbolReadResolver.evaluateLongIntegerConstant(indexSymbol);
 if (l != null) {
 index = l;
                    }
                }
 final DbgValueInstruction dbgValue = new DbgValueInstruction(value, variable, index, expression);


 if (dbgValue.equals(lastDbgValue)) {
 // at higher optimization levels llvm often duplicates the @llvm.dbg.value
 // intrinsic call, we remove it again to avoid unnecessary runtime overhead
 removeFromBlock.addFirst(blockInstIndex);


                } else {
 variable.addValue(dbgValue);
 currentBlock.set(blockInstIndex, dbgValue);
 lastDbgValue = dbgValue;
                }
            }
        }
    }