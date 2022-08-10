 public void configure(TestElement el) {
 setName(el.getName());
 Arguments arguments = (Arguments) el.getProperty(HTTPSamplerBase.ARGUMENTS).getObjectValue();


 boolean useRaw = el.getPropertyAsBoolean(HTTPSamplerBase.POST_BODY_RAW, HTTPSamplerBase.POST_BODY_RAW_DEFAULT);
 if(useRaw) {
 String postBody = computePostBody(arguments, true); // Convert CRLF to CR, see modifyTestElement
 postBodyContent.setInitialText(postBody); 
 postBodyContent.setCaretPosition(0);
 argsPanel.clear();
 postContentTabbedPane.setSelectedIndex(tabRawBodyIndex, false);
        } else {
 postBodyContent.setInitialText("");
 argsPanel.configure(arguments);
 postContentTabbedPane.setSelectedIndex(TAB_PARAMETERS, false);
        }
 if(showFileUploadPane) {
 filesPanel.configure(el);
        }


 domain.setText(el.getPropertyAsString(HTTPSamplerBase.DOMAIN));


 String portString = el.getPropertyAsString(HTTPSamplerBase.PORT);


 // Only display the port number if it is meaningfully specified
 if (portString.equals(HTTPSamplerBase.UNSPECIFIED_PORT_AS_STRING)) {
 port.setText(""); // $NON-NLS-1$
        } else {
 port.setText(portString);
        }
 protocol.setText(el.getPropertyAsString(HTTPSamplerBase.PROTOCOL));
 contentEncoding.setText(el.getPropertyAsString(HTTPSamplerBase.CONTENT_ENCODING));
 path.setText(el.getPropertyAsString(HTTPSamplerBase.PATH));
 if (notConfigOnly){
 method.setText(el.getPropertyAsString(HTTPSamplerBase.METHOD));
 followRedirects.setSelected(el.getPropertyAsBoolean(HTTPSamplerBase.FOLLOW_REDIRECTS));
 autoRedirects.setSelected(el.getPropertyAsBoolean(HTTPSamplerBase.AUTO_REDIRECTS));
 useKeepAlive.setSelected(el.getPropertyAsBoolean(HTTPSamplerBase.USE_KEEPALIVE));
 useMultipart.setSelected(el.getPropertyAsBoolean(HTTPSamplerBase.DO_MULTIPART_POST));
 useBrowserCompatibleMultipartMode.setSelected(el.getPropertyAsBoolean(
 HTTPSamplerBase.BROWSER_COMPATIBLE_MULTIPART, HTTPSamplerBase.BROWSER_COMPATIBLE_MULTIPART_MODE_DEFAULT));
        }
    }