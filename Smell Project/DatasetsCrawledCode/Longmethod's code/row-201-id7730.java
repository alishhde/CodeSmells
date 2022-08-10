 public <SEARCH_T extends Annotation> List<Feature> extract(JCas jCas,
 Annotation focusAnnotation, Bounds bounds,
 Class<SEARCH_T> annotationClass, FeatureExtractor1<SEARCH_T> extractor)
 throws CleartkExtractorException {
 LinkedHashMap<String,Double> runningTotals = new LinkedHashMap<>();


 for (Context context : this.contexts) {
 for (Feature feature : context.extract(
 jCas,
 focusAnnotation,
 bounds,
 annotationClass,
 extractor)) {
 try{
 double val = Double.parseDouble(feature.getValue().toString());
 if(!runningTotals.containsKey(feature.getName())){
 runningTotals.put(feature.getName(), 0.0);
          }
 runningTotals.put(feature.getName(), runningTotals.get(feature.getName()) + val);
        }catch(Exception e){
 // just ignore this feature?
        }
      }
    }
 List<Feature> features = new ArrayList<>();
 for(String key : runningTotals.keySet()){
 features.add(new Feature(this.name + "_" + key, runningTotals.get(key)));
    }
 return features;
  }