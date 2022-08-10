 private static Function<EventMention, String> getPropertyGetter(final String propertyName) {
 return new Function<EventMention, String>() {
 @Override
 public String apply(EventMention eventMention) {
 EventProperties eventProperties = eventMention.getEvent().getProperties();
 Feature feature = eventProperties.getType().getFeatureByBaseName(propertyName);
 return eventProperties.getFeatureValueAsString(feature);
			}
		};
	}