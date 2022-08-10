 @Override
 public Component getNextComponent(final Container container, final Component component,
 final FocusTraversalDirection direction) {
 Utils.checkNull(container, "container");
 Utils.checkNull(direction, "direction");


 Component nextComponent = null;


 int n = container.getLength();
 if (n > 0) {
 switch (direction) {
 case FORWARD:
 if (component == null) {
 // Return the first component in the sequence
 nextComponent = container.get(0);
                        } else {
 // Return the next component in the sequence
 int index = container.indexOf(component);
 if (index == -1) {
 throw new IllegalArgumentException("Component is not a child of the container.");
                            }


 if (index < n - 1) {
 nextComponent = container.get(index + 1);
                            } else {
 if (wrap) {
 nextComponent = container.get(0);
                                }
                            }
                        }


 break;


 case BACKWARD:
 if (component == null) {
 // Return the last component in the sequence
 nextComponent = container.get(n - 1);
                        } else {
 // Return the previous component in the sequence
 int index = container.indexOf(component);
 if (index == -1) {
 throw new IllegalArgumentException("Component is not a child of the container.");
                            }


 if (index > 0) {
 nextComponent = container.get(index - 1);
                            } else {
 if (wrap) {
 nextComponent = container.get(n - 1);
                                }
                            }
                        }


 break;


 default:
 break;
                }
            }


 return nextComponent;
        }