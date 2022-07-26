public class Config {


 public String mysqlAddr;
 public Integer mysqlPort;
 public String mysqlUsername;
 public String mysqlPassword;


 public String mqNamesrvAddr;
 public String mqTopic;


 public String startType = "DEFAULT";
 public String binlogFilename;
 public Long nextPosition;
 public Integer maxTransactionRows = 100;


 public void load() throws IOException {


 InputStream in = Config.class.getClassLoader().getResourceAsStream("rocketmq_mysql.conf");
 Properties properties = new Properties();
 properties.load(in);


 properties2Object(properties, this);


    }


 private void properties2Object(final Properties p, final Object object) {
 Method[] methods = object.getClass().getMethods();
 for (Method method : methods) {
 String mn = method.getName();
 if (mn.startsWith("set")) {
 try {
 String tmp = mn.substring(4);
 String first = mn.substring(3, 4);


 String key = first.toLowerCase() + tmp;
 String property = p.getProperty(key);
 if (property != null) {
 Class<?>[] pt = method.getParameterTypes();
 if (pt != null && pt.length > 0) {
 String cn = pt[0].getSimpleName();
 Object arg;
 if (cn.equals("int") || cn.equals("Integer")) {
 arg = Integer.parseInt(property);
                            } else if (cn.equals("long") || cn.equals("Long")) {
 arg = Long.parseLong(property);
                            } else if (cn.equals("double") || cn.equals("Double")) {
 arg = Double.parseDouble(property);
                            } else if (cn.equals("boolean") || cn.equals("Boolean")) {
 arg = Boolean.parseBoolean(property);
                            } else if (cn.equals("float") || cn.equals("Float")) {
 arg = Float.parseFloat(property);
                            } else if (cn.equals("String")) {
 arg = property;
                            } else {
 continue;
                            }
 method.invoke(object, arg);
                        }
                    }
                } catch (Throwable ignored) {
                }
            }
        }
    }


 public void setMysqlAddr(String mysqlAddr) {
 this.mysqlAddr = mysqlAddr;
    }


 public void setMysqlPort(Integer mysqlPort) {
 this.mysqlPort = mysqlPort;
    }


 public void setMysqlUsername(String mysqlUsername) {
 this.mysqlUsername = mysqlUsername;
    }


 public void setMysqlPassword(String mysqlPassword) {
 this.mysqlPassword = mysqlPassword;
    }


 public void setBinlogFilename(String binlogFilename) {
 this.binlogFilename = binlogFilename;
    }


 public void setNextPosition(Long nextPosition) {
 this.nextPosition = nextPosition;
    }


 public void setMaxTransactionRows(Integer maxTransactionRows) {
 this.maxTransactionRows = maxTransactionRows;
    }


 public void setMqNamesrvAddr(String mqNamesrvAddr) {
 this.mqNamesrvAddr = mqNamesrvAddr;
    }


 public void setMqTopic(String mqTopic) {
 this.mqTopic = mqTopic;
    }


 public void setStartType(String startType) {
 this.startType = startType;
    }
}