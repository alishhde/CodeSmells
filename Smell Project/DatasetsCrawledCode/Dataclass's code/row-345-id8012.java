public class Shape {


 private String type;


 private Map<String, Member> members = Collections.emptyMap();


 private String documentation;


 private List<String> required;


 private List<String> enumValues;


 private String payload;


 private boolean flattened;


 private boolean exception;


 private boolean streaming;


 private boolean wrapper;


 private Member listMember;


 private Member mapKeyType;


 private Member mapValueType;


 @JsonProperty(value = "error")
 private ErrorTrait errorTrait;


 private long min;


 private long max;


 private String pattern;


 private boolean fault;


 private boolean deprecated;


 @JsonProperty(value = "eventstream")
 private boolean isEventStream;


 @JsonProperty(value = "event")
 private boolean isEvent;


 private String timestampFormat;


 private boolean sensitive;


 public boolean isFault() {
 return fault;
    }


 public void setFault(boolean fault) {
 this.fault = fault;
    }


 public String getPattern() {
 return pattern;
    }


 public void setPattern(String pattern) {
 this.pattern = pattern;
    }


 public String getType() {
 return type;
    }


 public void setType(String type) {
 this.type = type;
    }


 public Map<String, Member> getMembers() {
 return members;
    }


 public void setMembers(Map<String, Member> members) {
 this.members = members;
    }


 public String getDocumentation() {
 return documentation;
    }


 public void setDocumentation(String documentation) {
 this.documentation = documentation;
    }


 public List<String> getRequired() {
 return required;
    }


 public void setRequired(List<String> required) {
 this.required = required;
    }


 public List<String> getEnumValues() {
 return enumValues;
    }


 @JsonProperty(value = "enum")
 public void setEnumValues(List<String> enumValues) {
 this.enumValues = enumValues;
    }


 public String getPayload() {
 return payload;
    }


 public void setPayload(String payload) {
 this.payload = payload;
    }


 public boolean isFlattened() {
 return flattened;
    }


 public void setFlattened(boolean flattened) {
 this.flattened = flattened;
    }


 public boolean isException() {
 return exception;
    }


 public void setException(boolean exception) {
 this.exception = exception;
    }


 public Member getMapKeyType() {
 return mapKeyType;
    }


 @JsonProperty(value = "key")
 public void setMapKeyType(Member mapKeyType) {
 this.mapKeyType = mapKeyType;
    }


 public Member getMapValueType() {
 return mapValueType;
    }


 @JsonProperty(value = "value")
 public void setMapValueType(Member mapValueType) {
 this.mapValueType = mapValueType;
    }


 public Member getListMember() {
 return listMember;
    }


 @JsonProperty(value = "member")
 public void setListMember(Member listMember) {
 this.listMember = listMember;
    }


 public long getMin() {
 return min;
    }


 public void setMin(long min) {
 this.min = min;
    }


 public long getMax() {
 return max;
    }


 public void setMax(long max) {
 this.max = max;
    }


 public boolean isStreaming() {
 return streaming;
    }


 public void setStreaming(boolean streaming) {
 this.streaming = streaming;
    }


 public boolean isWrapper() {
 return wrapper;
    }


 public void setWrapper(boolean wrapper) {
 this.wrapper = wrapper;
    }


 public ErrorTrait getErrorTrait() {
 return errorTrait;
    }


 public void setErrorTrait(ErrorTrait errorTrait) {
 this.errorTrait = errorTrait;
    }


 public boolean isDeprecated() {
 return deprecated;
    }


 public void setDeprecated(boolean deprecated) {
 this.deprecated = deprecated;
    }


 public boolean isEventStream() {
 return isEventStream;
    }


 public void setIsEventStream(boolean eventStream) {
 isEventStream = eventStream;
    }


 public boolean isEvent() {
 return isEvent;
    }


 public void setIsEvent(boolean event) {
 isEvent = event;
    }


 public String getTimestampFormat() {
 return timestampFormat;
    }


 public void setTimestampFormat(String timestampFormat) {
 this.timestampFormat = timestampFormat;
    }


 public boolean isSensitive() {
 return sensitive;
    }


 public void setSensitive(boolean sensitive) {
 this.sensitive = sensitive;
    }
}