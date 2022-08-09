 class Whitespace implements Text {
 private String text;
 public Whitespace(String text) {
 this.text = text;
        }
 @Override
 public String getText() {
 return text;
        }
    }