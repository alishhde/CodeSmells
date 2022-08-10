 private static void emitStackMove(CompilationResultBuilder crb, AArch64MacroAssembler masm, AllocatableValue result, Value input) {
 try (ScratchRegister r1 = masm.getScratchRegister()) {
 try (ScratchRegister r2 = masm.getScratchRegister()) {
 Register rscratch1 = r1.getRegister();
 Register rscratch2 = r2.getRegister();
 // use the slot kind to define the operand size
 PlatformKind kind = input.getPlatformKind();
 final int size = kind.getSizeInBytes() * Byte.SIZE;


 // Always perform stack -> stack copies through integer registers
 crb.blockComment("[stack -> stack copy]");
 AArch64Address src = loadStackSlotAddress(crb, masm, asStackSlot(input), rscratch2);
 masm.ldr(size, rscratch1, src);
 AArch64Address dst = loadStackSlotAddress(crb, masm, asStackSlot(result), rscratch2);
 masm.str(size, rscratch1, dst);
            }
        }
    }