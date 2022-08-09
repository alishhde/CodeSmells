public class User {
 private String name = "nameA";


 private int age = 100;


 private int index;


 private String[] names;


 public String getName() {
 return name;
  }


 public void setName(String name) {
 this.name = name;
  }


 public String[] getNames() {
 return names;
  }


 public void setNames(String[] names) {
 this.names = names;
  }


 public int getAge() {
 return age;
  }


 public void setAge(int age) {
 this.age = age;
  }


 public int getIndex() {
 return index;
  }


 public void setIndex(int index) {
 this.index = index;
  }


 @Override
 public String toString() {
 return "User [name=" + name + ", age=" + age + ", index=" + index + "]";
  }


 public String jsonString() {
 try {
 return JsonUtils.writeValueAsString(this);
    } catch (JsonProcessingException e) {
 throw new IllegalStateException(e);
    }
  }
}