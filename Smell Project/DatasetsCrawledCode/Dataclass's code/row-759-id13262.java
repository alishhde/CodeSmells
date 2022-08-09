public class ActionNamesAction extends ActionSupport {


 private static final long serialVersionUID = -5389385242431387840L;


 private Set<String> actionNames;
 private String namespace = "";
 private Set<String> namespaces;
 private String extension;


 protected ConfigurationHelper configHelper;


 @Inject
 public void setConfigurationHelper(ConfigurationHelper cfg) {
 this.configHelper = cfg;
    }


 public Set<String> getActionNames() {
 return actionNames;
    }


 public String getNamespace() {
 return StringEscapeUtils.escapeHtml4(namespace);
    }


 public void setNamespace(String namespace) {
 this.namespace = namespace;
    }


 @Inject(StrutsConstants.STRUTS_ACTION_EXTENSION)
 public void setExtension(String ext) {
 this.extension = ext;
    }


 public ActionConfig getConfig(String actionName) {
 return configHelper.getActionConfig(namespace, actionName);
    }


 public Set<String> getNamespaces() {
 return namespaces;
    }


 public String getExtension() {
 if (extension == null) {
 return "action";
        }
 if (extension.contains(",")) {
 return extension.substring(0, extension.indexOf(","));
        }
 return extension;
    }


 public String execute() throws Exception {
 namespaces = configHelper.getNamespaces();
 if (namespaces.size() == 0) {
 addActionError("There are no namespaces in this configuration");
 return ERROR;
        }
 if (namespace == null) {
 namespace = "";
        }
 actionNames = new TreeSet<String>(configHelper.getActionNames(namespace));
 return SUCCESS;
    }


 /**
     * Index action to support cooperation with REST plugin
     *
     * @return action result
     * @throws Exception
     */
 public String index() throws Exception {
 return execute();
    }


 public String redirect() {
 return SUCCESS;
    }


}