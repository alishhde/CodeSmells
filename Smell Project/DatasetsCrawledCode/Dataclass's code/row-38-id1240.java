public abstract class TestConfiguration {


 private String downloadUrl;


 private final String description;


 private TestSuite suite;


 public TestConfiguration(String description) {
 this.description = description;
	}


 public String getDownloadUrl() {
 return downloadUrl;
	}


 public void setDownloadUrl(String downloadUrl) {
 this.downloadUrl = downloadUrl;
	}


 public String getDescription() {
 return description;
	}


 @Override
 public String toString() {
 return getClass().getSimpleName() + " [" + description + "]";
	}


 public TestSuite createSuite(TestSuite parentSuite) {
 suite = new TestSuite("Testing on " + getDescription());
 parentSuite.addTest(suite);
 suite.addTest(new Activation("TestSuite: " + getDescription(), true));
 return suite;
	}


 public void add(Class<? extends TestCase> clazz) {
 Assert.isNotNull(suite, "Invoke createSuite() first");
 suite.addTestSuite(clazz);
	}


 public void done() {
 Assert.isNotNull(suite, "Invoke createSuite() first");
 suite.addTest(new Activation("done", false));
 suite = null;
	}


 private final class Activation extends TestCase {


 private final boolean activate;


 private Activation(String name, boolean activate) {
 super(name);
 this.activate = activate;
		}


 @Override
 protected void runTest() throws Throwable {
 if (activate) {
 activate();
			}
 else {
 getDefault().activate();
			}
		}


	}


 protected abstract TestConfiguration getDefault();


 public abstract void activate();


 public abstract TestHarness createHarness();


}