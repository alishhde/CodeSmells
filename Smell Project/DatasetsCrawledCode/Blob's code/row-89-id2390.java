public class PTransformReplacements {
 /**
   * Gets the singleton input of an {@link AppliedPTransform}, ignoring any additional inputs
   * returned by {@link PTransform#getAdditionalInputs()}.
   */
 public static <T> PCollection<T> getSingletonMainInput(
 AppliedPTransform<? extends PCollection<? extends T>, ?, ?> application) {
 return getSingletonMainInput(
 application.getInputs(), application.getTransform().getAdditionalInputs().keySet());
  }


 private static <T> PCollection<T> getSingletonMainInput(
 Map<TupleTag<?>, PValue> inputs, Set<TupleTag<?>> ignoredTags) {
 PCollection<T> mainInput = null;
 for (Map.Entry<TupleTag<?>, PValue> input : inputs.entrySet()) {
 if (!ignoredTags.contains(input.getKey())) {
 checkArgument(
 mainInput == null,
 "Got multiple inputs that are not additional inputs for a "
                + "singleton main input: %s and %s",
 mainInput,
 input.getValue());
 checkArgument(
 input.getValue() instanceof PCollection,
 "Unexpected input type %s",
 input.getValue().getClass());
 mainInput = (PCollection<T>) input.getValue();
      }
    }
 checkArgument(
 mainInput != null,
 "No main input found in inputs: Inputs %s, Side Input tags %s",
 inputs,
 ignoredTags);
 return mainInput;
  }


 public static <T> PCollection<T> getSingletonMainOutput(
 AppliedPTransform<?, PCollection<T>, ? extends PTransform<?, PCollection<T>>> transform) {
 return (PCollection<T>) Iterables.getOnlyElement(transform.getOutputs().values());
  }
}