public class ScriptValueImpl extends ActionValueImpl implements ScriptValue
{


 /**
	 * The default value of the '{@link #getScript() <em>Script</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getScript()
	 * @generated
	 * @ordered
	 */
 protected static final String SCRIPT_EDEFAULT = null;


 /**
	 * The cached value of the '{@link #getScript() <em>Script</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getScript()
	 * @generated
	 * @ordered
	 */
 protected String script = SCRIPT_EDEFAULT;


 /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
 protected ScriptValueImpl( )
	{
 super( );
	}


 /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
 @Override
 protected EClass eStaticClass( )
	{
 return AttributePackage.Literals.SCRIPT_VALUE;
	}


 /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
 public String getScript( )
	{
 return script;
	}


 /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
 public void setScript( String newScript )
	{
 String oldScript = script;
 script = newScript;
 if ( eNotificationRequired( ) )
 eNotify( new ENotificationImpl( this,
 Notification.SET,
 AttributePackage.SCRIPT_VALUE__SCRIPT,
 oldScript,
 script ) );
	}


 /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
 @Override
 public Object eGet( int featureID, boolean resolve, boolean coreType )
	{
 switch ( featureID )
		{
 case AttributePackage.SCRIPT_VALUE__SCRIPT :
 return getScript( );
		}
 return super.eGet( featureID, resolve, coreType );
	}


 /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
 @Override
 public void eSet( int featureID, Object newValue )
	{
 switch ( featureID )
		{
 case AttributePackage.SCRIPT_VALUE__SCRIPT :
 setScript( (String) newValue );
 return;
		}
 super.eSet( featureID, newValue );
	}


 /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
 @Override
 public void eUnset( int featureID )
	{
 switch ( featureID )
		{
 case AttributePackage.SCRIPT_VALUE__SCRIPT :
 setScript( SCRIPT_EDEFAULT );
 return;
		}
 super.eUnset( featureID );
	}


 /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
 @Override
 public boolean eIsSet( int featureID )
	{
 switch ( featureID )
		{
 case AttributePackage.SCRIPT_VALUE__SCRIPT :
 return SCRIPT_EDEFAULT == null ? script != null
						: !SCRIPT_EDEFAULT.equals( script );
		}
 return super.eIsSet( featureID );
	}


 /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
 @Override
 public String toString( )
	{
 if ( eIsProxy( ) )
 return super.toString( );


 StringBuffer result = new StringBuffer( super.toString( ) );
 result.append( " (script: " ); //$NON-NLS-1$
 result.append( script );
 result.append( ')' );
 return result.toString( );
	}


 /**
	 * A convenience method provided to build a script action value when needed
	 * 
	 * @param script
	 * @return
	 */
 public static final ScriptValue create( String script )
	{
 ScriptValue sv = AttributeFactory.eINSTANCE.createScriptValue( );
 sv.setScript( script );
 return sv;
	}


 /**
	 * A convenient method to get an instance copy. This is much faster than the
	 * ECoreUtil.copy().
	 */
 public ScriptValue copyInstance( )
	{
 ScriptValueImpl dest = new ScriptValueImpl( );
 dest.set( this );
 return dest;
	}


 protected void set( ScriptValue src )
	{
 super.set( src );
 script = src.getScript( );
	}


 /*
	 * Get script expression.
	 * 
	 * @return expression the script expression.
	 */
 public ScriptExpression getScriptExpression( )
	{
 ScriptExpression expression = new ScriptExpression( );
 expression.setType( ChartUtil.getExpressionType( script ) );
 expression.setValue( ChartUtil.getExpressionText( script ) );
 return expression;
	}


 /*
	 * Set script expression.
	 * 
	 * @param expression the script expression.
	 */
 public void setScriptExpression( ScriptExpression expression )
	{
 setScript( ChartUtil.adaptExpression( expression ) );
	}


} // ScriptValueImpl