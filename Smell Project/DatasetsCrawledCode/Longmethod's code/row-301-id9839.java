 @Override
 public void main(List<JCCompilationUnit> trees) {
 // complete the javac AST with a completed ceylon model
 timer.startTask("prepareForTypeChecking");
 prepareForTypeChecking(trees);
 timer.endTask();
 List<JCCompilationUnit> javaTrees = List.nil();
 List<JCCompilationUnit> ceylonTrees = List.nil();
 // split them in two sets: java and ceylon
 for(JCCompilationUnit tree : trees){
 if(tree instanceof CeylonCompilationUnit)
 ceylonTrees = ceylonTrees.prepend(tree);
 else
 javaTrees = javaTrees.prepend(tree);
        }
 timer.startTask("Enter on Java trees");
 boolean needsModelReset = isBootstrap;
 // enter java trees first to set up their ClassSymbol objects for ceylon trees to use during type-checking
 if(!javaTrees.isEmpty()){
 setupImportedPackagesForJavaTrees(javaTrees);
 hasJavaAndCeylonSources = true;
 needsModelReset = true;
        }
 // this is false if we're in an APT round where we did not generate the trees
 if(!compiler.isAddModuleTrees()){
 setupImportedPackagesForJavaTrees(ceylonTrees);
        }
 if(isBootstrap || hasJavaAndCeylonSources){
 super.main(trees);
        }
 // now we can type-check the Ceylon code
 List<JCCompilationUnit> packageInfo = completeCeylonTrees(trees);
 trees = trees.prependList(packageInfo);
 ceylonTrees = ceylonTrees.prependList(packageInfo);
 
 if(compiler.isHadRunTwiceException()){
 needsModelReset = true;
        }
 if(needsModelReset){
 // bootstrapping the language module is a bit more complex
 resetAndRunEnterAgain(trees);
        }else{
 timer.startTask("Enter on Ceylon trees");
 // and complete their new trees
 try {
 sourceLanguage.push(Language.CEYLON);
 super.main(ceylonTrees);
            } finally {
 sourceLanguage.pop();
            }
 timer.endTask();
        }
    }