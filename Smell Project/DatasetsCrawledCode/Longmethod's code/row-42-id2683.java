 @Override
 public void writeEdge(I srcId, V srcValue, Edge<I, E> edge)
 throws IOException, InterruptedException {


 if (txcounter == txsize) {
 txcounter = 0;
 isFirstElement = true;
 stopConnection();
 startConnection();
      }


 try {
 JSONObject jsonEdge;
 String suffix;


 /* extract the JSON object of the vertex */
 jsonEdge = getEdge(srcId, srcValue, edge);
 /* determine the suffix to add the object into the JSON array */
 if (isFirstElement) {
 isFirstElement = false;
 suffix = "";
        } else {
 suffix = ",";
        }
 rexsterBufferedStream.write(suffix + jsonEdge);
 txcounter += 1;


      } catch (JSONException e) {
 throw new InterruptedException("Error writing the edge: " +
 e.getMessage());
      }
    }