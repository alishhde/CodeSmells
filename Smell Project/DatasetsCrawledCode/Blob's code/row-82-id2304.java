public class TruffleNFI_DLL implements DLLRFFI {


 public static final class NFIHandle implements LibHandle {
 @SuppressWarnings("unused") private final String libName;
 final TruffleObject libHandle;


 NFIHandle(String libName, TruffleObject libHandle) {
 this.libName = libName;
 this.libHandle = libHandle;
        }


 @Override
 public Type getRFFIType() {
 return RFFIFactory.Type.NFI;
        }
    }


 private static final class TruffleNFI_DLOpenNode extends Node implements DLLRFFI.DLOpenNode {


 @Override
 @TruffleBoundary
 public LibHandle execute(String path, boolean local, boolean now) {
 String librffiPath = LibPaths.getBuiltinLibPath("R");
 // Do not call before/afterDowncall when loading libR to prevent the pushing/popping of
 // the callback array, which requires that the libR have already been loaded
 boolean notifyStateRFFI = !librffiPath.equals(path);
 long before = notifyStateRFFI ? RContext.getInstance().getStateRFFI().beforeDowncall(RFFIFactory.Type.NFI) : 0;
 try {
 String libName = DLL.libName(path);
 Env env = RContext.getInstance().getEnv();
 TruffleObject libHandle = (TruffleObject) env.parse(Source.newBuilder("nfi", prepareLibraryOpen(path, local, now), path).build()).call();
 return new NFIHandle(libName, libHandle);
            } finally {
 if (notifyStateRFFI) {
 RContext.getInstance().getStateRFFI().afterDowncall(before, RFFIFactory.Type.NFI);
                }
            }
        }
    }


 @TruffleBoundary
 private static String prepareLibraryOpen(String path, boolean local, boolean now) {
 StringBuilder sb = new StringBuilder("load");
 sb.append("(");
 sb.append(local ? "RTLD_LOCAL" : "RTLD_GLOBAL");
 sb.append('|');
 sb.append(now ? "RTLD_NOW" : "RTLD_LAZY");
 sb.append(") \"");
 sb.append(path);
 sb.append('"');
 return sb.toString();
    }


 private static class TruffleNFI_DLSymNode extends Node implements DLLRFFI.DLSymNode {


 @Child private Node lookupSymbol;


 @Override
 @TruffleBoundary
 public SymbolHandle execute(Object handle, String symbol) {
 assert handle instanceof NFIHandle;
 NFIHandle nfiHandle = (NFIHandle) handle;
 if (lookupSymbol == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 lookupSymbol = insert(Message.READ.createNode());
            }
 try {
 TruffleObject result = (TruffleObject) ForeignAccess.sendRead(lookupSymbol, nfiHandle.libHandle, symbol);
 return new SymbolHandle(result);
            } catch (UnknownIdentifierException e) {
 throw new UnsatisfiedLinkError();
            } catch (InteropException e) {
 throw RInternalError.shouldNotReachHere();
            }
        }
    }


 private static class TruffleNFI_DLCloseNode extends Node implements DLLRFFI.DLCloseNode {


 @Override
 public int execute(Object handle) {
 assert handle instanceof NFIHandle;
 // TODO
 return 0;
        }
    }


 @Override
 public DLOpenNode createDLOpenNode() {
 return new TruffleNFI_DLOpenNode();
    }


 @Override
 public DLSymNode createDLSymNode() {
 return new TruffleNFI_DLSymNode();
    }


 @Override
 public DLCloseNode createDLCloseNode() {
 return new TruffleNFI_DLCloseNode();
    }
}