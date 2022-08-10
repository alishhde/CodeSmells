 @Override
 @SuppressWarnings("unchecked")
 public void processElement(Object untypedElem) throws Exception {
 WindowedValue<T> elem = (WindowedValue<T>) untypedElem;


 Collection<W> windows =
 windowFn.assignWindows(
 windowFn.new AssignContext() {
 @Override
 public T element() {
 return elem.getValue();
                }


 @Override
 public Instant timestamp() {
 return elem.getTimestamp();
                }


 @Override
 public BoundedWindow window() {
 return Iterables.getOnlyElement(elem.getWindows());
                }
              });


 WindowedValue<T> res =
 WindowedValue.of(elem.getValue(), elem.getTimestamp(), windows, elem.getPane());
 receiver.process(res);
    }