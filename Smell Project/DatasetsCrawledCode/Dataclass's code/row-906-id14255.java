 static class Event {


 final Type type;
 final TruffleFile file;
 final IOException ioe;
 final BasicFileAttributes attrs;


 Event(Type type, TruffleFile file, BasicFileAttributes attrs) {
 this.type = type;
 this.file = file;
 this.attrs = attrs;
 this.ioe = null;
            }


 Event(Type type, TruffleFile file, IOException ioe) {
 this.type = type;
 this.file = file;
 this.attrs = null;
 this.ioe = ioe;
            }


 enum Type {
 PRE_VISIT_DIRECTORY,
 VISIT,
 POST_VISIT_DIRECTORY
            }
        }