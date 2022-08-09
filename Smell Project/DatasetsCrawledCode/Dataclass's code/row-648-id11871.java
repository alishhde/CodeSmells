 @PipeBitInfo(
 name = "Closed TLink Counter",
 description = "Counts the number of TLinks that have shares Events or Times in the Gold view.",
 role = PipeBitInfo.Role.SPECIAL,
 dependencies = { PipeBitInfo.TypeProduct.TEMPORAL_RELATION }
	)
 public static class CountCloseRelation extends JCasAnnotator_ImplBase {


 private String systemViewName = CAS.NAME_DEFAULT_SOFA;


 @Override
 public void process(JCas jCas) throws AnalysisEngineProcessException {
 JCas systemView, goldView;
 int sizeLimit = 6;
 try {
 systemView = jCas.getView(this.systemViewName);
 goldView = jCas.getView(GOLD_VIEW_NAME);
			} catch (CASException e) {
 throw new AnalysisEngineProcessException(e);
			}


 //count how many sentences have timex, and how many sentences have only one timex
 for (TemporalTextRelation relation : JCasUtil.select(systemView, TemporalTextRelation.class)) {
 sysRelationCount ++;
 Annotation arg1 = relation.getArg1().getArgument();
 Annotation arg2 = relation.getArg2().getArgument();
 if( arg1.getBegin()> arg2.getBegin()){
 Annotation temp = arg1;
 arg1 = arg2;
 arg2 = temp;
				}
 List<WordToken> words = JCasUtil.selectBetween(systemView, WordToken.class, arg1, arg2);
 if(words.size()<sizeLimit){
 closeRelationCount++;
				}
			}


 Map<List<Annotation>, TemporalTextRelation> relationLookup = new HashMap<>();
 for (TemporalTextRelation relation : Lists.newArrayList(JCasUtil.select(goldView, TemporalTextRelation.class))) {
 Annotation arg1 = relation.getArg1().getArgument();
 Annotation arg2 = relation.getArg2().getArgument();
 // The key is a list of args so we can do bi-directional lookup
 List<Annotation> key = Arrays.asList(arg1, arg2);
 if(!relationLookup.containsKey(key)){
 relationLookup.put(key, relation);
				}
			}


 //count how many sentences have timex, and how many sentences have only one timex
 for (TemporalTextRelation relation : relationLookup.values()) {
 goldRelationCount ++;
 Annotation arg1 = relation.getArg1().getArgument();
 Annotation arg2 = relation.getArg2().getArgument();
 if( arg1.getBegin()> arg2.getBegin()){
 Annotation temp = arg1;
 arg1 = arg2;
 arg2 = temp;
				}
 List<WordToken> words = JCasUtil.selectBetween(systemView, WordToken.class, arg1, arg2);
 if(words.size()<sizeLimit){
 closeGoldRelationCount++;
				}
			}
		}
	}