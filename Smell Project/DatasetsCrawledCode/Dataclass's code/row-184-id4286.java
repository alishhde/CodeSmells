 public static class Module_Param_Boolean extends Module_Parameter {


 private final boolean boolean_value;


 public type_t get_type() {
 return type_t.MP_Boolean;
		}


 public Module_Param_Boolean(final boolean p) {
 boolean_value = p;
		}


 public boolean get_boolean() {
 return boolean_value;
		}


 public String get_type_str() {
 return "boolean";
		}


 @Override
 public void log_value() {
 new TitanBoolean(boolean_value).log();
		}
	}