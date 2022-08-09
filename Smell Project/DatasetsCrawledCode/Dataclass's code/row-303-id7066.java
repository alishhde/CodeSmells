public class ReportInstance extends AbstractDTOBase {


 private String id;
 private ReportStatusEnum status;
 private String url;
 private String ownerId;
 private Boolean hasDetailRows;
 private ZonedDateTime completionDate;
 private ZonedDateTime requestDate;


 public String getId() {
 return id;
    }


 public void setId(String id) {
 this.id = id;
    }


 public ReportStatusEnum getStatus() {
 return status;
    }


 public void setStatus(ReportStatusEnum status) {
 this.status = status;
    }


 public String getUrl() {
 return url;
    }


 public void setUrl(String url) {
 this.url = url;
    }


 public String getOwnerId() {
 return ownerId;
    }


 public void setOwnerId(String ownerId) {
 this.ownerId = ownerId;
    }


 public Boolean getHasDetailRows() {
 return hasDetailRows;
    }


 public void setHasDetailRows(Boolean hasDetailRows) {
 this.hasDetailRows = hasDetailRows;
    }


 public ZonedDateTime getCompletionDate() {
 return completionDate;
    }


 public void setCompletionDate(ZonedDateTime completionDate) {
 this.completionDate = completionDate;
    }


 public ZonedDateTime getRequestDate() {
 return requestDate;
    }


 public void setRequestDate(ZonedDateTime requestDate) {
 this.requestDate = requestDate;
    }
}