@ManagedAttributeValueType
public interface AclRule extends ManagedAttributeValue
{
 String getIdentity();
 ObjectType getObjectType();
 LegacyOperation getOperation();
 Map<ObjectProperties.Property,String> getAttributes();
 RuleOutcome getOutcome();
}