 public static class RequireCapability
    {
 private final String namespace;


 private final String filter;


 private final String effective;


 public RequireCapability ( final String namespace, final String filter, final String effective )
        {
 this.namespace = namespace;
 this.filter = filter;
 this.effective = effective;
        }


 public String getNamespace ()
        {
 return this.namespace;
        }


 public String getFilter ()
        {
 return this.filter;
        }


 public String getEffective ()
        {
 return this.effective;
        }
    }