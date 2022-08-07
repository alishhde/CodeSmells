 public class TestContent {
 public final boolean isError;


 public final String data;


 public TestContent(boolean isError, String data) {
 this.isError = isError;
 this.data = data;
		}
 
 @Override
 public String toString() {
 if (isError) {
 return "Error("+data+")";
			} else {
 return data;
			}
		}
	}