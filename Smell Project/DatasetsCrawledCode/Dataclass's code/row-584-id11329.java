@Entity
@Table(name = JPADynRealmMembership.TABLE)
public class JPADynRealmMembership extends AbstractGeneratedKeyEntity implements DynRealmMembership {


 private static final long serialVersionUID = 8157856850557493134L;


 public static final String TABLE = "DynRealmMembership";


 @OneToOne
 private JPADynRealm dynRealm;


 @ManyToOne
 private JPAAnyType anyType;


 @NotNull
 private String fiql;


 @Override
 public DynRealm getDynRealm() {
 return dynRealm;
    }


 @Override
 public void setDynRealm(final DynRealm dynRealm) {
 checkType(dynRealm, JPADynRealm.class);
 this.dynRealm = (JPADynRealm) dynRealm;
    }


 @Override
 public AnyType getAnyType() {
 return anyType;
    }


 @Override
 public void setAnyType(final AnyType anyType) {
 checkType(anyType, JPAAnyType.class);
 this.anyType = (JPAAnyType) anyType;
    }


 @Override
 public String getFIQLCond() {
 return fiql;
    }


 @Override
 public void setFIQLCond(final String fiql) {
 this.fiql = fiql;
    }


}