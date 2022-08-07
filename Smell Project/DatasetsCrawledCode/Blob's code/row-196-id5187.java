 private final class FindInMethod extends MethodVisitor {


 private final String name;
 private final String desc;
 private final int access;
 private FindInAnno fia;
 private boolean bodyGenerated;


 public FindInMethod(int access, String name, String desc, MethodVisitor mv) {
 super(Opcodes.ASM5, mv);
 this.access = access;
 this.name = name;
 this.desc = desc;
            }


 @Override
 public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
 if ("Lnet/java/html/js/JavaScriptBody;".equals(desc)) { // NOI18N
 found++;
 return new FindInAnno();
                }
 return super.visitAnnotation(desc, visible);
            }


 private void generateJSBody(FindInAnno fia) {
 this.fia = fia;
            }


 @Override
 public void visitCode() {
 if (fia == null) {
 return;
                }
 generateBody(true);
            }


 private boolean generateBody(boolean hasCode) {
 if (bodyGenerated) {
 return false;
                }
 bodyGenerated = true;
 if (mv != null) {
 AnnotationVisitor va = super.visitAnnotation("Lnet/java/html/js/JavaScriptBody;", false);
 AnnotationVisitor varr = va.visitArray("args");
 for (String argName : fia.args) {
 varr.visit(null, argName);
                    }
 varr.visitEnd();
 va.visit("javacall", fia.javacall);
 va.visit("body", fia.body);
 va.visitEnd();
                }
 
 String body;
 List<String> args;
 if (fia.javacall) {
 body = callback(fia.body);
 args = new ArrayList<String>(fia.args);
 args.add("vm");
                } else {
 body = fia.body;
 args = fia.args;
                }


 super.visitFieldInsn(
 Opcodes.GETSTATIC, FindInClass.this.name,
 "$$fn$$" + name + "_" + found,
 "Lorg/netbeans/html/boot/spi/Fn;"
                );
 super.visitInsn(Opcodes.DUP);
 super.visitMethodInsn(
 Opcodes.INVOKESTATIC,
 "org/netbeans/html/boot/spi/Fn", "isValid",
 "(Lorg/netbeans/html/boot/spi/Fn;)Z"
                );
 Label ifNotNull = new Label();
 super.visitJumpInsn(Opcodes.IFNE, ifNotNull);


 // init Fn
 super.visitInsn(Opcodes.POP);
 super.visitLdcInsn(Type.getObjectType(FindInClass.this.name));
 super.visitInsn(fia.keepAlive ? Opcodes.ICONST_1 : Opcodes.ICONST_0);
 super.visitLdcInsn(body);
 super.visitIntInsn(Opcodes.SIPUSH, args.size());
 super.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/String");
 boolean needsVM = false;
 for (int i = 0; i < args.size(); i++) {
 assert !needsVM;
 String argName = args.get(i);
 needsVM = "vm".equals(argName);
 super.visitInsn(Opcodes.DUP);
 super.visitIntInsn(Opcodes.BIPUSH, i);
 super.visitLdcInsn(argName);
 super.visitInsn(Opcodes.AASTORE);
                }
 super.visitMethodInsn(Opcodes.INVOKESTATIC,
 "org/netbeans/html/boot/spi/Fn", "define",
 "(Ljava/lang/Class;ZLjava/lang/String;[Ljava/lang/String;)Lorg/netbeans/html/boot/spi/Fn;"
                );
 Label noPresenter = new Label();
 super.visitInsn(Opcodes.DUP);
 super.visitJumpInsn(Opcodes.IFNULL, noPresenter);
 int cnt = resourcesCnt;
 while (cnt > 0) {
 String resource = resources[--cnt];
 if (resource == null) {
 continue;
                    }
 super.visitLdcInsn(Type.getObjectType(FindInClass.this.name));
 super.visitLdcInsn(resource);
 super.visitMethodInsn(Opcodes.INVOKESTATIC,
 "org/netbeans/html/boot/spi/Fn", "preload",
 "(Lorg/netbeans/html/boot/spi/Fn;Ljava/lang/Class;Ljava/lang/String;)Lorg/netbeans/html/boot/spi/Fn;"
                    );
                }
 super.visitInsn(Opcodes.DUP);
 super.visitFieldInsn(
 Opcodes.PUTSTATIC, FindInClass.this.name,
 "$$fn$$" + name + "_" + found,
 "Lorg/netbeans/html/boot/spi/Fn;"
                );
 // end of Fn init


 super.visitLabel(ifNotNull);


 final int offset;
 if ((access & Opcodes.ACC_STATIC) == 0) {
 offset = 1;
 super.visitIntInsn(Opcodes.ALOAD, 0);
                } else {
 offset = 0;
 super.visitInsn(Opcodes.ACONST_NULL);
                }


 super.visitIntInsn(Opcodes.SIPUSH, args.size());
 super.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");


 class SV extends SignatureVisitor {


 private boolean nowReturn;
 private Type returnType;
 private int index;
 private int loadIndex = offset;


 public SV() {
 super(Opcodes.ASM5);
                    }


 @Override
 public void visitBaseType(char descriptor) {
 final Type t = Type.getType("" + descriptor);
 if (nowReturn) {
 returnType = t;
 return;
                        }
 FindInMethod.super.visitInsn(Opcodes.DUP);
 FindInMethod.super.visitIntInsn(Opcodes.SIPUSH, index++);
 FindInMethod.super.visitVarInsn(t.getOpcode(Opcodes.ILOAD), loadIndex++);
 String factory;
 switch (descriptor) {
 case 'I':
 factory = "java/lang/Integer";
 break;
 case 'J':
 factory = "java/lang/Long";
 loadIndex++;
 break;
 case 'S':
 factory = "java/lang/Short";
 break;
 case 'F':
 factory = "java/lang/Float";
 break;
 case 'D':
 factory = "java/lang/Double";
 loadIndex++;
 break;
 case 'Z':
 factory = "java/lang/Boolean";
 break;
 case 'C':
 factory = "java/lang/Character";
 break;
 case 'B':
 factory = "java/lang/Byte";
 break;
 default:
 throw new IllegalStateException(t.toString());
                        }
 FindInMethod.super.visitMethodInsn(Opcodes.INVOKESTATIC,
 factory, "valueOf", "(" + descriptor + ")L" + factory + ";"
                        );
 FindInMethod.super.visitInsn(Opcodes.AASTORE);
                    }


 @Override
 public SignatureVisitor visitArrayType() {
 if (nowReturn) {
 return new SignatureVisitor(Opcodes.ASM5) {
 @Override
 public void visitClassType(String name) {
 returnType = Type.getType("[" + Type.getObjectType(name).getDescriptor());
                                }


 @Override
 public void visitBaseType(char descriptor) {
 returnType = Type.getType("[" + descriptor);
                                }
                            };
                        }
 loadObject();
 return new SignatureWriter();
                    }


 @Override
 public void visitClassType(String name) {
 if (nowReturn) {
 returnType = Type.getObjectType(name);
 return;
                        }
 loadObject();
                    }


 @Override
 public SignatureVisitor visitReturnType() {
 nowReturn = true;
 return this;
                    }


 private void loadObject() {
 FindInMethod.super.visitInsn(Opcodes.DUP);
 FindInMethod.super.visitIntInsn(Opcodes.SIPUSH, index++);
 FindInMethod.super.visitVarInsn(Opcodes.ALOAD, loadIndex++);
 FindInMethod.super.visitInsn(Opcodes.AASTORE);
                    }


                }
 SV sv = new SV();
 SignatureReader sr = new SignatureReader(desc);
 sr.accept(sv);


 if (needsVM) {
 FindInMethod.super.visitInsn(Opcodes.DUP);
 FindInMethod.super.visitIntInsn(Opcodes.SIPUSH, sv.index);
 int lastSlash = FindInClass.this.name.lastIndexOf('/');
 String jsCallbacks = FindInClass.this.name.substring(0, lastSlash + 1) + "$JsCallbacks$";
 FindInMethod.super.visitFieldInsn(Opcodes.GETSTATIC, jsCallbacks, "VM", "L" + jsCallbacks + ";");
 FindInMethod.super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, jsCallbacks, "current", "()L" + jsCallbacks + ";");
 FindInMethod.super.visitInsn(Opcodes.AASTORE);
                }


 if (fia.wait4js) {
 super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
 "org/netbeans/html/boot/spi/Fn", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"
                    );
 switch (sv.returnType.getSort()) {
 case Type.VOID:
 super.visitInsn(Opcodes.RETURN);
 break;
 case Type.ARRAY:
 case Type.OBJECT:
 super.visitTypeInsn(Opcodes.CHECKCAST, sv.returnType.getInternalName());
 super.visitInsn(Opcodes.ARETURN);
 break;
 case Type.BOOLEAN: {
 Label handleNullValue = new Label();
 super.visitInsn(Opcodes.DUP);
 super.visitJumpInsn(Opcodes.IFNULL, handleNullValue);
 super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
 super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
 "java/lang/Boolean", "booleanValue", "()Z"
                            );
 super.visitInsn(Opcodes.IRETURN);
 super.visitLabel(handleNullValue);
 super.visitInsn(Opcodes.ICONST_0);
 super.visitInsn(Opcodes.IRETURN);
 break;
                        }
 default:
 super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Number");
 super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
 "java/lang/Number", sv.returnType.getClassName() + "Value", "()" + sv.returnType.getDescriptor()
                            );
 super.visitInsn(sv.returnType.getOpcode(Opcodes.IRETURN));
                    }
                } else {
 super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
 "org/netbeans/html/boot/spi/Fn", "invokeLater", "(Ljava/lang/Object;[Ljava/lang/Object;)V"
                    );
 super.visitInsn(Opcodes.RETURN);
                }
 super.visitLabel(noPresenter);
 if (hasCode) {
 super.visitCode();
                } else {
 super.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalStateException");
 super.visitInsn(Opcodes.DUP);
 super.visitLdcInsn("No presenter active. Use BrwsrCtx.execute!");
 super.visitMethodInsn(Opcodes.INVOKESPECIAL, 
 "java/lang/IllegalStateException", "<init>", "(Ljava/lang/String;)V"
                    );
 this.visitInsn(Opcodes.ATHROW);
                }
 return true;
            }
 
 @Override
 public void visitEnd() {
 super.visitEnd();
 if (fia != null) {
 if (generateBody(false)) {
 // native method
 super.visitMaxs(1, 0);
                    }
 FindInClass.this.superField(
 Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
 "$$fn$$" + name + "_" + found,
 "Lorg/netbeans/html/boot/spi/Fn;",
 null, null
                    );
                }
            }


 private final class FindInAnno extends AnnotationVisitor {


 List<String> args = new ArrayList<String>();
 String body;
 boolean javacall = false;
 boolean wait4js = true;
 boolean keepAlive = true;


 public FindInAnno() {
 super(Opcodes.ASM5);
                }


 @Override
 public void visit(String name, Object value) {
 if (name == null) {
 args.add((String) value);
 return;
                    }
 if (name.equals("javacall")) { // NOI18N
 javacall = (Boolean) value;
 return;
                    }
 if (name.equals("wait4js")) { // NOI18N
 wait4js = (Boolean) value;
 return;
                    }
 if (name.equals("keepAlive")) { // NOI18N
 keepAlive = (Boolean) value;
 return;
                    }
 assert name.equals("body"); // NOI18N
 body = (String) value;
                }


 @Override
 public AnnotationVisitor visitArray(String name) {
 return this;
                }


 @Override
 public void visitEnd() {
 if (body != null) {
 generateJSBody(this);
                    }
                }
            }
        }