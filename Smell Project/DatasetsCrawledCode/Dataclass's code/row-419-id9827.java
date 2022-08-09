@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "setOrderAttributesResult",
 "responseMetadata"
})
@XmlRootElement(name = "SetOrderAttributesResponse")
public class SetOrderAttributesResponse {






 @XmlElement(name = "SetOrderAttributesResult", required = true)


 protected SetOrderAttributesResult setOrderAttributesResult;
 @XmlElement(name = "ResponseMetadata", required = true)
 protected ResponseMetadata responseMetadata;


 public SetOrderAttributesResponse() {
 super();
    }


 public SetOrderAttributesResult getSetOrderAttributesResult() {
 return setOrderAttributesResult;
    }
 public ResponseMetadata getResponseMetadata() {
 return responseMetadata;
    }
}