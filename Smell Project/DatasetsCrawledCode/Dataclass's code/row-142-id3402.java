 public abstract static class Builder<T extends Builder<T>>  {
 protected abstract T self();


 protected Long id;
 protected String name;
 protected String description;
 
 /** 
       * @see Option#getId()
       */
 public T id(Long id) {
 this.id = id;
 return self();
      }


 /** 
       * @see Option#getName()
       */
 public T name(String name) {
 this.name = name;
 return self();
      }


 /** 
       * @see Option#getDescription()
       */
 public T description(String description) {
 this.description = description;
 return self();
      }


 public Option build() {
 return new Option(id, name, description);
      }
 
 public T fromOption(Option in) {
 return this
                  .id(in.getId())
                  .name(in.getName())
                  .description(in.getDescription());
      }
   }