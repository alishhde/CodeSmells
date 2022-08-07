public class XmiWriterCasConsumer extends CasConsumer_ImplBase {
 /**
   * Name of configuration parameter that must be set to the path of a directory into which the
   * output files will be written.
   */
 public static final String PARAM_OUTPUTDIR = "OutputDirectory";


 private File mOutputDir;


 private int mDocNum;


 public void initialize() throws ResourceInitializationException {
 mDocNum = 0;
 mOutputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));
 if (!mOutputDir.exists()) {
 mOutputDir.mkdirs();
    }
  }


 /**
   * Processes the CAS which was populated by the TextAnalysisEngines. <br>
   * In this case, the CAS is converted to XMI and written into the output file .
   * 
   * @param aCAS
   *          a CAS which has been populated by the TAEs
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
   */
 public void processCas(CAS aCAS) throws ResourceProcessException {
 String modelFileName = null;


 JCas jcas;
 try {
 jcas = aCAS.getJCas();
    } catch (CASException e) {
 throw new ResourceProcessException(e);
    }


 // retrieve the filename of the input file from the CAS
 FSIterator it = jcas.getAnnotationIndex(SourceDocumentInformation.type).iterator();
 File outFile = null;
 if (it.hasNext()) {
 SourceDocumentInformation fileLoc = (SourceDocumentInformation) it.next();
 File inFile;
 try {
 inFile = new File(new URL(fileLoc.getUri()).getPath());
 String outFileName = inFile.getName();
 if (fileLoc.getOffsetInSource() > 0) {
 outFileName += ("_" + fileLoc.getOffsetInSource());
        }
 outFileName += ".xmi";
 outFile = new File(mOutputDir, outFileName);
 modelFileName = mOutputDir.getAbsolutePath() + "/" + inFile.getName() + ".ecore";
      } catch (MalformedURLException e1) {
 // invalid URL, use default processing below
      }
    }
 if (outFile == null) {
 outFile = new File(mOutputDir, "doc" + mDocNum++ + ".xmi");     
    }
 // serialize XCAS and write to output file
 try {
 writeXmi(jcas.getCas(), outFile, modelFileName);
    } catch (IOException e) {
 throw new ResourceProcessException(e);
    } catch (SAXException e) {
 throw new ResourceProcessException(e);
    }
  }


 /**
   * Serialize a CAS to a file in XMI format
   * 
   * @param aCas
   *          CAS to serialize
   * @param name
   *          output file
   * @throws SAXException -
   * @throws Exception -
   * 
   * @throws ResourceProcessException -
   */
 private void writeXmi(CAS aCas, File name, String modelFileName) throws IOException, SAXException {
 FileOutputStream out = null;


 try {
 // write XMI
 out = new FileOutputStream(name);
 XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
 XMLSerializer xmlSer = new XMLSerializer(out, false);
 ser.serialize(aCas, xmlSer.getContentHandler());
    } finally {
 if (out != null) {
 out.close();
      }
    }
  }
}