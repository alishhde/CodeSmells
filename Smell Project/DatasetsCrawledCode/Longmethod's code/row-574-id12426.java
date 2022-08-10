 void setStackMap(StackMapTable_attribute attr) {
 if (attr == null) {
 map = null;
 return;
        }


 Method m = classWriter.getMethod();
 Descriptor d = m.descriptor;
 String[] args;
 try {
 ConstantPool cp = classWriter.getClassFile().constant_pool;
 String argString = d.getParameterTypes(cp);
 args = argString.substring(1, argString.length() - 1).split("[, ]+");
        } catch (ConstantPoolException | InvalidDescriptor e) {
 return;
        }
 boolean isStatic = m.access_flags.is(AccessFlags.ACC_STATIC);


 verification_type_info[] initialLocals = new verification_type_info[(isStatic ? 0 : 1) + args.length];
 if (!isStatic)
 initialLocals[0] = new CustomVerificationTypeInfo("this");
 for (int i = 0; i < args.length; i++) {
 initialLocals[(isStatic ? 0 : 1) + i] =
 new CustomVerificationTypeInfo(args[i].replace(".", "/"));
        }


 map = new HashMap<>();
 StackMapBuilder builder = new StackMapBuilder();


 // using -1 as the pc for the initial frame effectively compensates for
 // the difference in behavior for the first stack map frame (where the
 // pc offset is just offset_delta) compared to subsequent frames (where
 // the pc offset is always offset_delta+1).
 int pc = -1;


 map.put(pc, new StackMap(initialLocals, empty));


 for (int i = 0; i < attr.entries.length; i++)
 pc = attr.entries[i].accept(builder, pc);
    }