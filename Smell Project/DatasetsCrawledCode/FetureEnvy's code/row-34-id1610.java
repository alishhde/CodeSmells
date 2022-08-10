 @Override
 public ParseSpec withTimestampSpec(TimestampSpec spec)
  {
 return new TimeAndDimsParseSpec(spec, getDimensionsSpec());
  }