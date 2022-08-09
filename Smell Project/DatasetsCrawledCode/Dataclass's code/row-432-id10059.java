 static class State {


 private EnabledState enabled = EnabledState.UNKNOWN;
 public JavaFormatterOptions.Style style = JavaFormatterOptions.Style.GOOGLE;


 // enabled used to be a boolean so we use bean property methods for backwards compatibility
 public void setEnabled(@Nullable String enabledStr) {
 if (enabledStr == null) {
 enabled = EnabledState.UNKNOWN;
      } else if (Boolean.valueOf(enabledStr)) {
 enabled = EnabledState.ENABLED;
      } else {
 enabled = EnabledState.DISABLED;
      }
    }


 public String getEnabled() {
 switch (enabled) {
 case ENABLED:
 return "true";
 case DISABLED:
 return "false";
 default:
 return null;
      }
    }
  }