@UnknownActivationContextCheck(false)
@WhitelistAccessOnly
@ContentType("text/html")
@Import(stylesheet = "dashboard.css")
public class T5Dashboard extends AbstractInternalPage
{
 @Inject
 @Symbol(SymbolConstants.TAPESTRY_VERSION)
 @Property
 private String frameworkVersion;


 @Property
 @Inject
 @Symbol(SymbolConstants.PRODUCTION_MODE)
 private boolean productionMode;


 @Inject
 @Property
 private DashboardManager dashboardManager;


 @Property
 private String tabName;


 private String activeTab;


 public String getTabClass()
    {
 return tabName.equalsIgnoreCase(activeTab) ? "active" : null;
    }


 public Block getContent()
    {
 return dashboardManager.getTabContent(activeTab);
    }


 void onActivate()
    {
 activeTab = dashboardManager.getTabNames().get(0);
    }


 boolean onActivate(String tabName)
    {
 activeTab = tabName;


 return true;
    }


 String onPassivate()
    {
 return activeTab;
    }
}