 private void handleChainFromFilter(
 StreamTypeRecord streamType,
 MethodInvocationTree observableDotFilter,
 Tree filterMethodOrLambda,
 VisitorState state) {
 MethodInvocationTree outerCallInChain = observableDotFilter;
 if (outerCallInChain == null) {
 return;
    }
 // Traverse the observable call chain out through any pass-through methods
 do {
 outerCallInChain = observableOuterCallInChain.get(outerCallInChain);
 // Check for a map method (which might be a pass-through method or the first method after a
 // pass-through chain)
 MethodInvocationTree mapCallsite = observableOuterCallInChain.get(observableDotFilter);
 if (observableCallToInnerMethodOrLambda.containsKey(outerCallInChain)) {
 // Update mapToFilterMap
 Symbol.MethodSymbol mapMethod = ASTHelpers.getSymbol(outerCallInChain);
 if (streamType.isMapMethod(mapMethod)) {
 MaplikeToFilterInstanceRecord record =
 new MaplikeToFilterInstanceRecord(
 streamType.getMaplikeMethodRecord(mapMethod), filterMethodOrLambda);
 mapToFilterMap.put(observableCallToInnerMethodOrLambda.get(outerCallInChain), record);
        }
      }
    } while (outerCallInChain != null
        && streamType.matchesType(ASTHelpers.getReceiverType(outerCallInChain), state)
        && streamType.isPassthroughMethod(ASTHelpers.getSymbol(outerCallInChain)));
  }