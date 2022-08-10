 List<JCVariableDecl> freevarDefs(int pos, List<VarSymbol> freevars, Symbol owner,
 long additionalFlags) {
 long flags = FINAL | SYNTHETIC | additionalFlags;
 List<JCVariableDecl> defs = List.nil();
 Set<Name> proxyNames = new HashSet<>();
 for (List<VarSymbol> l = freevars; l.nonEmpty(); l = l.tail) {
 VarSymbol v = l.head;
 int index = 0;
 Name proxyName;
 do {
 proxyName = proxyName(v.name, index++);
            } while (!proxyNames.add(proxyName));
 VarSymbol proxy = new VarSymbol(
 flags, proxyName, v.erasure(types), owner);
 proxies.put(v, proxy);
 JCVariableDecl vd = make.at(pos).VarDef(proxy, null);
 vd.vartype = access(vd.vartype);
 defs = defs.prepend(vd);
        }
 return defs;
    }