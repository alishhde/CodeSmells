public class AccessRoleCreatorImpl extends AbstractKapuaEntityCreator<AccessRole> implements AccessRoleCreator {


 private static final long serialVersionUID = 972154225756734130L;


 private KapuaId accessInfo;
 private KapuaId roleId;


 /**
     * Constructor
     * 
     * @param scopeId
     */
 public AccessRoleCreatorImpl(KapuaId scopeId) {
 super(scopeId);
    }


 @Override
 public KapuaId getAccessInfoId() {
 return accessInfo;
    }


 @Override
 public void setAccessInfoId(KapuaId accessInfo) {
 this.accessInfo = accessInfo;
    }


 @Override
 public KapuaId getRoleId() {
 return roleId;
    }


 @Override
 public void setRoleId(KapuaId roleId) {
 this.roleId = roleId;
    }


}