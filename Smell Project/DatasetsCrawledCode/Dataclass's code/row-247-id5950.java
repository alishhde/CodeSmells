@SpringComponent
@VaadinSessionScope
public class ManageSoftwareModuleFilters implements Serializable {


 private static final long serialVersionUID = -1631725636290496525L;


 private SoftwareModuleType softwareModuleType;


 private String searchText;


 /**
     * @return the softwareModuleType
     */
 public Optional<SoftwareModuleType> getSoftwareModuleType() {
 return Optional.ofNullable(softwareModuleType);
    }


 /**
     * @param softwareModuleType
     *            the softwareModuleType to set
     */
 public void setSoftwareModuleType(final SoftwareModuleType softwareModuleType) {
 this.softwareModuleType = softwareModuleType;
    }


 /**
     * @return the searchText
     */
 public Optional<String> getSearchText() {
 return Optional.ofNullable(searchText);
    }


 /**
     * @param searchText
     *            the searchText to set
     */
 public void setSearchText(final String searchText) {
 this.searchText = searchText;
    }
}