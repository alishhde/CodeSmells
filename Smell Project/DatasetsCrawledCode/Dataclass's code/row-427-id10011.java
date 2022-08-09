 private class MouseObservationEvent extends ObservationEvent
    {
 private int deltaX;
 private int deltaY;
 private int deltaZ;


 public MouseObservationEvent(int deltaX, int deltaY, int deltaZ)
        {
 super();
 this.deltaX = deltaX;
 this.deltaY = deltaY;
 this.deltaZ = deltaZ;
        }


 @Override
 public JsonObject getJSON()
        {
 JsonObject jsonEvent = new JsonObject();
 jsonEvent.addProperty("time", this.timestamp);
 jsonEvent.addProperty("type", "mouse");
 jsonEvent.addProperty("deltaX", this.deltaX);
 jsonEvent.addProperty("deltaY", this.deltaY);
 jsonEvent.addProperty("deltaZ", this.deltaZ);
 return jsonEvent;
        }
    }