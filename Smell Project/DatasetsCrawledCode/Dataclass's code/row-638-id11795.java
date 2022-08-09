public class BindableSolrParameter {


 private final int index;
 private final Object value;
 private float boost;


 public BindableSolrParameter(int index, Object value) {
 super();
 this.index = index;
 this.value = value;
	}


 public float getBoost() {
 return boost;
	}


 public void setBoost(float boost) {
 this.boost = boost;
	}


 public int getIndex() {
 return index;
	}


 public Object getValue() {
 return value;
	}


}