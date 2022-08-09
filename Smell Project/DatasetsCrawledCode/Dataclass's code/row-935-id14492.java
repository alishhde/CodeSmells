@XmlRootElement(name="mqtt")
@XmlAccessorType(XmlAccessType.FIELD)
public class MqttDTO extends ProtocolDTO {


 @XmlAttribute(name="max_message_length")
 public Integer max_message_length;


 @XmlElementRef
 public List<ProtocolFilterDTO> protocol_filters = new ArrayList<ProtocolFilterDTO>();


 @XmlAttribute(name="queue_prefix")
 public String queue_prefix;


 @XmlAttribute(name="path_separator")
 public String path_separator;


 @XmlAttribute(name="any_child_wildcard")
 public String any_child_wildcard;


 @XmlAttribute(name="any_descendant_wildcard")
 public String any_descendant_wildcard;


 @XmlAttribute(name="regex_wildcard_start")
 public String regex_wildcard_start;


 @XmlAttribute(name="regex_wildcard_end")
 public String regex_wildcard_end;


 @XmlAttribute(name="part_pattern")
 public String part_pattern;


 @XmlAttribute(name="die_delay")
 public Long die_delay;


 @Override
 public boolean equals(Object o) {
 if (this == o) return true;
 if (o == null || getClass() != o.getClass()) return false;
 if (!super.equals(o)) return false;


 MqttDTO mqttDTO = (MqttDTO) o;


 if (any_child_wildcard != null ? !any_child_wildcard.equals(mqttDTO.any_child_wildcard) : mqttDTO.any_child_wildcard != null)
 return false;
 if (any_descendant_wildcard != null ? !any_descendant_wildcard.equals(mqttDTO.any_descendant_wildcard) : mqttDTO.any_descendant_wildcard != null)
 return false;
 if (max_message_length != null ? !max_message_length.equals(mqttDTO.max_message_length) : mqttDTO.max_message_length != null)
 return false;
 if (path_separator != null ? !path_separator.equals(mqttDTO.path_separator) : mqttDTO.path_separator != null)
 return false;
 if (protocol_filters != null ? !protocol_filters.equals(mqttDTO.protocol_filters) : mqttDTO.protocol_filters != null)
 return false;
 if (queue_prefix != null ? !queue_prefix.equals(mqttDTO.queue_prefix) : mqttDTO.queue_prefix != null)
 return false;
 if (regex_wildcard_end != null ? !regex_wildcard_end.equals(mqttDTO.regex_wildcard_end) : mqttDTO.regex_wildcard_end != null)
 return false;
 if (regex_wildcard_start != null ? !regex_wildcard_start.equals(mqttDTO.regex_wildcard_start) : mqttDTO.regex_wildcard_start != null)
 return false;
 if (part_pattern != null ? !part_pattern.equals(mqttDTO.part_pattern) : mqttDTO.part_pattern != null)
 return false;


 return true;
    }


 @Override
 public int hashCode() {
 int result = super.hashCode();
 result = 31 * result + (max_message_length != null ? max_message_length.hashCode() : 0);
 result = 31 * result + (protocol_filters != null ? protocol_filters.hashCode() : 0);
 result = 31 * result + (queue_prefix != null ? queue_prefix.hashCode() : 0);
 result = 31 * result + (part_pattern != null ? part_pattern.hashCode() : 0);
 result = 31 * result + (path_separator != null ? path_separator.hashCode() : 0);
 result = 31 * result + (any_child_wildcard != null ? any_child_wildcard.hashCode() : 0);
 result = 31 * result + (any_descendant_wildcard != null ? any_descendant_wildcard.hashCode() : 0);
 result = 31 * result + (regex_wildcard_start != null ? regex_wildcard_start.hashCode() : 0);
 result = 31 * result + (regex_wildcard_end != null ? regex_wildcard_end.hashCode() : 0);
 return result;
    }
}