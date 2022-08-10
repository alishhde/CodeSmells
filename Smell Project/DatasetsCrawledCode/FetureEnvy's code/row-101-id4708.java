 public synchronized void removePipeline(Pipeline pipeline) {
 for (DatanodeDetails details : pipeline.getNodes()) {
 UUID dnId = details.getUuid();
 dn2ObjectMap.computeIfPresent(dnId,
          (k, v) -> {
 v.remove(pipeline.getId());
 return v;
          });
    }
  }