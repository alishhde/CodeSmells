@Entity
public class Tower extends Item {


 private Fit fit;
 private String tubing;


 public static enum Fit {
 Custom,
 Exact,
 Universal
    }


 public Fit getFit() {
 return fit;
    }


 public void setFit(Fit fit) {
 this.fit = fit;
    }


 public String getTubing() {
 return tubing;
    }


 public void setTubing(String tubing) {
 this.tubing = tubing;
    }


    ;
}