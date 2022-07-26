public class OptionalManyTransitionImpl extends MinimalEObjectImpl.Container implements OptionalManyTransition
{
 /**
   * The default value of the '{@link #getVal() <em>Val</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVal()
   * @generated
   * @ordered
   */
 protected static final String VAL_EDEFAULT = null;


 /**
   * The cached value of the '{@link #getVal() <em>Val</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVal()
   * @generated
   * @ordered
   */
 protected String val = VAL_EDEFAULT;


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 protected OptionalManyTransitionImpl()
  {
 super();
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 @Override
 protected EClass eStaticClass()
  {
 return SyntacticsequencertestPackage.Literals.OPTIONAL_MANY_TRANSITION;
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 public String getVal()
  {
 return val;
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 public void setVal(String newVal)
  {
 String oldVal = val;
 val = newVal;
 if (eNotificationRequired())
 eNotify(new ENotificationImpl(this, Notification.SET, SyntacticsequencertestPackage.OPTIONAL_MANY_TRANSITION__VAL, oldVal, val));
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 @Override
 public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
 switch (featureID)
    {
 case SyntacticsequencertestPackage.OPTIONAL_MANY_TRANSITION__VAL:
 return getVal();
    }
 return super.eGet(featureID, resolve, coreType);
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 @Override
 public void eSet(int featureID, Object newValue)
  {
 switch (featureID)
    {
 case SyntacticsequencertestPackage.OPTIONAL_MANY_TRANSITION__VAL:
 setVal((String)newValue);
 return;
    }
 super.eSet(featureID, newValue);
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 @Override
 public void eUnset(int featureID)
  {
 switch (featureID)
    {
 case SyntacticsequencertestPackage.OPTIONAL_MANY_TRANSITION__VAL:
 setVal(VAL_EDEFAULT);
 return;
    }
 super.eUnset(featureID);
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 @Override
 public boolean eIsSet(int featureID)
  {
 switch (featureID)
    {
 case SyntacticsequencertestPackage.OPTIONAL_MANY_TRANSITION__VAL:
 return VAL_EDEFAULT == null ? val != null : !VAL_EDEFAULT.equals(val);
    }
 return super.eIsSet(featureID);
  }


 /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 @Override
 public String toString()
  {
 if (eIsProxy()) return super.toString();


 StringBuffer result = new StringBuffer(super.toString());
 result.append(" (val: ");
 result.append(val);
 result.append(')');
 return result.toString();
  }


} //OptionalManyTransitionImpl