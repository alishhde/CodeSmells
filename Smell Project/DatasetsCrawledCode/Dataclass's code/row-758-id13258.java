public class CreditBureauData {


 private final long creditBureauId;


 private final String creditBureauName;


 private final String country;


 private final String productName;


 private final String creditBureauSummary;


 private final long implementationKey;


 private CreditBureauData(final long creditBureauId, final String creditBureauName, final String country,
 final String productName, final String creditBureauSummary, final long implementationKey) {
 this.creditBureauId = creditBureauId;
 this.creditBureauName = creditBureauName;
 this.country = country;
 this.productName = productName;
 this.creditBureauSummary = creditBureauSummary;
 this.implementationKey = implementationKey;


	}


 public static CreditBureauData instance(final long creditBureauId, final String creditBureauName,
 final String country, final String productName, final String creditBureauSummary, final long implementationKey) {


 return new CreditBureauData(creditBureauId, creditBureauName, country, productName, creditBureauSummary,
 implementationKey);
	}


 public String getCreditBureauSummary() {
 return this.creditBureauSummary;
	}


 public long getCreditBureauId() {
 return this.creditBureauId;
	}


 public String getCreditBureauName() {
 return this.creditBureauName;
	}


 public String getCountry() {
 return this.country;
	}


 public String getProductName() {
 return this.productName;
	}


 public long getImplementationKey() {
 return this.implementationKey;
	}


}