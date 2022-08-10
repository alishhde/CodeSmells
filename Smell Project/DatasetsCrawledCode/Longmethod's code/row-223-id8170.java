 private void resizeInstructions() {
 byte[] b = code.data; // bytecode of the method
 int u, v, label; // indexes in b
 int i, j; // loop indexes
 /*
         * 1st step: As explained above, resizing an instruction may require to
         * resize another one, which may require to resize yet another one, and
         * so on. The first step of the algorithm consists in finding all the
         * instructions that need to be resized, without modifying the code.
         * This is done by the following "fix point" algorithm:
         * 
         * Parse the code to find the jump instructions whose offset will need
         * more than 2 bytes to be stored (the future offset is computed from
         * the current offset and from the number of bytes that will be inserted
         * or removed between the source and target instructions). For each such
         * instruction, adds an entry in (a copy of) the indexes and sizes
         * arrays (if this has not already been done in a previous iteration!).
         * 
         * If at least one entry has been added during the previous step, go
         * back to the beginning, otherwise stop.
         * 
         * In fact the real algorithm is complicated by the fact that the size
         * of TABLESWITCH and LOOKUPSWITCH instructions depends on their
         * position in the bytecode (because of padding). In order to ensure the
         * convergence of the algorithm, the number of bytes to be added or
         * removed from these instructions is over estimated during the previous
         * loop, and computed exactly only after the loop is finished (this
         * requires another pass to parse the bytecode of the method).
         */
 int[] allIndexes = new int[0]; // copy of indexes
 int[] allSizes = new int[0]; // copy of sizes
 boolean[] resize; // instructions to be resized
 int newOffset; // future offset of a jump instruction


 resize = new boolean[code.length];


 // 3 = loop again, 2 = loop ended, 1 = last pass, 0 = done
 int state = 3;
 do {
 if (state == 3) {
 state = 2;
            }
 u = 0;
 while (u < b.length) {
 int opcode = b[u] & 0xFF; // opcode of current instruction
 int insert = 0; // bytes to be added after this instruction


 switch (ClassWriter.TYPE[opcode]) {
 case ClassWriter.NOARG_INSN:
 case ClassWriter.IMPLVAR_INSN:
 u += 1;
 break;
 case ClassWriter.LABEL_INSN:
 if (opcode > 201) {
 // converts temporary opcodes 202 to 217, 218 and
 // 219 to IFEQ ... JSR (inclusive), IFNULL and
 // IFNONNULL
 opcode = opcode < 218 ? opcode - 49 : opcode - 20;
 label = u + readUnsignedShort(b, u + 1);
                    } else {
 label = u + readShort(b, u + 1);
                    }
 newOffset = getNewOffset(allIndexes, allSizes, u, label);
 if (newOffset < Short.MIN_VALUE
                            || newOffset > Short.MAX_VALUE) {
 if (!resize[u]) {
 if (opcode == Opcodes.GOTO || opcode == Opcodes.JSR) {
 // two additional bytes will be required to
 // replace this GOTO or JSR instruction with
 // a GOTO_W or a JSR_W
 insert = 2;
                            } else {
 // five additional bytes will be required to
 // replace this IFxxx <l> instruction with
 // IFNOTxxx <l'> GOTO_W <l>, where IFNOTxxx
 // is the "opposite" opcode of IFxxx (i.e.,
 // IFNE for IFEQ) and where <l'> designates
 // the instruction just after the GOTO_W.
 insert = 5;
                            }
 resize[u] = true;
                        }
                    }
 u += 3;
 break;
 case ClassWriter.LABELW_INSN:
 u += 5;
 break;
 case ClassWriter.TABL_INSN:
 if (state == 1) {
 // true number of bytes to be added (or removed)
 // from this instruction = (future number of padding
 // bytes - current number of padding byte) -
 // previously over estimated variation =
 // = ((3 - newOffset%4) - (3 - u%4)) - u%4
 // = (-newOffset%4 + u%4) - u%4
 // = -(newOffset & 3)
 newOffset = getNewOffset(allIndexes, allSizes, 0, u);
 insert = -(newOffset & 3);
                    } else if (!resize[u]) {
 // over estimation of the number of bytes to be
 // added to this instruction = 3 - current number
 // of padding bytes = 3 - (3 - u%4) = u%4 = u & 3
 insert = u & 3;
 resize[u] = true;
                    }
 // skips instruction
 u = u + 4 - (u & 3);
 u += 4 * (readInt(b, u + 8) - readInt(b, u + 4) + 1) + 12;
 break;
 case ClassWriter.LOOK_INSN:
 if (state == 1) {
 // like TABL_INSN
 newOffset = getNewOffset(allIndexes, allSizes, 0, u);
 insert = -(newOffset & 3);
                    } else if (!resize[u]) {
 // like TABL_INSN
 insert = u & 3;
 resize[u] = true;
                    }
 // skips instruction
 u = u + 4 - (u & 3);
 u += 8 * readInt(b, u + 4) + 8;
 break;
 case ClassWriter.WIDE_INSN:
 opcode = b[u + 1] & 0xFF;
 if (opcode == Opcodes.IINC) {
 u += 6;
                    } else {
 u += 4;
                    }
 break;
 case ClassWriter.VAR_INSN:
 case ClassWriter.SBYTE_INSN:
 case ClassWriter.LDC_INSN:
 u += 2;
 break;
 case ClassWriter.SHORT_INSN:
 case ClassWriter.LDCW_INSN:
 case ClassWriter.FIELDORMETH_INSN:
 case ClassWriter.TYPE_INSN:
 case ClassWriter.IINC_INSN:
 u += 3;
 break;
 case ClassWriter.ITFMETH_INSN:
 case ClassWriter.INDYMETH_INSN:
 u += 5;
 break;
 // case ClassWriter.MANA_INSN:
 default:
 u += 4;
 break;
                }
 if (insert != 0) {
 // adds a new (u, insert) entry in the allIndexes and
 // allSizes arrays
 int[] newIndexes = new int[allIndexes.length + 1];
 int[] newSizes = new int[allSizes.length + 1];
 System.arraycopy(allIndexes, 0, newIndexes, 0,
 allIndexes.length);
 System.arraycopy(allSizes, 0, newSizes, 0, allSizes.length);
 newIndexes[allIndexes.length] = u;
 newSizes[allSizes.length] = insert;
 allIndexes = newIndexes;
 allSizes = newSizes;
 if (insert > 0) {
 state = 3;
                    }
                }
            }
 if (state < 3) {
                --state;
            }
        } while (state != 0);


 // 2nd step:
 // copies the bytecode of the method into a new bytevector, updates the
 // offsets, and inserts (or removes) bytes as requested.


 ByteVector newCode = new ByteVector(code.length);


 u = 0;
 while (u < code.length) {
 int opcode = b[u] & 0xFF;
 switch (ClassWriter.TYPE[opcode]) {
 case ClassWriter.NOARG_INSN:
 case ClassWriter.IMPLVAR_INSN:
 newCode.putByte(opcode);
 u += 1;
 break;
 case ClassWriter.LABEL_INSN:
 if (opcode > 201) {
 // changes temporary opcodes 202 to 217 (inclusive), 218
 // and 219 to IFEQ ... JSR (inclusive), IFNULL and
 // IFNONNULL
 opcode = opcode < 218 ? opcode - 49 : opcode - 20;
 label = u + readUnsignedShort(b, u + 1);
                } else {
 label = u + readShort(b, u + 1);
                }
 newOffset = getNewOffset(allIndexes, allSizes, u, label);
 if (resize[u]) {
 // replaces GOTO with GOTO_W, JSR with JSR_W and IFxxx
 // <l> with IFNOTxxx <l'> GOTO_W <l>, where IFNOTxxx is
 // the "opposite" opcode of IFxxx (i.e., IFNE for IFEQ)
 // and where <l'> designates the instruction just after
 // the GOTO_W.
 if (opcode == Opcodes.GOTO) {
 newCode.putByte(200); // GOTO_W
                    } else if (opcode == Opcodes.JSR) {
 newCode.putByte(201); // JSR_W
                    } else {
 newCode.putByte(opcode <= 166 ? ((opcode + 1) ^ 1) - 1
                                : opcode ^ 1);
 newCode.putShort(8); // jump offset
 newCode.putByte(200); // GOTO_W
 // newOffset now computed from start of GOTO_W
 newOffset -= 3;
                    }
 newCode.putInt(newOffset);
                } else {
 newCode.putByte(opcode);
 newCode.putShort(newOffset);
                }
 u += 3;
 break;
 case ClassWriter.LABELW_INSN:
 label = u + readInt(b, u + 1);
 newOffset = getNewOffset(allIndexes, allSizes, u, label);
 newCode.putByte(opcode);
 newCode.putInt(newOffset);
 u += 5;
 break;
 case ClassWriter.TABL_INSN:
 // skips 0 to 3 padding bytes
 v = u;
 u = u + 4 - (v & 3);
 // reads and copies instruction
 newCode.putByte(Opcodes.TABLESWITCH);
 newCode.putByteArray(null, 0, (4 - newCode.length % 4) % 4);
 label = v + readInt(b, u);
 u += 4;
 newOffset = getNewOffset(allIndexes, allSizes, v, label);
 newCode.putInt(newOffset);
 j = readInt(b, u);
 u += 4;
 newCode.putInt(j);
 j = readInt(b, u) - j + 1;
 u += 4;
 newCode.putInt(readInt(b, u - 4));
 for (; j > 0; --j) {
 label = v + readInt(b, u);
 u += 4;
 newOffset = getNewOffset(allIndexes, allSizes, v, label);
 newCode.putInt(newOffset);
                }
 break;
 case ClassWriter.LOOK_INSN:
 // skips 0 to 3 padding bytes
 v = u;
 u = u + 4 - (v & 3);
 // reads and copies instruction
 newCode.putByte(Opcodes.LOOKUPSWITCH);
 newCode.putByteArray(null, 0, (4 - newCode.length % 4) % 4);
 label = v + readInt(b, u);
 u += 4;
 newOffset = getNewOffset(allIndexes, allSizes, v, label);
 newCode.putInt(newOffset);
 j = readInt(b, u);
 u += 4;
 newCode.putInt(j);
 for (; j > 0; --j) {
 newCode.putInt(readInt(b, u));
 u += 4;
 label = v + readInt(b, u);
 u += 4;
 newOffset = getNewOffset(allIndexes, allSizes, v, label);
 newCode.putInt(newOffset);
                }
 break;
 case ClassWriter.WIDE_INSN:
 opcode = b[u + 1] & 0xFF;
 if (opcode == Opcodes.IINC) {
 newCode.putByteArray(b, u, 6);
 u += 6;
                } else {
 newCode.putByteArray(b, u, 4);
 u += 4;
                }
 break;
 case ClassWriter.VAR_INSN:
 case ClassWriter.SBYTE_INSN:
 case ClassWriter.LDC_INSN:
 newCode.putByteArray(b, u, 2);
 u += 2;
 break;
 case ClassWriter.SHORT_INSN:
 case ClassWriter.LDCW_INSN:
 case ClassWriter.FIELDORMETH_INSN:
 case ClassWriter.TYPE_INSN:
 case ClassWriter.IINC_INSN:
 newCode.putByteArray(b, u, 3);
 u += 3;
 break;
 case ClassWriter.ITFMETH_INSN:
 case ClassWriter.INDYMETH_INSN:
 newCode.putByteArray(b, u, 5);
 u += 5;
 break;
 // case MANA_INSN:
 default:
 newCode.putByteArray(b, u, 4);
 u += 4;
 break;
            }
        }


 // recomputes the stack map frames
 if (frameCount > 0) {
 if (compute == FRAMES) {
 frameCount = 0;
 stackMap = null;
 previousFrame = null;
 frame = null;
 Frame f = new Frame();
 f.owner = labels;
 Type[] args = Type.getArgumentTypes(descriptor);
 f.initInputFrame(cw, access, args, maxLocals);
 visitFrame(f);
 Label l = labels;
 while (l != null) {
 /*
                     * here we need the original label position. getNewOffset
                     * must therefore never have been called for this label.
                     */
 u = l.position - 3;
 if ((l.status & Label.STORE) != 0 || (u >= 0 && resize[u])) {
 getNewOffset(allIndexes, allSizes, l);
 // TODO update offsets in UNINITIALIZED values
 visitFrame(l.frame);
                    }
 l = l.successor;
                }
            } else {
 /*
                 * Resizing an existing stack map frame table is really hard.
                 * Not only the table must be parsed to update the offets, but
                 * new frames may be needed for jump instructions that were
                 * inserted by this method. And updating the offsets or
                 * inserting frames can change the format of the following
                 * frames, in case of packed frames. In practice the whole table
                 * must be recomputed. For this the frames are marked as
                 * potentially invalid. This will cause the whole class to be
                 * reread and rewritten with the COMPUTE_FRAMES option (see the
                 * ClassWriter.toByteArray method). This is not very efficient
                 * but is much easier and requires much less code than any other
                 * method I can think of.
                 */
 cw.invalidFrames = true;
            }
        }
 // updates the exception handler block labels
 Handler h = firstHandler;
 while (h != null) {
 getNewOffset(allIndexes, allSizes, h.start);
 getNewOffset(allIndexes, allSizes, h.end);
 getNewOffset(allIndexes, allSizes, h.handler);
 h = h.next;
        }
 // updates the instructions addresses in the
 // local var and line number tables
 for (i = 0; i < 2; ++i) {
 ByteVector bv = i == 0 ? localVar : localVarType;
 if (bv != null) {
 b = bv.data;
 u = 0;
 while (u < bv.length) {
 label = readUnsignedShort(b, u);
 newOffset = getNewOffset(allIndexes, allSizes, 0, label);
 writeShort(b, u, newOffset);
 label += readUnsignedShort(b, u + 2);
 newOffset = getNewOffset(allIndexes, allSizes, 0, label)
                            - newOffset;
 writeShort(b, u + 2, newOffset);
 u += 10;
                }
            }
        }
 if (lineNumber != null) {
 b = lineNumber.data;
 u = 0;
 while (u < lineNumber.length) {
 writeShort(
 b,
 u,
 getNewOffset(allIndexes, allSizes, 0,
 readUnsignedShort(b, u)));
 u += 4;
            }
        }
 // updates the labels of the other attributes
 Attribute attr = cattrs;
 while (attr != null) {
 Label[] labels = attr.getLabels();
 if (labels != null) {
 for (i = labels.length - 1; i >= 0; --i) {
 getNewOffset(allIndexes, allSizes, labels[i]);
                }
            }
 attr = attr.next;
        }


 // replaces old bytecodes with new ones
 code = newCode;
    }