 @SuppressWarnings("serial")
 public static final class VertexGroupItem<K, VGV> extends Tuple4<K, K, Either<VGV, NullValue>, Long> {


 private final Either.Right<VGV, NullValue> nullValue = new Either.Right<>(NullValue.getInstance());


 public VertexGroupItem() {
 reset();
		}


 public K getVertexId() {
 return f0;
		}


 public void setVertexId(K vertexId) {
 f0 = vertexId;
		}


 public K getGroupRepresentativeId() {
 return f1;
		}


 public void setGroupRepresentativeId(K groupRepresentativeId) {
 f1 = groupRepresentativeId;
		}


 public VGV getVertexGroupValue() {
 return f2.isLeft() ? f2.left() : null;
		}


 public void setVertexGroupValue(VGV vertexGroupValue) {
 if (vertexGroupValue == null) {
 f2 = nullValue;
			} else {
 f2 = new Either.Left<>(vertexGroupValue);
			}
		}


 public Long getVertexGroupCount() {
 return f3;
		}


 public void setVertexGroupCount(Long vertexGroupCount) {
 f3 = vertexGroupCount;
		}


 /**
		 * Resets the fields to initial values. This is necessary if the tuples are reused and not all fields were modified.
		 */
 public void reset() {
 f0 = null;
 f1 = null;
 f2 = nullValue;
 f3 = 0L;
		}
	}