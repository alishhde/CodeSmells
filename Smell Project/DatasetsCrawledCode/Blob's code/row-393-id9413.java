public class TriplesOrQuadsReader extends AbstractRdfReader<Quad, QuadWritable> {


 @Override
 protected RecordReader<LongWritable, QuadWritable> selectRecordReader(Lang lang) throws IOException {
 if (!RDFLanguages.isQuads(lang) && !RDFLanguages.isTriples(lang))
 throw new IOException(lang.getLabel() + " is not a RDF triples/quads format");


 if (HadoopRdfIORegistry.hasQuadReader(lang)) {
 // Supports quads directly
 return HadoopRdfIORegistry.createQuadReader(lang);
        } else {
 // Try to create a triples reader and wrap upwards into quads
 // This will throw an error if a triple reader is not available
 return new TriplesToQuadsReader(HadoopRdfIORegistry.createTripleReader(lang));
        }
    }


 /**
     * Gets the graph node which represents the graph into which triples will be
     * indicated to belong to when they are converting into quads.
     * <p>
     * Defaults to {@link Quad#defaultGraphNodeGenerated} which represents the
     * default graph
     * </p>
     * 
     * @return Graph node
     */
 protected Node getGraphNode() {
 return Quad.defaultGraphNodeGenerated;
    }
}