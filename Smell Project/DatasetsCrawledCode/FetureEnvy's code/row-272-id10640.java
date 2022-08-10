 private XMLEvent expectTag(String expected, boolean allowEnd)
 throws IOException {
 XMLEvent ev = null;
 while (true) {
 try {
 ev = events.nextEvent();
      } catch (XMLStreamException e) {
 throw new IOException("Expecting " + expected +
 ", but got XMLStreamException", e);
      }
 switch (ev.getEventType()) {
 case XMLEvent.ATTRIBUTE:
 throw new IOException("Got unexpected attribute: " + ev);
 case XMLEvent.CHARACTERS:
 if (!ev.asCharacters().isWhiteSpace()) {
 throw new IOException("Got unxpected characters while " +
 "looking for " + expected + ": " +
 ev.asCharacters().getData());
        }
 break;
 case XMLEvent.END_ELEMENT:
 if (!allowEnd) {
 throw new IOException("Got unexpected end event " +
 "while looking for " + expected);
        }
 return ev;
 case XMLEvent.START_ELEMENT:
 if (!expected.startsWith("[")) {
 if (!ev.asStartElement().getName().getLocalPart().
 equals(expected)) {
 throw new IOException("Failed to find <" + expected + ">; " +
 "got " + ev.asStartElement().getName().getLocalPart() +
 " instead.");
          }
        }
 return ev;
 default:
 // Ignore other event types like comment, etc.
 if (LOG.isTraceEnabled()) {
 LOG.trace("Skipping XMLEvent of type " +
 ev.getEventType() + "(" +  ev + ")");
        }
 break;
      }
    }
  }