 private static class Wrap<T> extends DoFn<T, KV<Long, T>> {


 @ProcessElement
 public void processElement(ProcessContext ctx) {
 ctx.output(KV.of(ctx.timestamp().getMillis(), ctx.element()));
    }
  }