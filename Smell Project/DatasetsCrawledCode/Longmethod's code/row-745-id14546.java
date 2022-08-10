 @Override
 public void configureInputJobProperties(TableDesc tableDesc,
 Map<String, String> jobProperties) {


 try {
 Map<String, String> tableProperties = tableDesc.getJobProperties();


 String jobInfoProperty = tableProperties.get(HCatConstants.HCAT_KEY_JOB_INFO);
 if (jobInfoProperty != null) {


 LinkedList<InputJobInfo> inputJobInfos = (LinkedList<InputJobInfo>) HCatUtil.deserialize(
 jobInfoProperty);
 if (inputJobInfos == null || inputJobInfos.isEmpty()) {
 throw new IOException("No InputJobInfo was set in job config");
        }
 InputJobInfo inputJobInfo = inputJobInfos.getLast();


 HCatTableInfo tableInfo = inputJobInfo.getTableInfo();
 HCatSchema dataColumns = tableInfo.getDataColumns();
 List<HCatFieldSchema> dataFields = dataColumns.getFields();
 StringBuilder columnNamesSb = new StringBuilder();
 StringBuilder typeNamesSb = new StringBuilder();
 for (HCatFieldSchema dataField : dataFields) {
 if (columnNamesSb.length() > 0) {
 columnNamesSb.append(",");
 typeNamesSb.append(":");
          }
 columnNamesSb.append(dataField.getName());
 typeNamesSb.append(dataField.getTypeString());
        }
 jobProperties.put(IOConstants.SCHEMA_EVOLUTION_COLUMNS, columnNamesSb.toString());
 jobProperties.put(IOConstants.SCHEMA_EVOLUTION_COLUMNS_TYPES, typeNamesSb.toString());


 boolean isTransactionalTable = AcidUtils.isTablePropertyTransactional(tableProperties);
 AcidUtils.AcidOperationalProperties acidOperationalProperties =
 AcidUtils.getAcidOperationalProperties(tableProperties);
 AcidUtils.setAcidOperationalProperties(
 jobProperties, isTransactionalTable, acidOperationalProperties);
      }
    } catch (IOException e) {
 throw new IllegalStateException("Failed to set output path", e);
    }


  }