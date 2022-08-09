 private static class GrailsContentProposal implements IContentProposal, Comparable<GrailsContentProposal> {


 private String fLabel;


 private String fContent;


 private String fDescription;


 private Image fImage;


 public GrailsContentProposal(String label, String content, String description, Image image) {
 fLabel = label;
 fContent = content;
 fDescription = description;
 fImage = image;
		}


 public String getContent() {
 return fContent;
		}


 public int getCursorPosition() {
 if (fContent != null) {
 return fContent.length();
			}
 return 0;
		}


 public String getDescription() {
 return fDescription;
		}


 public String getLabel() {
 return fLabel;
		}


 @SuppressWarnings("unused")
 public Image getImage() {
 return fImage;
		}


 public String toString() {
 return fLabel;
		}


 public int compareTo(GrailsContentProposal o) {
 return this.fContent.compareTo(o.fContent);
		}
	}