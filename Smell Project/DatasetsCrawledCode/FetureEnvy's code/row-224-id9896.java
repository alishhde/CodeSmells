 @Override
 public final Description matchClass(ClassTree classTree, VisitorState state) {
 if (!HAS_CONSTRUCTORS_WITH_INJECT.matches(classTree, state)) {
 return Description.NO_MATCH;
    }


 List<MethodTree> ctors = ASTHelpers.getConstructors(classTree);
 List<MethodTree> ctorsWithInject =
 ctors.stream()
            .filter(c -> hasInjectAnnotation().matches(c, state))
            .collect(toImmutableList());


 if (ctorsWithInject.size() != 1) {
 // Injection frameworks don't support multiple @Inject ctors.
 // There is already an ERROR check for it.
 // http://errorprone.info/bugpattern/MoreThanOneInjectableConstructor
 return Description.NO_MATCH;
    }


 // collect the assignments in ctor
 Set<Symbol> variablesAssigned = new HashSet<>();
 new TreeScanner<Void, Void>() {
 @Override
 public Void visitAssignment(AssignmentTree tree, Void unused) {
 Symbol symbol = ASTHelpers.getSymbol(tree.getVariable());
 // check if it is instance field.
 if (symbol != null && symbol.getKind() == ElementKind.FIELD && !symbol.isStatic()) {
 variablesAssigned.add(symbol);
        }
 return super.visitAssignment(tree, null);
      }
    }.scan((JCTree) getOnlyElement(ctorsWithInject), null);


 SuggestedFix.Builder fix = SuggestedFix.builder();
 VariableTree variableTreeFirstMatch = null;
 for (Tree member : classTree.getMembers()) {
 if (!(member instanceof VariableTree)) {
 continue;
      }
 VariableTree variableTree = (VariableTree) member;
 if (!INSTANCE_FIELD_WITH_INJECT.matches(variableTree, state)) {
 continue;
      }
 if (!variablesAssigned.contains(ASTHelpers.getSymbol(variableTree))) {
 continue;
      }
 variableTreeFirstMatch = variableTree;
 removeInjectAnnotationFromVariable(variableTree, state).ifPresent(fix::merge);
    }
 if (variableTreeFirstMatch == null) {
 return Description.NO_MATCH;
    }
 if (fix.isEmpty()) {
 return describeMatch(variableTreeFirstMatch);
    }
 return describeMatch(variableTreeFirstMatch, fix.build());
  }