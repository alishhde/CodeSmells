 public static class PartnerLinkRef extends OBase implements RValue, LValue, Serializable {
 public static final long serialVersionUID = -1L;
 private static final String PARTNERLINK = "partnerLink";
 private static final String ISMYENDPOINTREFERENCE = "isMyEndpointReference";


 @JsonCreator
 public PartnerLinkRef() {
 setIsMyEndpointReference(false);
        }


 public PartnerLinkRef(OProcess owner) {
 super(owner);
 setIsMyEndpointReference(false);
        }


 @JsonIgnore
 public boolean isIsMyEndpointReference() {
 Object o = fieldContainer.get(ISMYENDPOINTREFERENCE);
 return o == null ? false : (Boolean) o;
        }


 @JsonIgnore
 public OPartnerLink getPartnerLink() {
 Object o = fieldContainer.get(PARTNERLINK);
 return o == null ? null : (OPartnerLink) o;
        }


 // Must fit in a LValue even if it's not variable based
 @JsonIgnore
 public Variable getVariable() {
 return null;
        }


 public void setIsMyEndpointReference(boolean isMyEndpointReference) {
 fieldContainer.put(ISMYENDPOINTREFERENCE, isMyEndpointReference);
        }


 public void setPartnerLink(OPartnerLink partnerLink) {
 fieldContainer.put(PARTNERLINK, partnerLink);
        }


 public String toString() {
 return "{PLinkRef " + getPartnerLink() + "!" + isIsMyEndpointReference() + "}";
        }
    }