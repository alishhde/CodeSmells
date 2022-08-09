 public static class Builder{
 private final EagleServiceSingleEntityQueryRequest rawQuery;
 public Builder(){
 this.rawQuery= new EagleServiceSingleEntityQueryRequest();
        }
 public EagleServiceSingleEntityQueryRequest done(){
 return this.rawQuery;
        }
 public Builder query(String query) {
 this.rawQuery.setQuery(query);
 return this;
        }


 public Builder startTime(long startTime) {
 this.rawQuery.setStartTime(startTime);
 return this;
        }


 public Builder endTime(long endTime) {
 this.rawQuery.setEndTime(endTime);
 return this;
        }


 public Builder pageSize(int pageSize) {
 this.rawQuery.setPageSize(pageSize);
 return this;
        }


 public Builder startRowkey(String startRowkey) {
 this.rawQuery.setStartRowkey(startRowkey);
 return this;
        }


 public Builder treeAgg(boolean treeAgg) {
 this.rawQuery.setTreeAgg(treeAgg);
 return this;
        }


 public Builder filerIfMissing(boolean filterIfMissing) {
 this.rawQuery.setFilterIfMissing(filterIfMissing);
 return this;
        }


 public Builder metricName(String metricName) {
 this.rawQuery.setMetricName(metricName);
 return this;
        }


 public Builder verbose(Boolean verbose) {
 this.rawQuery.setVerbose(verbose);
 return this;
        }
    }