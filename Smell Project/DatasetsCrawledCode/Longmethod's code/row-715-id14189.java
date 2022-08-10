 public List<Feature> extract(JCas jCas, CollectionTextRelation cluster,
 IdentifiedAnnotation mention) throws AnalysisEngineProcessException {
 if(cache == null){
 throw new RuntimeException("This extractor requires a cached Markable->ConllDependencyNode map to be set with setCache()");
    }
 List<Feature> feats = new ArrayList<>();
 CounterMap<String> featCounts = new CounterMap<>();
 
 if(StringMatchingFeatureExtractor.isPronoun(mention)) return feats;
 
 String m = mention.getCoveredText();
 Set<String> mentionWords = contentWords(mention);
 Set<String> nonHeadMentionWords = new HashSet<>(mentionWords);
 ConllDependencyNode mentionHead = cache.get(mention);
 
 String mentionHeadString = null;
 if(mentionHead != null){
 mentionHeadString = mentionHead.getCoveredText().toLowerCase();
 nonHeadMentionWords.remove(mentionHeadString);


 int maxNonoverlap = 0;


 for(IdentifiedAnnotation member : new ListIterable<IdentifiedAnnotation>(cluster.getMembers())){
 if(member == null){
 System.err.println("Something that shouldn't happen has happened");
 continue;
        }else if(mention.getBegin() < member.getEnd()){
 // during training this might happen -- see a member of a cluster that
 // is actually subsequent to the candidate mention
 continue;
        }else if(StringMatchingFeatureExtractor.isPronoun(member)){
 continue;
        }


 String s = member.getCoveredText();
 Set<String> memberWords = contentWords(member);
 Set<String> nonHeadMemberWords = new HashSet<>(memberWords);
 ConllDependencyNode memberHead = cache.get(member);
 String memberHeadString = null;
 if(memberHead != null){
 memberHeadString = memberHead.getCoveredText().toLowerCase();
 nonHeadMemberWords.remove(memberHeadString);


 if(mentionHeadString.equals(memberHeadString)){


 if(m.equalsIgnoreCase(s)) featCounts.add("MC_STRING_EXACT");
 if(startMatch(m,s)) featCounts.add("MC_STRING_START");
 if(endMatch(m,s)) featCounts.add("MC_STRING_END");
 if(soonMatch(m,s)) featCounts.add("MC_STRING_SOON");
 if(wordOverlap(mentionWords, memberWords)) featCounts.add("MC_OVERLAP");
 if(wordSubstring(mentionWords, memberWords)) featCounts.add("MC_SUB");


 int nonHeadOverlap = wordNonOverlapCount(nonHeadMemberWords, nonHeadMentionWords);
 if(nonHeadOverlap > maxNonoverlap){
 maxNonoverlap = nonHeadOverlap;
            }
          }
        }
      }
 feats.add(new Feature("MC_MAX_NONOVERLAP", maxNonoverlap));
    }
 
 
 for(String featKey : featCounts.keySet()){
 // normalized
//      feats.add(new Feature(featKey, (double) featCounts.get(featKey) / clusterSize));
 // boolean
 feats.add(new Feature(featKey, true));
    }
 return feats;
  }