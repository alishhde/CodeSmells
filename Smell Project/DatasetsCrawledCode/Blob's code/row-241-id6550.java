public abstract class AbstractSimpleExtractor<T> implements Extractor<T> {


 private static final Logger LOG = LoggerFactory.getLogger(AbstractSimpleExtractor.class);
 private static final int LOG_ERROR_LIMIT = 100;
 
 private int errors;
 private boolean errorOnLast;
 private final T defaultValue;
 private final TokenizerFactory scannerFactory;
 
 protected AbstractSimpleExtractor(T defaultValue) {
 this(defaultValue, TokenizerFactory.getDefaultInstance());
  }
 
 protected AbstractSimpleExtractor(T defaultValue, TokenizerFactory scannerFactory) {
 this.defaultValue = defaultValue;
 this.scannerFactory = scannerFactory;
  }


 @Override
 public void initialize() {
 this.errors = 0;
 this.errorOnLast = false;
  }
 
 @Override
 public T extract(String input) {
 errorOnLast = false;
 T res = defaultValue;
 try {
 res = doExtract(scannerFactory.create(input));
    } catch (Exception e) {
 errorOnLast = true;
 errors++;
 if (errors < LOG_ERROR_LIMIT) {
 LOG.error("Error occurred parsing input '{}' using extractor {}", input, this);
      }
    }
 return res;
  }


 @Override
 public boolean errorOnLastRecord() {
 return errorOnLast;
  }
 
 @Override
 public T getDefaultValue() {
 return defaultValue;
  }
 
 @Override
 public ExtractorStats getStats() {
 return new ExtractorStats(errors);
  }
 
 /**
   * Subclasses must override this method to return a new instance of the
   * class that this {@code Extractor} instance is designed to parse.
   * <p>Any runtime parsing exceptions from the given {@code Tokenizer} instance
   * should be thrown so that they may be caught by the error handling logic
   * inside of this class.
   * 
   * @param tokenizer The {@code Tokenizer} instance for the current record
   * @return A new instance of the type defined for this class
   */
 protected abstract T doExtract(Tokenizer tokenizer);
}