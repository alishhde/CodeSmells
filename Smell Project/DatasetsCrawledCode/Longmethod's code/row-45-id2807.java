 static <T> Sketch<T> create(CountMinSketch sketch) {
 int width = (int) Math.ceil(2 / sketch.getRelativeError());
 int depth = (int) Math.ceil(-Math.log(1 - sketch.getConfidence()) / Math.log(2));
 return new AutoValue_SketchFrequencies_Sketch<>(depth, width, sketch);
    }