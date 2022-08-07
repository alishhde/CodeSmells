 private abstract static class ElementParser {


 private final String baseUri;
 private final String tag;


 private final ElementParser parent;
 private final List<Pair<String, Object>> normalizedAttributes;


 public ElementParser(ElementParser parent, String baseUri, String tag) {
 this.parent = parent;
 this.baseUri = baseUri;
 this.tag = tag;
 this.normalizedAttributes = new LinkedList<>();
    }


 public final Object parse(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
 String tagName;
 boolean foundStartTag = false;
 int skippingElementDepth = 0;
 while (true) {
 int eventType = xmlParser.getEventType();
 switch (eventType) {
 case XmlPullParser.START_TAG:
 tagName = xmlParser.getName();
 if (tag.equals(tagName)) {
 foundStartTag = true;
 parseStartTag(xmlParser);
            } else if (foundStartTag) {
 if (skippingElementDepth > 0) {
 skippingElementDepth++;
              } else if (handleChildInline(tagName)) {
 parseStartTag(xmlParser);
              } else {
 ElementParser childElementParser = newChildParser(this, tagName, baseUri);
 if (childElementParser == null) {
 skippingElementDepth = 1;
                } else {
 addChild(childElementParser.parse(xmlParser));
                }
              }
            }
 break;
 case XmlPullParser.TEXT:
 if (foundStartTag && skippingElementDepth == 0) {
 parseText(xmlParser);
            }
 break;
 case XmlPullParser.END_TAG:
 if (foundStartTag) {
 if (skippingElementDepth > 0) {
 skippingElementDepth--;
              } else {
 tagName = xmlParser.getName();
 parseEndTag(xmlParser);
 if (!handleChildInline(tagName)) {
 return build();
                }
              }
            }
 break;
 case XmlPullParser.END_DOCUMENT:
 return null;
 default:
 // Do nothing.
 break;
        }
 xmlParser.next();
      }
    }


 private ElementParser newChildParser(ElementParser parent, String name, String baseUri) {
 if (QualityLevelParser.TAG.equals(name)) {
 return new QualityLevelParser(parent, baseUri);
      } else if (ProtectionParser.TAG.equals(name)) {
 return new ProtectionParser(parent, baseUri);
      } else if (StreamIndexParser.TAG.equals(name)) {
 return new StreamIndexParser(parent, baseUri);
      }
 return null;
    }


 /**
     * Stash an attribute that may be normalized at this level. In other words, an attribute that
     * may have been pulled up from the child elements because its value was the same in all
     * children.
     * <p>
     * Stashing an attribute allows child element parsers to retrieve the values of normalized
     * attributes using {@link #getNormalizedAttribute(String)}.
     *
     * @param key The name of the attribute.
     * @param value The value of the attribute.
     */
 protected final void putNormalizedAttribute(String key, Object value) {
 normalizedAttributes.add(Pair.create(key, value));
    }


 /**
     * Attempt to retrieve a stashed normalized attribute. If there is no stashed attribute with
     * the provided name, the parent element parser will be queried, and so on up the chain.
     *
     * @param key The name of the attribute.
     * @return The stashed value, or null if the attribute was not be found.
     */
 protected final Object getNormalizedAttribute(String key) {
 for (int i = 0; i < normalizedAttributes.size(); i++) {
 Pair<String, Object> pair = normalizedAttributes.get(i);
 if (pair.first.equals(key)) {
 return pair.second;
        }
      }
 return parent == null ? null : parent.getNormalizedAttribute(key);
    }


 /**
     * Whether this {@link ElementParser} parses a child element inline.
     *
     * @param tagName The name of the child element.
     * @return Whether the child is parsed inline.
     */
 protected boolean handleChildInline(String tagName) {
 return false;
    }


 /**
     * @param xmlParser The underlying {@link XmlPullParser}
     * @throws ParserException If a parsing error occurs.
     */
 protected void parseStartTag(XmlPullParser xmlParser) throws ParserException {
 // Do nothing.
    }


 /**
     * @param xmlParser The underlying {@link XmlPullParser}
     */
 protected void parseText(XmlPullParser xmlParser) {
 // Do nothing.
    }


 /**
     * @param xmlParser The underlying {@link XmlPullParser}
     */
 protected void parseEndTag(XmlPullParser xmlParser) {
 // Do nothing.
    }


 /**
     * @param parsedChild A parsed child object.
     */
 protected void addChild(Object parsedChild) {
 // Do nothing.
    }


 protected abstract Object build();


 protected final String parseRequiredString(XmlPullParser parser, String key)
 throws MissingFieldException {
 String value = parser.getAttributeValue(null, key);
 if (value != null) {
 return value;
      } else {
 throw new MissingFieldException(key);
      }
    }


 protected final int parseInt(XmlPullParser parser, String key, int defaultValue)
 throws ParserException {
 String value = parser.getAttributeValue(null, key);
 if (value != null) {
 try {
 return Integer.parseInt(value);
        } catch (NumberFormatException e) {
 throw new ParserException(e);
        }
      } else {
 return defaultValue;
      }
    }


 protected final int parseRequiredInt(XmlPullParser parser, String key) throws ParserException {
 String value = parser.getAttributeValue(null, key);
 if (value != null) {
 try {
 return Integer.parseInt(value);
        } catch (NumberFormatException e) {
 throw new ParserException(e);
        }
      } else {
 throw new MissingFieldException(key);
      }
    }


 protected final long parseLong(XmlPullParser parser, String key, long defaultValue)
 throws ParserException {
 String value = parser.getAttributeValue(null, key);
 if (value != null) {
 try {
 return Long.parseLong(value);
        } catch (NumberFormatException e) {
 throw new ParserException(e);
        }
      } else {
 return defaultValue;
      }
    }


 protected final long parseRequiredLong(XmlPullParser parser, String key)
 throws ParserException {
 String value = parser.getAttributeValue(null, key);
 if (value != null) {
 try {
 return Long.parseLong(value);
        } catch (NumberFormatException e) {
 throw new ParserException(e);
        }
      } else {
 throw new MissingFieldException(key);
      }
    }


 protected final boolean parseBoolean(XmlPullParser parser, String key, boolean defaultValue) {
 String value = parser.getAttributeValue(null, key);
 if (value != null) {
 return Boolean.parseBoolean(value);
      } else {
 return defaultValue;
      }
    }


  }