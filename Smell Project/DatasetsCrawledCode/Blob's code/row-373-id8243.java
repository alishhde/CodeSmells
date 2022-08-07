public class Bug288734TestLanguageSwitch<T> extends Switch<T>
{
 /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 protected static Bug288734TestLanguagePackage modelPackage;


 /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
 public Bug288734TestLanguageSwitch()
  {
 if (modelPackage == null)
    {
 modelPackage = Bug288734TestLanguagePackage.eINSTANCE;
    }
  }


 /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
 @Override
 protected boolean isSwitchFor(EPackage ePackage)
  {
 return ePackage == modelPackage;
  }


 /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
 @Override
 protected T doSwitch(int classifierID, EObject theEObject)
  {
 switch (classifierID)
    {
 case Bug288734TestLanguagePackage.MODEL:
      {
 Model model = (Model)theEObject;
 T result = caseModel(model);
 if (result == null) result = defaultCase(theEObject);
 return result;
      }
 case Bug288734TestLanguagePackage.TCONSTANT:
      {
 TConstant tConstant = (TConstant)theEObject;
 T result = caseTConstant(tConstant);
 if (result == null) result = defaultCase(theEObject);
 return result;
      }
 case Bug288734TestLanguagePackage.TSTRING_CONSTANT:
      {
 TStringConstant tStringConstant = (TStringConstant)theEObject;
 T result = caseTStringConstant(tStringConstant);
 if (result == null) result = caseTConstant(tStringConstant);
 if (result == null) result = defaultCase(theEObject);
 return result;
      }
 case Bug288734TestLanguagePackage.TINTEGER_CONSTANT:
      {
 TIntegerConstant tIntegerConstant = (TIntegerConstant)theEObject;
 T result = caseTIntegerConstant(tIntegerConstant);
 if (result == null) result = caseTConstant(tIntegerConstant);
 if (result == null) result = defaultCase(theEObject);
 return result;
      }
 case Bug288734TestLanguagePackage.TBOOLEAN_CONSTANT:
      {
 TBooleanConstant tBooleanConstant = (TBooleanConstant)theEObject;
 T result = caseTBooleanConstant(tBooleanConstant);
 if (result == null) result = caseTConstant(tBooleanConstant);
 if (result == null) result = defaultCase(theEObject);
 return result;
      }
 case Bug288734TestLanguagePackage.TANNOTATION:
      {
 TAnnotation tAnnotation = (TAnnotation)theEObject;
 T result = caseTAnnotation(tAnnotation);
 if (result == null) result = defaultCase(theEObject);
 return result;
      }
 default: return defaultCase(theEObject);
    }
  }


 /**
   * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Model</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
 public T caseModel(Model object)
  {
 return null;
  }


 /**
   * Returns the result of interpreting the object as an instance of '<em>TConstant</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>TConstant</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
 public T caseTConstant(TConstant object)
  {
 return null;
  }


 /**
   * Returns the result of interpreting the object as an instance of '<em>TString Constant</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>TString Constant</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
 public T caseTStringConstant(TStringConstant object)
  {
 return null;
  }


 /**
   * Returns the result of interpreting the object as an instance of '<em>TInteger Constant</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>TInteger Constant</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
 public T caseTIntegerConstant(TIntegerConstant object)
  {
 return null;
  }


 /**
   * Returns the result of interpreting the object as an instance of '<em>TBoolean Constant</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>TBoolean Constant</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
 public T caseTBooleanConstant(TBooleanConstant object)
  {
 return null;
  }


 /**
   * Returns the result of interpreting the object as an instance of '<em>TAnnotation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>TAnnotation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
 public T caseTAnnotation(TAnnotation object)
  {
 return null;
  }


 /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
 @Override
 public T defaultCase(EObject object)
  {
 return null;
  }


} //Bug288734TestLanguageSwitch