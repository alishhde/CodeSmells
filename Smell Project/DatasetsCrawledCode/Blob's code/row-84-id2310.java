public class FastRTrace {


 static final class Helper extends RBaseNode {
 @Child private GetFunctions.Get getNode;
 @Child private EnvFunctions.TopEnv topEnv;
 @Child private FrameFunctions.ParentFrame parentFrame;


 protected Object getWhere(VirtualFrame frame) {
 if (topEnv == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 topEnv = insert(TopEnvNodeGen.create());
            }
 if (parentFrame == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 parentFrame = insert(ParentFrameNodeGen.create());
            }
 return topEnv.execute(frame, parentFrame.execute(frame, 1), RNull.instance);
        }


 protected Object getFunction(VirtualFrame frame, Object what, Object where) {
 if (getNode == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 getNode = insert(GetNodeGen.create());
            }
 return getNode.execute(frame, what, where, RType.Function.getName(), true);
        }


 protected void checkWhat(Object what) {
 if (what == RMissing.instance) {
 throw error(RError.Message.ARGUMENT_MISSING, "what");
            }
        }


 protected RFunction checkFunction(Object what) {
 if (what instanceof RFunction) {
 RFunction func = (RFunction) what;
 if (func.isBuiltin()) {
 throw error(RError.Message.GENERIC, "builtin functions cannot be traced");
                } else {
 return func;
                }
            } else {
 throw error(RError.Message.ARG_MUST_BE_CLOSURE);
            }
        }
    }


 @RBuiltin(name = ".fastr.trace", visibility = CUSTOM, kind = PRIMITIVE, parameterNames = {"what", "tracer", "exit", "at", "print", "signature", "where"}, behavior = COMPLEX)
 public abstract static class Trace extends RBuiltinNode.Arg7 {


 @Child private TraceFunctions.PrimTrace primTrace;
 @Child private CastLogicalNode castLogical;
 @Child private SetVisibilityNode visibility = SetVisibilityNode.create();
 @Child private Helper helper = new Helper();


 static {
 Casts.noCasts(Trace.class);
        }


 @Specialization
 protected Object trace(VirtualFrame frame, Object whatObj, Object tracer, Object exit, Object at, Object printObj, Object signature, Object whereObj) {
 Object what = whatObj;
 helper.checkWhat(what);
 Object where = whereObj;
 if (where == RMissing.instance) {
 where = helper.getWhere(frame);
            }
 String funcName = RRuntime.asString(what);
 if (funcName != null) {
 what = helper.getFunction(frame, what, where);
            }
 RFunction func = helper.checkFunction(what);


 if (tracer == RMissing.instance && exit == RMissing.instance && at == RMissing.instance && printObj == RMissing.instance && signature == RMissing.instance) {
 // simple case, nargs() == 1, corresponds to .primTrace that has invisible output
 if (primTrace == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 primTrace = insert(PrimTraceNodeGen.create());
                }


 Object result = primTrace.execute(frame, func);
 visibility.execute(frame, false);
 return result;
            }


 if (at != RMissing.instance) {
 throw RError.nyi(this, "'at'");
            }
 boolean print = true;
 if (printObj != RMissing.instance) {
 if (castLogical == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 castLogical = insert(CastLogicalNodeGen.create(false, false, false));
                }
 print = RRuntime.fromLogical((byte) castLogical.doCast(printObj));
            }
 complexCase(func, tracer, exit, at, print, signature);
 visibility.execute(frame, true);
 return Utils.toString(func);
        }


 @TruffleBoundary
 private void complexCase(RFunction func, Object tracerObj, @SuppressWarnings("unused") Object exit, Object at, boolean print, @SuppressWarnings("unused") Object signature) {
 // the complex case
 RPairList tracer;
 if (tracerObj instanceof RFunction) {
 Closure closure = Closure.createLanguageClosure(RASTUtils.createCall(tracerObj, false, ArgumentsSignature.empty(0)).asRNode());
 tracer = RDataFactory.createLanguage(closure);
            } else if ((tracerObj instanceof RPairList && ((RPairList) tracerObj).isLanguage())) {
 tracer = (RPairList) tracerObj;
            } else {
 throw error(RError.Message.GENERIC, "tracer is unexpected type");
            }
 TraceHandling.enableStatementTrace(func, tracer, at, print);
        }
    }


 @RBuiltin(name = ".fastr.untrace", visibility = OFF, kind = PRIMITIVE, parameterNames = {"what", "signature", "where"}, behavior = COMPLEX)
 public abstract static class Untrace extends RBuiltinNode.Arg3 {


 @Child private TraceFunctions.PrimUnTrace primUnTrace;
 @Child private Helper helper = new Helper();


 static {
 Casts.noCasts(Untrace.class);
        }


 @Specialization
 protected Object untrace(VirtualFrame frame, Object whatObj, Object signature, Object whereObj) {
 Object what = whatObj;
 helper.checkWhat(what);
 Object where = whereObj;
 if (where == RMissing.instance) {
 where = helper.getWhere(frame);
            }
 String funcName = RRuntime.asString(what);
 if (funcName != null) {
 what = helper.getFunction(frame, what, where);
            }
 RFunction func = helper.checkFunction(what);
 if (signature == RMissing.instance) {
 if (primUnTrace == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 primUnTrace = insert(PrimUnTraceNodeGen.create());
                }
 primUnTrace.execute(frame, func);
            } else {
 throw RError.nyi(this, "method tracing");
            }


 return Utils.toString(func);
        }
    }
}