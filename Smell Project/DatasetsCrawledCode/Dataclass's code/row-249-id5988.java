public class DeploymentPlannersResponse extends BaseResponse {
 @SerializedName(ApiConstants.NAME)
 @Param(description = "Deployment Planner name")
 private String name;


 public String getName() {
 return name;
    }


 public void setName(String name) {
 this.name = name;
    }
}