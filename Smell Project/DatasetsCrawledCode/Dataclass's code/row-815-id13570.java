class ResourceMethodConfigImpl implements ResourceMethodConfig
{
 private final ConfigValue<Long> _timeoutMs;


 ResourceMethodConfigImpl(ConfigValue<Long> timeoutMs)
  {
 _timeoutMs = timeoutMs;
  }


 public ConfigValue<Long> getTimeoutMs()
  {
 return _timeoutMs;
  }


 @Override
 public String toString()
  {
 return "ResourceMethodConfigImpl{" +
 "_timeoutMs=" + _timeoutMs +
 '}';
  }


 @Override
 public boolean equals(Object o)
  {
 if (this == o) return true;
 if (o == null || getClass() != o.getClass()) return false;
 ResourceMethodConfigImpl that = (ResourceMethodConfigImpl) o;
 return Objects.equals(_timeoutMs, that._timeoutMs);
  }


 @Override
 public int hashCode()
  {
 return Objects.hash(_timeoutMs);
  }
}