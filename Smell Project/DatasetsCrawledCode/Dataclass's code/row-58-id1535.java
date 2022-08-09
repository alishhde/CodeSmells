 @Data
 static class VaultInitializationResponseImpl implements VaultInitializationResponse {


 private List<String> keys = new ArrayList<>();


 @JsonProperty("root_token")
 private String rootToken = "";


 public VaultToken getRootToken() {
 return VaultToken.of(rootToken);
		}
	}