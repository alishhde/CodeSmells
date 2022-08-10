 public static void main(String[] args) throws ResourceInitializationException, SAXException, IOException {
 AggregateBuilder aggregateBuilder = new AggregateBuilder();
 aggregateBuilder.add(SimpleSegmentAnnotator.createAnnotatorDescription());
 aggregateBuilder.add(SentenceDetectorAnnotatorBIO.getDescription(sentModelPath));
 
 aggregateBuilder.createAggregateDescription().toXML(new FileWriter("desc/analysis_engine/SentenceAnnotatorBIOAggregate.xml"));
 SentenceDetectorAnnotatorBIO.getDescription(sentModelPath).toXML(new FileWriter("desc/analysis_engine/SentenceDetectorAnnotatorBIO.xml"));    
  }