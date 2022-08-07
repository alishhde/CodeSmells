 public static class Builder {
 /**
       * @see AddDomainOptions#primaryNameServer
       */
 public static AddDomainOptions primaryNameServer(String primaryNameServer) {
 return AddDomainOptions.class.cast(new AddDomainOptions().primaryNameServer(primaryNameServer));
      }


 /**
       * @see AddDomainOptions#responsiblePerson
       */
 public static AddDomainOptions responsiblePerson(String responsiblePerson) {
 return AddDomainOptions.class.cast(new AddDomainOptions().responsiblePerson(responsiblePerson));
      }


 /**
       * @see AddDomainOptions#ttl
       */
 public static AddDomainOptions ttl(int ttl) {
 return AddDomainOptions.class.cast(new AddDomainOptions().ttl(ttl));
      }


 /**
       * @see AddDomainOptions#refresh
       */
 public static AddDomainOptions refresh(int refresh) {
 return AddDomainOptions.class.cast(new AddDomainOptions().refresh(refresh));
      }


 /**
       * @see AddDomainOptions#retry
       */
 public static AddDomainOptions retry(int retry) {
 return AddDomainOptions.class.cast(new AddDomainOptions().retry(retry));
      }


 /**
       * @see AddDomainOptions#expire
       */
 public static AddDomainOptions expire(int expire) {
 return AddDomainOptions.class.cast(new AddDomainOptions().expire(expire));
      }


 /**
       * @see AddDomainOptions#minimum
       */
 public static AddDomainOptions minimum(int minimum) {
 return AddDomainOptions.class.cast(new AddDomainOptions().minimum(minimum));
      }


 /**
       * @see AddDomainOptions#minimalRecords
       */
 public static AddDomainOptions minimalRecords() {
 return AddDomainOptions.class.cast(new AddDomainOptions().minimalRecords());
      }
   }