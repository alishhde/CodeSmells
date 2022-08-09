 abstract static class RequestParamsBuilder<T> {
 T body;


 public RequestParamsBuilder(T body) {
 this.body = body;
    }


 abstract RequestParams buildRequestParams();


 void setBody(T body) {
 this.body = body;
    }
  }