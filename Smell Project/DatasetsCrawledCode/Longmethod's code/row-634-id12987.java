 private static void walker(List<List<String>> finalResult, final List<List<String>> input,
 List<String> listSoFar, final int level) throws SemanticException {
 // Base case.
 if (level == (input.size() - 1)) {
 assert (input.get(level) != null) : "Unique skewed element list has null list in " + level
            + "th position.";
 for (String v : input.get(level)) {
 List<String> oneCompleteIndex = new ArrayList<String>(listSoFar);
 oneCompleteIndex.add(v);
 finalResult.add(oneCompleteIndex);
        }
 return;
      }


 // Recursive.
 for (String v : input.get(level)) {
 List<String> clonedListSoFar = new ArrayList<String>(listSoFar);
 clonedListSoFar.add(v);
 int nextLevel = level + 1;
 walker(finalResult, input, clonedListSoFar, nextLevel);
      }
    }