 public boolean equals(TListSentryPrivilegesRequest that) {
 if (that == null)
 return false;


 boolean this_present_protocol_version = true;
 boolean that_present_protocol_version = true;
 if (this_present_protocol_version || that_present_protocol_version) {
 if (!(this_present_protocol_version && that_present_protocol_version))
 return false;
 if (this.protocol_version != that.protocol_version)
 return false;
    }


 boolean this_present_requestorUserName = true && this.isSetRequestorUserName();
 boolean that_present_requestorUserName = true && that.isSetRequestorUserName();
 if (this_present_requestorUserName || that_present_requestorUserName) {
 if (!(this_present_requestorUserName && that_present_requestorUserName))
 return false;
 if (!this.requestorUserName.equals(that.requestorUserName))
 return false;
    }


 boolean this_present_roleName = true && this.isSetRoleName();
 boolean that_present_roleName = true && that.isSetRoleName();
 if (this_present_roleName || that_present_roleName) {
 if (!(this_present_roleName && that_present_roleName))
 return false;
 if (!this.roleName.equals(that.roleName))
 return false;
    }


 boolean this_present_authorizableHierarchy = true && this.isSetAuthorizableHierarchy();
 boolean that_present_authorizableHierarchy = true && that.isSetAuthorizableHierarchy();
 if (this_present_authorizableHierarchy || that_present_authorizableHierarchy) {
 if (!(this_present_authorizableHierarchy && that_present_authorizableHierarchy))
 return false;
 if (!this.authorizableHierarchy.equals(that.authorizableHierarchy))
 return false;
    }


 return true;
  }