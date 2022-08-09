public class WizardUIInfoPage {
 
 private int order;
 
 private String description;
 
 public int getOrder() {
 return order;
	}
 
 public String getDescription() {
 return description;
	}
 
 public static WizardUIInfoPage getDefaultPage(int order) {
 WizardUIInfoPage page = new WizardUIInfoPage();
 page.order = order;
 page.description = "";
 return page;
	}
 
}