 private SimpleDateFormat createFormat(DateResolution dateResolution) {
 switch (dateResolution) {
 case Year:
 return new SimpleDateFormat("yyyy");
 case Month:
 return new SimpleDateFormat("yyyyMM");
 case Day:
 return new SimpleDateFormat("yyyyMMdd");
 case Hour:
 return new SimpleDateFormat("yyyyMMddhh");
 case Minute:
 return new SimpleDateFormat("yyyyMMddhhmm");
 case Second:
 return new SimpleDateFormat("yyyyMMddhhmmss");
 default:
 return new SimpleDateFormat("yyyyMMddhhmmssSSS");
        }
    }