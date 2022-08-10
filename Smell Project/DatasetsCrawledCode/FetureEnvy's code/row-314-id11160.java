 public static List<Map<String, Object>> getFacilityContactMechValueMaps(Delegator delegator, String facilityId, boolean showOld, String contactMechTypeId) {
 List<Map<String, Object>> facilityContactMechValueMaps = new LinkedList<Map<String,Object>>();


 List<GenericValue> allFacilityContactMechs = null;


 try {
 List<GenericValue> tempCol = EntityQuery.use(delegator).from("FacilityContactMech").where("facilityId", facilityId).queryList();
 if (contactMechTypeId != null) {
 List<GenericValue> tempColTemp = new LinkedList<GenericValue>();
 for (GenericValue partyContactMech: tempCol) {
 GenericValue contactMech = delegator.getRelatedOne("ContactMech", partyContactMech, false);
 if (contactMech != null && contactMechTypeId.equals(contactMech.getString("contactMechTypeId"))) {
 tempColTemp.add(partyContactMech);
                    }


                }
 tempCol = tempColTemp;
            }
 if (!showOld) tempCol = EntityUtil.filterByDate(tempCol, true);
 allFacilityContactMechs = tempCol;
        } catch (GenericEntityException e) {
 Debug.logWarning(e, module);
        }


 if (allFacilityContactMechs == null) return facilityContactMechValueMaps;


 for (GenericValue facilityContactMech: allFacilityContactMechs) {
 GenericValue contactMech = null;


 try {
 contactMech = facilityContactMech.getRelatedOne("ContactMech", false);
            } catch (GenericEntityException e) {
 Debug.logWarning(e, module);
            }
 if (contactMech != null) {
 Map<String, Object> facilityContactMechValueMap = new HashMap<String, Object>();


 facilityContactMechValueMaps.add(facilityContactMechValueMap);
 facilityContactMechValueMap.put("contactMech", contactMech);
 facilityContactMechValueMap.put("facilityContactMech", facilityContactMech);


 try {
 facilityContactMechValueMap.put("contactMechType", contactMech.getRelatedOne("ContactMechType", true));
                } catch (GenericEntityException e) {
 Debug.logWarning(e, module);
                }


 try {
 List<GenericValue> facilityContactMechPurposes = facilityContactMech.getRelated("FacilityContactMechPurpose", null, null, false);


 if (!showOld) facilityContactMechPurposes = EntityUtil.filterByDate(facilityContactMechPurposes, true);
 facilityContactMechValueMap.put("facilityContactMechPurposes", facilityContactMechPurposes);
                } catch (GenericEntityException e) {
 Debug.logWarning(e, module);
                }


 try {
 if ("POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId"))) {
 facilityContactMechValueMap.put("postalAddress", contactMech.getRelatedOne("PostalAddress", false));
                    } else if ("TELECOM_NUMBER".equals(contactMech.getString("contactMechTypeId"))) {
 facilityContactMechValueMap.put("telecomNumber", contactMech.getRelatedOne("TelecomNumber", false));
                    }
                } catch (GenericEntityException e) {
 Debug.logWarning(e, module);
                }
            }
        }


 return facilityContactMechValueMaps;
    }