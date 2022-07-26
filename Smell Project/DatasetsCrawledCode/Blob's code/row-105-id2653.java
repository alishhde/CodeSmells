public class DefaultTypeDeclaration extends AbstractDeclaration implements TypeDeclaration {


 private final Element m_componentMetadata;
 private final String m_componentName;
 private final String m_componentVersion;
 private final String m_extension;
 private boolean visible = true;


 public DefaultTypeDeclaration(BundleContext bundleContext, Element componentMetadata) {
 super(bundleContext, TypeDeclaration.class);
 m_componentMetadata = componentMetadata;
 visible = initVisible();
 m_componentName = initComponentName();
 m_componentVersion = initComponentVersion(bundleContext);
 m_extension = initExtension();
    }


 private String initExtension() {
 if (m_componentMetadata.getNameSpace() == null) {
 return m_componentMetadata.getName();
        }
 return m_componentMetadata.getNameSpace() + ":" + m_componentMetadata.getName();
    }


 private String initComponentVersion(BundleContext bundleContext) {
 String version = m_componentMetadata.getAttribute("version");
 if (version != null) {
 if ("bundle".equalsIgnoreCase(version)) {
 return bundleContext.getBundle().getHeaders().get(Constants.BUNDLE_VERSION);
            }
        }
 return version;
    }


 private String initComponentName() {
 String name = m_componentMetadata.getAttribute("name");
 if (name == null) {
 name = m_componentMetadata.getAttribute("classname");
        }
 return name;
    }


 private boolean initVisible() {
 String publicAttribute = m_componentMetadata.getAttribute("public");
 return (publicAttribute == null) || !publicAttribute.equalsIgnoreCase("false");
    }


 public String getComponentName() {
 return m_componentName;
    }


 public String getComponentVersion() {
 return m_componentVersion;
    }


 public String getExtension() {
 return m_extension;
    }


 public Element getComponentMetadata() {
 return m_componentMetadata;
    }


 public boolean isPublic() {
 return visible;
    }
}