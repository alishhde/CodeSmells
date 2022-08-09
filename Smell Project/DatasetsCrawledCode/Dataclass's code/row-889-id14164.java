public final class ConfigBoolean extends ConfigVariable
{
 public ConfigBoolean(OptionSpec spec)
    {
 super(spec);


 this.enabled = false;
 this.isSet = false;
    }


 public ConfigBoolean(OptionSpec spec, boolean enabled)
    {
 super(spec);
 this.set(enabled);
    }
 
 private boolean enabled;
 private boolean isSet;




 public void set(boolean value)
    {
 this.enabled = value;
 this.isSet = true;
    }


 public void set(String value)
    {
 this.enabled = parseValue(value);
 this.isSet = true;
    }


 public boolean isSet()
    {
 return isSet;
    	}


 public void addToCommandline(Commandline cmdline)
    {
 if (isSet)
 cmdline.createArgument(true).setValue("-" + spec.getFullName() + "=" + enabled);
    }


 private boolean parseValue(String value)
    {
 return value.toLowerCase().matches("\\s*(true|yes|on)\\s*");
    }
}