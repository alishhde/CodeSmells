 @Override
 protected void translateCore(final ITranslationEnvironment environment,
 final IInstruction instruction, final List<ReilInstruction> instructions) {
 final IOperandTreeNode registerOperand1 =
 instruction.getOperands().get(0).getRootNode().getChildren().get(0);
 final IOperandTreeNode registerOperand2 =
 instruction.getOperands().get(1).getRootNode().getChildren().get(0);
 final IOperandTreeNode registerOperand3 =
 instruction.getOperands().get(2).getRootNode().getChildren().get(0);
 final IOperandTreeNode registerOperand4 =
 instruction.getOperands().get(3).getRootNode().getChildren().get(0);


 final String targetRegister = (registerOperand1.getValue());
 final String sourceRegister1 = (registerOperand2.getValue());
 final String sourceRegister2 = (registerOperand3.getValue());
 final String sourceRegister3 = (registerOperand4.getValue());


 final OperandSize bt = OperandSize.BYTE;
 final OperandSize dw = OperandSize.DWORD;
 final OperandSize wd = OperandSize.WORD;


 long baseOffset = ReilHelpers.nextReilAddress(instruction, instructions);


 final String operand2 = environment.getNextVariableString();
 final String tmpRotate1 = environment.getNextVariableString();
 final String tmpRotate2 = environment.getNextVariableString();
 final String diffOfProducts = environment.getNextVariableString();
 final String operand2from15to0 = environment.getNextVariableString();
 final String operand2from31to16 = environment.getNextVariableString();
 final String registerRmfrom15to0 = environment.getNextVariableString();
 final String registerRmfrom31to16 = environment.getNextVariableString();
 final String tmpVar1 = environment.getNextVariableString();
 final String tmpVar2 = environment.getNextVariableString();
 final String tmpVar3 = environment.getNextVariableString();
 final String trueDiffOfProducts = environment.getNextVariableString();
 final String product1 = environment.getNextVariableString();
 final String product2 = environment.getNextVariableString();


 if (instruction.getMnemonic().contains("X")) {
 instructions.add(ReilHelpers.createBsh(baseOffset++, dw, sourceRegister2, bt,
 String.valueOf(-16), dw, tmpRotate1));
 instructions.add(ReilHelpers.createBsh(baseOffset++, dw, sourceRegister2, bt,
 String.valueOf(16), dw, tmpRotate2));
 instructions.add(ReilHelpers.createOr(baseOffset++, dw, tmpRotate1, dw, tmpRotate2, dw,
 operand2));
 instructions.add(ReilHelpers.createAnd(baseOffset++, dw, operand2, dw,
 String.valueOf(0xFFFFFFFFL), dw, operand2));
    } else {
 instructions.add(ReilHelpers.createStr(baseOffset++, dw, sourceRegister2, dw, operand2));
    }


 instructions.add(ReilHelpers.createAnd(baseOffset++, dw, operand2, dw, String.valueOf(0xFFFFL),
 dw, operand2from15to0));
 instructions.add(ReilHelpers.createAnd(baseOffset++, dw, sourceRegister1, dw,
 String.valueOf(0xFFFFL), dw, registerRmfrom15to0));


 Helpers.signedMul(baseOffset, environment, instruction, instructions, wd, operand2from15to0,
 wd, registerRmfrom15to0, dw, product1);
 baseOffset = ReilHelpers.nextReilAddress(instruction, instructions);


 instructions.add(ReilHelpers.createBsh(baseOffset++, dw, operand2, dw, String.valueOf(-16L),
 dw, tmpVar1));
 instructions.add(ReilHelpers.createAnd(baseOffset++, dw, tmpVar1, dw, String.valueOf(0xFFFFL),
 dw, operand2from31to16));
 instructions.add(ReilHelpers.createBsh(baseOffset++, dw, sourceRegister1, dw,
 String.valueOf(-16L), dw, tmpVar2));
 instructions.add(ReilHelpers.createAnd(baseOffset++, dw, tmpVar2, dw, String.valueOf(0xFFFFL),
 dw, registerRmfrom31to16));
 Helpers.signedMul(baseOffset, environment, instruction, instructions, wd, operand2from31to16,
 wd, registerRmfrom31to16, dw, product2);
 baseOffset = ReilHelpers.nextReilAddress(instruction, instructions);


 Helpers.signedSub(baseOffset, environment, instruction, instructions, product2, product1,
 diffOfProducts, trueDiffOfProducts);
 baseOffset = ReilHelpers.nextReilAddress(instruction, instructions);


 instructions.add(ReilHelpers.createAdd(baseOffset++, dw, sourceRegister3, dw, diffOfProducts,
 dw, tmpVar3));
 instructions.add(ReilHelpers.createAnd(baseOffset++, dw, tmpVar3, dw,
 String.valueOf(0xFFFFFFFFL), dw, targetRegister));


 Helpers.overflowCondition(baseOffset, environment, instruction, instructions, tmpVar3);
  }