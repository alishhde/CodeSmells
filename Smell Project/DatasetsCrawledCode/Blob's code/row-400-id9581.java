@Singleton
public class CheProductInfoDataProvider extends ProductInfoDataProviderImpl {


 private final LocalizationConstant locale;
 private final Resources resources;


 @Inject
 public CheProductInfoDataProvider(LocalizationConstant locale, Resources resources) {
 this.locale = locale;
 this.resources = resources;
  }


 @Override
 public String getName() {
 return locale.getProductName();
  }


 @Override
 public String getSupportLink() {
 return locale.getSupportLink();
  }


 @Override
 public String getDocumentTitle() {
 return locale.cheTabTitle();
  }


 @Override
 public String getDocumentTitle(String workspaceName) {
 return locale.cheTabTitle(workspaceName);
  }


 @Override
 public SVGResource getLogo() {
 return resources.logo();
  }


 @Override
 public SVGResource getWaterMarkLogo() {
 return resources.waterMarkLogo();
  }


 @Override
 public String getSupportTitle() {
 return locale.supportTitle();
  }
}