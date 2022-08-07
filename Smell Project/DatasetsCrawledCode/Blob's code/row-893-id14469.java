final class SearchFirstStringNode extends Node {


 private static final int[] UNINTIALIZED_CACHED_INDICES = new int[0];


 private final VectorLengthProfile targetLengthProfile = VectorLengthProfile.create();
 private final VectorLengthProfile elementsLengthProfile = VectorLengthProfile.create();
 private final ValueProfile targetClassProfile = ValueProfile.createClassProfile();
 private final ValueProfile elementsClassProfile = ValueProfile.createClassProfile();


 @Child private StringEqualsNode stringEquals = CompareStringNode.createEquals();
 @Child private CompareStringNode stringStartsWith;
 @Child private StringEqualsNode equalsDuplicate;


 private final NACheck elementsNACheck = NACheck.create();
 private final NACheck targetNACheck = NACheck.create();
 private final BranchProfile everFoundDuplicate = BranchProfile.create();
 private final BranchProfile seenInvalid = BranchProfile.create();


 /** Instead of using the notFoundStartIndex we use NA. */
 private final boolean useNAForNotFound;
 private final boolean exactMatch;


 @CompilationFinal(dimensions = 1) private int[] cachedIndices;


 private SearchFirstStringNode(boolean exactMatch, boolean useNAForNotFound) {
 this.exactMatch = exactMatch;
 this.useNAForNotFound = useNAForNotFound;
 if (!exactMatch) {
 stringStartsWith = CompareStringNode.createStartsWith();
        }
    }


 public RAbstractIntVector apply(RAbstractStringVector target, RAbstractStringVector elements, int notFoundStartIndex, RStringVector names) {
 RAbstractStringVector targetProfiled = targetClassProfile.profile(target);
 RAbstractStringVector elementsProfiled = elementsClassProfile.profile(elements);


 int targetLength = targetLengthProfile.profile(targetProfiled.getLength());
 int elementsLength = elementsLengthProfile.profile(elementsProfiled.getLength());


 targetNACheck.enable(target);
 elementsNACheck.enable(elements);


 if (cachedIndices == UNINTIALIZED_CACHED_INDICES) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 cachedIndices = searchCached(targetProfiled, targetLength, elementsProfiled, elementsLength, names);
        }
 if (cachedIndices != null) {
 if (!isCacheValid(targetProfiled, targetLength, elementsProfiled, elementsLength, cachedIndices)) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 cachedIndices = null; // set to generic
 // fallthrough to generic
            } else {
 assert sameVector(searchCached(target, targetLength, elements, elementsLength, names), cachedIndices);
 return RDataFactory.createIntVector(cachedIndices, true, names);
            }
        }


 return searchGeneric(targetProfiled, targetLength, elementsProfiled, elementsLength, notFoundStartIndex, false, names);
    }


 public static SearchFirstStringNode createNode(boolean exactMatch, boolean useNAForNotFound) {
 return new SearchFirstStringNode(exactMatch, useNAForNotFound);
    }


 private int[] searchCached(RAbstractStringVector target, int targetLength, RAbstractStringVector elements, int elementsLength, RStringVector names) {
 if (exactMatch) {
 RAbstractIntVector genericResult = searchGeneric(target, targetLength, elements, elementsLength, -1, true, names);
 if (genericResult != null) {
 return genericResult.materialize().getReadonlyData();
            }
        }
 return null;
    }


 private boolean isCacheValid(RAbstractStringVector target, int targetLength,
 RAbstractStringVector elements, int elementsLength, int[] cached) {
 int cachedLength = cached.length;
 if (elementsLength != cachedLength) {
 seenInvalid.enter();
 return false;
        }


 for (int i = 0; i < cachedLength; i++) {
 int cachedIndex = cached[i];
 String cachedElement = elements.getDataAt(i);
 int cachedElementHash = cachedElement.hashCode();


 assert !elementsNACheck.check(cachedElement) && cachedElement.length() > 0;


 int cachedTranslatedIndex = cachedIndex - 1;
 for (int j = 0; j < cachedTranslatedIndex; j++) {
 String targetString = target.getDataAt(j);
 if (!targetNACheck.check(targetString) && stringEquals.executeCompare(cachedElement, cachedElementHash, targetString)) {
 seenInvalid.enter();
 return false;
                }
            }
 if (cachedTranslatedIndex < targetLength) {
 String targetString = target.getDataAt(cachedTranslatedIndex);
 if (!targetNACheck.check(targetString) && !stringEquals.executeCompare(cachedElement, cachedElementHash, targetString)) {
 seenInvalid.enter();
 return false;
                }
            } else {
 seenInvalid.enter();
 return false;
            }
        }
 return true;


    }


 private static boolean sameVector(int[] a, int[] b) {
 if (a == null) {
 return false;
        }
 if (a.length != b.length) {
 return false;
        }


 for (int i = 0; i < a.length; i++) {
 if (a[i] != b[i]) {
 return false;
            }
        }
 return true;
    }


 private final BranchProfile notFoundProfile = BranchProfile.create();
 private final ConditionProfile hashingProfile = ConditionProfile.createBinaryProfile();


 private RAbstractIntVector searchGeneric(RAbstractStringVector target, int targetLength, RAbstractStringVector elements, int elementsLength, int notFoundStartIndex, boolean nullOnNotFound,
 RStringVector names) {
 int[] indices = new int[elementsLength];
 boolean resultComplete = true;


 long hashingCost = targetLength * 10L + 10 /* constant overhead */;
 long lookupCost = elementsLength * 2L;
 long nestedLoopCost = targetLength * (long) elementsLength;
 NonRecursiveHashMapCharacter map;
 if (hashingProfile.profile(nestedLoopCost > hashingCost + lookupCost)) {
 map = new NonRecursiveHashMapCharacter(targetLength);
 for (int i = 0; i < targetLength; i++) {
 String name = target.getDataAt(i);
 if (!targetNACheck.check(name)) {
 map.put(name, i);
                }
            }
        } else {
 map = null;
        }
 int notFoundIndex = notFoundStartIndex;
 for (int i = 0; i < elementsLength; i++) {
 String element = elements.getDataAt(i);
 boolean isElementNA = elementsNACheck.check(element) || element.length() == 0;
 if (!isElementNA) {
 int index;
 if (map != null) {
 index = map.get(element);
 if (!exactMatch && index < 0) {
 // the map is only good for exact matches
 index = findNonExactIndex(target, targetLength, element);
                    }
                } else {
 index = findIndex(target, targetLength, element);
                }
 if (index >= 0) {
 indices[i] = index + 1;
 continue;
                }
            }
 notFoundProfile.enter();
 if (nullOnNotFound) {
 return null;
            } else {
 int prevDuplicateIndex = -1;
 if (!isElementNA) {
 prevDuplicateIndex = findFirstDuplicate(elements, element, i);
                }
 int nextIndex;
 if (prevDuplicateIndex == -1) {
 if (useNAForNotFound) {
 resultComplete = false;
 nextIndex = RRuntime.INT_NA;
                    } else {
 nextIndex = ++notFoundIndex;
                    }
                } else {
 nextIndex = indices[prevDuplicateIndex];
                }
 indices[i] = nextIndex;
            }
        }
 return RDataFactory.createIntVector(indices, resultComplete && elements.isComplete(), names);
    }


 private int findNonExactIndex(RAbstractStringVector target, int targetLength, String element) {
 assert !exactMatch;
 int nonExactIndex = -1;
 for (int j = 0; j < targetLength; j++) {
 String targetValue = target.getDataAt(j);
 if (!targetNACheck.check(targetValue)) {
 if (stringStartsWith.executeCompare(targetValue, element)) {
 if (nonExactIndex == -1) {
 nonExactIndex = j;
                    } else {
 return -1;
                    }
                }
            }
        }
 return nonExactIndex;
    }


 private int findIndex(RAbstractStringVector target, int targetLength, String element) {
 int nonExactIndex = -1;
 int elementHash = element.hashCode();
 for (int j = 0; j < targetLength; j++) {
 String targetValue = target.getDataAt(j);
 if (!targetNACheck.check(targetValue)) {
 if (stringEquals.executeCompare(element, elementHash, targetValue)) {
 return j;
                }
 if (!exactMatch) {
 if (stringStartsWith.executeCompare(targetValue, element)) {
 if (nonExactIndex == -1) {
 nonExactIndex = j;
                        } else {
 nonExactIndex = -2;
                        }
                    }
                }
            }
        }
 return nonExactIndex;
    }


 private int findFirstDuplicate(RAbstractStringVector elements, String element, int currentIndex) {
 if (equalsDuplicate == null) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 equalsDuplicate = insert(CompareStringNode.createEquals());
        }


 int elementHash = element.hashCode();
 for (int j = 0; j < currentIndex; j++) {
 String otherElement = elements.getDataAt(j);
 if (!targetNACheck.check(otherElement) && equalsDuplicate.executeCompare(element, elementHash, otherElement)) {
 everFoundDuplicate.enter();
 return j;
            }
        }
 return -1;
    }


 abstract static class CompareStringNode extends Node {


 public abstract boolean executeCompare(String a, String b);


 public static StringEqualsNode createEquals() {
 return new StringEqualsNode();
        }


 public static StringStartsWithNode createStartsWith() {
 return new StringStartsWithNode();
        }


 public static class StringEqualsNode extends CompareStringNode {


 private final ConditionProfile identityEquals = ConditionProfile.createBinaryProfile();
 private final ConditionProfile hashEquals = ConditionProfile.createBinaryProfile();


 @Override
 public final boolean executeCompare(String a, String b) {
 assert !RRuntime.isNA(a);
 assert !RRuntime.isNA(b);
 if (identityEquals.profile(Utils.fastPathIdentityEquals(a, b))) {
 return true;
                } else {
 if (hashEquals.profile(a.hashCode() != b.hashCode())) {
 return false;
                    }
 return a.equals(b);
                }
            }


 public final boolean executeCompare(String a, int aHash, String b) {
 assert !RRuntime.isNA(a);
 assert !RRuntime.isNA(b);
 if (identityEquals.profile(Utils.fastPathIdentityEquals(a, b))) {
 return true;
                } else {
 if (hashEquals.profile(aHash != b.hashCode())) {
 return false;
                    }
 return a.equals(b);
                }
            }
        }


 private static class StringStartsWithNode extends CompareStringNode {


 private final ConditionProfile identityEquals = ConditionProfile.createBinaryProfile();


 @Override
 public final boolean executeCompare(String a, String b) {
 assert !RRuntime.isNA(a);
 assert !RRuntime.isNA(b);
 if (identityEquals.profile(Utils.fastPathIdentityEquals(a, b))) {
 return true;
                } else {
 return a.startsWith(b);
                }
            }
        }
    }
}