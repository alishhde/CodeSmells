 private class BDMVSAXHandler extends DefaultHandler
	{
 private String current_tag;
 private StringBuffer buff = new StringBuffer();
 private boolean insideTitle;
 private boolean insideDescription;
 private int maxThumbSize = -1;
 public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
 if ("di:title".equalsIgnoreCase(qName))
			{
 insideTitle = true;
			}
 else if ("di:description".equalsIgnoreCase(qName))
			{
 insideDescription = true;
			}
 else if (insideDescription && "di:thumbnail".equals(qName))
			{
 String thumbStr = attributes.getValue("href");
 String sizeStr = attributes.getValue("size");
 if (thumbStr != null && sizeStr != null)
				{
 int xidx = sizeStr.indexOf('x');
 if (xidx != -1)
					{
 int currSize = 0;
 try
						{
 currSize = Integer.parseInt(sizeStr.substring(0, xidx)) * Integer.parseInt(sizeStr.substring(xidx + 1));
						}
 catch (NumberFormatException nfe)
						{
 if (sage.Sage.DBG) System.out.println("ERROR could not extract BDMV thumbnail size of :" + nfe + " from " + sizeStr);
						}
 if (currSize > maxThumbSize)
						{
 metaThumbnail = new java.io.File(new java.io.File(bdmvDir, "META" + java.io.File.separator + "DL"), thumbStr).getAbsolutePath();
						}
					}
				}
			}
 current_tag = qName;
		}
 public void characters(char[] ch, int start, int length)
		{
 String data = new String(ch,start,length);


 //Jump blank chunk
 if (data.trim().length() == 0)
 return;
 buff.append(data);
		}
 public void endElement(String uri, String localName, String qName)
		{
 String data = buff.toString().trim();


 if (qName.equals(current_tag))
 buff = new StringBuffer();
 if ("di:title".equals(qName))
 insideTitle = false;
 else if ("di:description".equals(qName))
 insideDescription = false;
 else if (insideTitle && "di:name".equals(qName))
			{
 metaTitle = data;
			}
		}
	}