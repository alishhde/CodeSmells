 static public Object postUpdate(String itemName, String stateString) {
 ItemRegistry registry = ScriptServiceUtil.getItemRegistry();
 EventPublisher publisher = ScriptServiceUtil.getEventPublisher();
 if (publisher != null && registry != null) {
 try {
 Item item = registry.getItem(itemName);
 State state = TypeParser.parseState(item.getAcceptedDataTypes(), stateString);
 if (state != null) {
 publisher.post(ItemEventFactory.createStateEvent(itemName, state));
                } else {
 LoggerFactory.getLogger(BusEvent.class).warn(
 "Cannot convert '{}' to a state type which item '{}' accepts: {}.", stateString, itemName,
 getAcceptedDataTypeNames(item));
                }
            } catch (ItemNotFoundException e) {
 LoggerFactory.getLogger(BusEvent.class).warn("Item '{}' does not exist.", itemName);
            }
        }
 return null;
    }