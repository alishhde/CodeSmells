public class ConsoleProxyClientParam {
 private String clientHostAddress;
 private int clientHostPort;
 private String clientHostPassword;
 private String clientTag;
 private String ticket;
 private String locale;
 private String clientTunnelUrl;
 private String clientTunnelSession;


 private String hypervHost;


 private String ajaxSessionId;
 private String username;
 private String password;


 public ConsoleProxyClientParam() {
 clientHostPort = 0;
    }


 public String getClientHostAddress() {
 return clientHostAddress;
    }


 public void setClientHostAddress(String clientHostAddress) {
 this.clientHostAddress = clientHostAddress;
    }


 public int getClientHostPort() {
 return clientHostPort;
    }


 public void setClientHostPort(int clientHostPort) {
 this.clientHostPort = clientHostPort;
    }


 public String getClientHostPassword() {
 return clientHostPassword;
    }


 public void setClientHostPassword(String clientHostPassword) {
 this.clientHostPassword = clientHostPassword;
    }


 public String getClientTag() {
 return clientTag;
    }


 public void setClientTag(String clientTag) {
 this.clientTag = clientTag;
    }


 public String getTicket() {
 return ticket;
    }


 public void setTicket(String ticket) {
 this.ticket = ticket;
    }


 public String getClientTunnelUrl() {
 return clientTunnelUrl;
    }


 public void setClientTunnelUrl(String clientTunnelUrl) {
 this.clientTunnelUrl = clientTunnelUrl;
    }


 public String getClientTunnelSession() {
 return clientTunnelSession;
    }


 public void setClientTunnelSession(String clientTunnelSession) {
 this.clientTunnelSession = clientTunnelSession;
    }


 public String getAjaxSessionId() {
 return ajaxSessionId;
    }


 public void setAjaxSessionId(String ajaxSessionId) {
 this.ajaxSessionId = ajaxSessionId;
    }


 public String getLocale() {
 return locale;
    }


 public void setLocale(String locale) {
 this.locale = locale;
    }


 public String getClientMapKey() {
 if (clientTag != null && !clientTag.isEmpty())
 return clientTag;


 return clientHostAddress + ":" + clientHostPort;
    }


 public void setHypervHost(String host) {
 hypervHost = host;
    }


 public String getHypervHost() {
 return hypervHost;
    }


 public void setUsername(String username) {
 this.username = username;


    }


 public String getUsername() {
 return username;
    }


 public void setPassword(String password) {
 this.password = password;
    }


 public String getPassword() {
 return password;
    }
}