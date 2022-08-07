 class Cel4rreg {


 long seghigh;
 long seglow;
 int p_dsafmt = -1;
 long p_dsaptr;
 RegisterSet regs;


 /**
         * Creates the instance and attempts to locate the registers.
         */
 Cel4rreg() {
 /* Debug option - before we do anything else, try using the old svcdump code */
 String useSvcdump = System.getProperty("zebedee.use.svcdump");
 if (useSvcdump != null && useSvcdump.equals("true")) {
 getRegistersFromSvcdump();
 return;
            }


 /*
             * Try and get the registers from the following locations:
             *
             * 1) RTM2 work area
             * 2) BPXGMSTA service
             * 3) linkage stack entries
             * 4) TCB
             * 5) Usta
             *
             * if any succeeds we return otherwise move to the next location.
             */


 int whereCount = 0;


 try {
 if ((regs = getRegistersFromRTM2()) != null && whereCount++ >= whereSkip) {
 whereFound = "RTM2";
 failingRegisters = regs;
 registers = regs;
 return;
                }
            } catch (IOException e) {
 throw new Error("oops: " + e);
            }
 /* If we still have not found a dsa, invoke kernel svs */
 try {
 if ((regs = getRegistersFromBPXGMSTA()) != null && whereCount++ >= whereSkip) {
 whereFound = regs.whereFound();
 if (whereFound == null)
 whereFound = "BPXGMSTA";
 if (tcb.tcbcmp() != 0)
 failingRegisters = regs;
 registers = regs;
 return;
                }
            } catch (IOException e) {
 //throw new Error("oops: " + e);
            }
 try {
 if ((regs = getRegistersFromLinkageStack()) != null && whereCount++ >= whereSkip) {
 whereFound = "Linkage";
 if (tcb.tcbcmp() != 0)
 failingRegisters = regs;
 registers = regs;
 return;
                }
            } catch (IOException e) {
 log.logp(Level.WARNING,"com.ibm.j9ddr.corereaders.tdump.zebedee.le.Caa.Cel4rreg", "Cel4rreg","Unexepected exception", e);
 throw new Error("Unexpected IOException: " + e);
            }
 try {
 if ((regs = getRegistersFromTCB()) != null && whereCount++ >= whereSkip) {
 whereFound = "TCB";
 if (tcb.tcbcmp() != 0)
 failingRegisters = regs;
 registers = regs;
 return;
                }
            } catch (IOException e) {
 throw new Error("oops: " + e);
            }
 try {
 if (is64bit) {
 /* This is from celqrreg.plx370: "Get the save R4 from a NOSTACK call" */
 long lca = CeexlaaTemplate.getCeelaa_lca64(inputStream, laa);
 p_dsaptr = CeelcaTemplate.getCeelca_savstack(inputStream, lca);
 log.fine("p_dsaptr from lca = " + hex(p_dsaptr));
 p_dsafmt = stackdirection = CEECAASTACK_DOWN;
 if (validateDSA() == 0 && whereCount++ >= whereSkip) {
 whereFound = "LCA";
 return;
                    }
                }
            } catch (IOException e) {
 throw new Error("oops: " + e);
            }
 /* Last ditch */
 try {
 if ((regs = getRegistersFromUsta()) != null && whereCount++ >= whereSkip) {
 whereFound = regs.whereFound();
 if (tcb.tcbcmp() != 0)
 failingRegisters = regs;
 registers = regs;
 return;
                }
            } catch (IOException e) {
            }
 whereFound = "not found";
        }


 /**
         * Try and get the registers from the RTM2 work area. Returns null if none found. As a
         * side-effect it also sets the stackdirection.
         */
 private RegisterSet getRegistersFromRTM2() throws IOException {
 int level = ceecaalevel();
 log.finer("caa level is " + level);
 /* If the CAA level is 13 or greater, get stack direction from 
             * CAA.  For older releases or the dummy CAA, default stack 
             * direction to UP.
             */
 if (is64bit) {
 /* Always use downstack in 64-bit mode? */
 stackdirection = CEECAASTACK_DOWN;
 log.finer("stack direction is down");
            } else if (level >= 13) { /* If LE 2.10 or higher */
 /* Obtain dsa format from the CAA */
 stackdirection = ceecaa_stackdirection();
 log.finer("stack direction is " + (stackdirection == CEECAASTACK_UP ? "up" : "down"));
            } else {
 stackdirection = CEECAASTACK_UP;
 log.finer("stack direction is up");
            }
 if ((stackdirection == CEECAASTACK_DOWN) && !is64bit) {
 try {
 long tempptr = ceecaasmcb();			//the ceecaasmcb call is not currently supported for 64 bit CAAs
 seghigh = SmcbTemplate.getSmcb_dsbos(inputStream, tempptr);
 seglow = CeexstkhTemplate.getStkh_stackfloor(inputStream, seghigh);
                } catch (Exception e) {
 //throw new Error("oops: " + e);
 return null;
                }
            }
 /* At this point, a valid CAA has been obtained. Access the RTM2 to obtain the DSA. */
 long rtm2ptr = tcb.tcbrtwa();
 if (rtm2ptr != 0) {
 try {
 log.finer("found some rtm2 registers");
 RegisterSet regs = new RegisterSet();
 long rtm2grs = rtm2ptr + Ihartm2aTemplate.getRtm2ereg$offset();
 long rtm2grshi = rtm2ptr + Ihartm2aTemplate.getRtm2g64h$offset();
 for (int i = 0; i < 16; i++) {
 long low = space.readUnsignedInt(rtm2grs + i*4);
 long high = is64bit ? space.readUnsignedInt(rtm2grshi + i*4) : 0;
 regs.setRegister(i, (high << 32) | low);
                    }
 long rtm2psw = rtm2ptr + Ihartm2aTemplate.getRtm2apsw$offset();
 regs.setPSW(space.readLong(rtm2psw));
 if (registersValid(regs)) {
 log.finer("found good dsa in rtm2");
                    } else {
 log.finer("bad dsa in rtm2");
 regs = null;
                    }
 return regs;
                } catch (IOException e) {
 throw e;
                } catch (Exception e) {
 throw new Error("oops: " + e);
                }
            } else {
 log.finer("failed to get registers from rtm2");
 return null;
            }
        }


 /**
         * Validates the given register set with retry for down stack
         */
 private boolean registersValid(RegisterSet regs) throws IOException {
 if (regs == null)
 return false;
 p_dsafmt = stackdirection;
 if (p_dsafmt == CEECAASTACK_DOWN) {
 p_dsaptr = regs.getRegisterAsAddress(4);
 log.finer("p_dsaptr from reg 4 = " + hex(p_dsaptr));
            } else {
 p_dsaptr = regs.getRegisterAsAddress(13);
 log.finer("p_dsaptr from reg 13 = " + hex(p_dsaptr));
            }
 int lastrc = validateDSA();
 if (lastrc == 0) {
 log.finer("found valid dsa");
 return true;
            } else {
 if (stackdirection == CEECAASTACK_DOWN) {
 p_dsaptr = regs.getRegisterAsAddress(13);
 log.finer("p_dsaptr from reg 13 (again) = " + hex(p_dsaptr));
 p_dsafmt = CEECAASTACK_UP;
 lastrc = validateDSA();
 if (lastrc == WARNING) {
 lastrc = validateDSA();
 if (lastrc == 0) {
 log.finer("found valid dsa");
 return true;
                        }
                    }
                }
 /* reset values */
 log.finer("p_dsaptr invalid so reset: " + hex(p_dsaptr));
 p_dsaptr = 0;
            }
 return false;
        }


 /**
         * Try and get the registers from the BPXGMSTA service.
         */
 private RegisterSet getRegistersFromBPXGMSTA() throws IOException {
 RegisterSet regs = tcb.getRegistersFromBPXGMSTA();
 if (is64bit)
 // celqrreg appears to always assume down stack
 stackdirection = CEECAASTACK_DOWN;
 if (registersValid(regs)) {
 log.finer("found good dsa in BPXGMSTA");
 return regs;
            } else {
 log.finer("BPX registers are invalid so keep looking");
 return null;
            }
        }


 /**
         * Try and get the registers from the linkage stack.
         */
 private RegisterSet getRegistersFromLinkageStack() throws IOException {
 log.finer("enter getRegistersFromLinkageStack");
 try {
 Lse[] linkageStack = tcb.getLinkageStack();
 /* If Linkage stack is empty, leave */
 if (linkageStack.length == 0) {
 log.finer("empty linkage stack");
 return null;
                }
 for (int i = 0; i < linkageStack.length; i++) {
 Lse lse = linkageStack[i];
 if (lse.lses1pasn() == space.getAsid()) {
 RegisterSet regs = new RegisterSet();
 if (lse.isZArchitecture() && (lse.lses1typ7() == Lse.LSED1PC || lse.lses1typ7() == Lse.LSED1BAKR)) {
 log.finer("found some z arch registers");
 regs.setPSW(lse.lses1pswh());
 for (int j = 0; j < 16; j++) {
 regs.setRegister(j, lse.lses1grs(j));
                            }
                        } else {
 log.finer("found some non z arch registers");
 regs.setPSW(lse.lsespsw());
 for (int j = 0; j < 16; j++) {
 regs.setRegister(j, lse.lsesgrs(j));
                            }
                        }
 if (registersValid(regs)) {
 log.finer("found good dsa in linkage stack");
 return regs;
                        }
                    } else {
 log.finer("different asid: " + hex(lse.lses1pasn()));
                    }
                }
            } catch (IOException e) {
 throw e;
            } catch (Exception e) {
 throw new Error("oops: " + e);
            }
 log.finer("could not find registers in linkage stack");
 return null;
        }


 /**
         * Try and get the registers from the TCB.
         */
 private RegisterSet getRegistersFromTCB() throws IOException {
 log.finer("getRegistersFromTCB");
 RegisterSet regs = tcb.getRegisters();
 if (registersValid(regs)) {
 log.finer("found good dsa in TCB");
 return regs;
            } else {
 return null;
            }
        }


 /**
         * Try and get the registers from the Usta. Note that this is a kind of last-ditch
         * thing and so no validation is done.
         */
 private RegisterSet getRegistersFromUsta() throws IOException {
 log.fine("enter getRegistersFromUsta");
 RegisterSet regs = tcb.getRegistersFromUsta();
 if (registersValid(regs)) {
 log.finer("found good dsa in Usta");
 return regs;
            } else {
 /* If there are more than three stack entries that's probably better than nothing */
 boolean isDownStack = stackdirection == CEECAASTACK_DOWN;
 long dsaptr;
 if (isDownStack) {
 dsaptr = regs.getRegister(4);
 log.finer("p_dsaptr from reg 4 = " + hex(p_dsaptr));
                } else {
 dsaptr = regs.getRegister(13);
 log.finer("p_dsaptr from reg 13 = " + hex(p_dsaptr));
                }
 try {
 DsaStackFrame dsa = new DsaStackFrame(dsaptr, isDownStack, regs, space, Caa.this);
 int count = 0;
 for (; dsa != null; dsa = dsa.getParentFrame()) {
 if (++count > 3) {
 p_dsaptr = dsaptr;
 p_dsafmt = stackdirection;
 return regs;
                    	}
                    }
                } catch (IOException e) {
                } catch (AssertionError e) {
                }
            }
 return null;
        }


 /**
         * Try and get the registers using the old svcdump code. This is for debugging
         * purposes only. Uses reflection so there is no compilation dependency.
         */
 private void getRegistersFromSvcdump() {
        }


 /**
         * Validate the given DSA. Returns 0 if valid. Note because this is Java, we can't
         * modify the input parameters, so we use the instance variables instead and
         * val_dsa == p_dsaptr, val_dsafmt == p_dsafmt.
         */
 private int validateDSA() {
 log.finer("attempt to validate " + hex(p_dsaptr) + " on " + (p_dsafmt == CEECAASTACK_DOWN ? "down" : "up") + " stack");
 try {
 if (is64bit) {
 assert laa != 0;
 long l_sancptr = CeexlaaTemplate.getCeelaa_sanc64(inputStream, laa);
 assert l_sancptr != 0;
 long seghigh = CeexsancTemplate.getSanc_bos(inputStream, l_sancptr);
 long seglow = 0;
 long sanc_stack = CeexsancTemplate.getSanc_stack(inputStream, l_sancptr);
 long sanc_user_stack = CeexsancTemplate.getSanc_user_stack(inputStream, l_sancptr);
 if (sanc_stack == sanc_user_stack) {
 /* Get Stackfloor from sanc */
 seglow = CeexsancTemplate.getSanc_user_floor(inputStream, l_sancptr);
                    } else {
 /* Get StackFloor from LAA */
 seglow = CeexlaaTemplate.getCeelaa_stackfloor64(inputStream, laa);
                    }
 if (p_dsaptr < seghigh && (p_dsaptr + 0x800) >= seglow && (p_dsaptr & 0xf) == 0) {
 log.finer("dsa " + hex(p_dsaptr) + " is within seglow = " + hex(seglow) + " seghigh = " + hex(seghigh));
 return 0;
                    } else {
 log.finer("dsa " + hex(p_dsaptr) + " is NOT within seglow = " + hex(seglow) + " seghigh = " + hex(seghigh));
 return ERROR;
                    }
                }
 if (p_dsafmt == CEECAASTACK_DOWN) {
 /* the check for being in the current segment is commented out */
                } else {
 if (is64bit)
 return ERROR;
 long tptr = ceecaaerrcm();
 /* Chicken egg situation */
 //assert !space.is64bit();
 /* If the input DSA address is within the HCOM and double word aligned, 
                     * assume that it is good. */
 if (p_dsaptr < (tptr + hcomLength) && p_dsaptr >= tptr && (p_dsaptr & 7) == 0) {
 log.finer("upstack dsa " + hex(p_dsaptr) + " is inside hcom");
 return 0;
                    }
                }
 long ddsa = ceecaaddsa();
 long dsaptr = p_dsaptr;
 int dsafmt8 = p_dsafmt;
 long slowdsaptr = p_dsaptr;
 int slowdsafmt8 = p_dsafmt;
 for (boolean slow = false;; slow = !slow) {
 Ceexdsaf dsaf = new Ceexdsaf(space, dsaptr, dsafmt8, is64bit);
 /* If the stack direction is down but we are validating an upstack DSA
                     * and the current DSA is inside the current segment of the down stack,
                     * assume this must be a OS_NOSTACK call, return WARNING and replace
                     * input DSA and DSAFmt with R4 value from this DSA */
 log.finer("looping with dsa = " + hex(dsaptr));
 if (stackdirection == CEECAASTACK_DOWN && p_dsafmt == CEECAASTACK_UP &&
 dsaptr < seghigh && dsaptr >= seglow) {
 p_dsaptr = CeedsaTemplate.getCeedsar4(inputStream, dsaptr);
 p_dsafmt = CEECAASTACK_DOWN;
 log.finer("warning, try switching to down stack");
 return WARNING;
                    }
 long callers_dsaptr = dsaf.DSA_Prev;
 dsafmt8 = dsaf.DSA_Format;
 /* If we are not able to backchain any farther or we have encountered
                     * a linkage stack, assume that the input DSA address is bad. */
 if (callers_dsaptr == 0 || callers_dsaptr == F1SA) {
 log.finer("cannot backchain futher because " + (callers_dsaptr == 0 ? "zero" : "linkage stack") + " found");
 return ERROR;
                    }
 /* If we were able to backchain to the dummy DSA, the input DSA address
                     * must be good. */
 if (callers_dsaptr == ddsa) {
 log.finer("dummy dsa reached");
 return 0;
                    }
 /* If we backchained across a stack transition, assume that the input
                     * DSA address is good. */
 if (dsafmt8 != p_dsafmt) {
 log.finer("backchained across a stack transition");
 return 0;
                    }
 /* If we have located an upstack DSA with a valid NAB value, assume that
                     * the input DSA address is good. */
 if (dsafmt8 == CEECAASTACK_UP) {
 long tptr = CeedsaTemplate.getCeedsanab(inputStream, callers_dsaptr);
 if (tptr == dsaptr) {
 log.finer("upstack DSA is good");
 return 0;
                        }
                    }
 dsaptr = callers_dsaptr;
 /* We use the Tortoise and the Hare algorithm to detect loops. If the slow
                     * iterator is lapped it means there is a loop. */
 if (slow) {
 dsaf = new Ceexdsaf(space, slowdsaptr, slowdsafmt8, is64bit);
 slowdsaptr = dsaf.DSA_Prev;
 slowdsafmt8 = dsaf.DSA_Format;
                    }
 if (dsaptr == slowdsaptr) {
 log.finer("loop detected in DSA chain");
 return ERROR;
                    }
                }
            } catch (IOException e) {
 /* Any bad read means the DSA was invalid */
 log.logp(Level.FINER,"com.ibm.j9ddr.corereaders.tdump.zebedee.le.Caa.Cel4rreg", "validateDSA","Bad read", e);
 return ERROR;
            } catch (Exception e) {
 log.logp(Level.WARNING,"com.ibm.j9ddr.corereaders.tdump.zebedee.le.Caa.Cel4rreg", "validateDSA","Unexepected exception", e);
 throw new Error("Unexpected Exception:: " + e);
            }
        }
    }