public class LivePropertySource {


 private final List<LiveProperty> properties;
 private final String sourceName;




 public LivePropertySource(String sourceName, List<LiveProperty> properties) {
 this.sourceName = sourceName;
 this.properties = properties != null ? ImmutableList.copyOf(properties) : ImmutableList.of();
	}


 public String getSourceName() {
 return this.sourceName;
	}


 public LiveProperty getProperty(String propertyName) {
 for (LiveProperty liveProperty : properties) {
 if (liveProperty.getProperty().equals(propertyName)) {
 return liveProperty;
			}
		}
 return null;
	}


}