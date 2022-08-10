 @Override
 public Collection<ValidationResult> validate(final ValidationContext validationContext,
 final CredentialsStrategy primaryStrategy) {
 boolean thisIsSelectedStrategy = this == primaryStrategy;
 Boolean useStrategy = validationContext.getProperty(strategyProperty).asBoolean();
 if (!thisIsSelectedStrategy && useStrategy) {
 String failureFormat = "property %1$s cannot be used with %2$s";
 Collection<ValidationResult> validationFailureResults = new ArrayList<ValidationResult>();
 String message = String.format(failureFormat, strategyProperty.getDisplayName(),
 primaryStrategy.getName());
 validationFailureResults.add(new ValidationResult.Builder()
                    .subject(strategyProperty.getDisplayName())
                    .valid(false)
                    .explanation(message).build());
 return validationFailureResults;
        }
 return null;
    }