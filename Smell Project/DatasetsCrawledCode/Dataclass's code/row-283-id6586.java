public class BeanIOIterator implements Iterator<Object>, Closeable {


 private BeanReader reader;
 private transient Object next;
 private transient Object forceNext;


 public BeanIOIterator(BeanReader reader) {
 this.reader = reader;
 this.next = next();
    }


 @Override
 public void close() throws IOException {
 if (reader != null) {
 reader.close();
 reader = null;
        }
    }


 @Override
 public boolean hasNext() {
 return next != null;
    }


 @Override
 public Object next() {
 Object answer = next;
 if (answer == null) {
 answer = reader.read();
 // after read we may force a next
 if (forceNext != null) {
 answer = forceNext;
 forceNext = null;
            }
        } else {
 next = reader.read();
 // after read we may force a next
 if (forceNext != null) {
 next = forceNext;
 forceNext = null;
            }
        }
 return answer;
    }


 @Override
 public void remove() {
 // noop
    }


 /**
     * Sets a custom object as the next, such as from a custom error handler
     */
 public void setNext(Object next) {
 this.forceNext = next;
    }
}