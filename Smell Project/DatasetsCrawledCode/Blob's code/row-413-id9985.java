 @BenchmarkMode(Mode.Throughput)
 @OutputTimeUnit(TimeUnit.SECONDS)
 @Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
 @Measurement(iterations = 10, time = 30, timeUnit = TimeUnit.SECONDS)
 @Fork(1)
 @State(Scope.Thread)
 public static class DeleteTasksBenchmark {
 private StateManager manager;
 private Storage storage;
 private Set<String> taskIds;


 @Param({"1000", "10000", "50000"})
 private int numTasksToDelete;


 @Setup(Level.Trial)
 public void setUpStorage() {
 Injector injector = getInjector();
 manager = injector.getInstance(StateManager.class);
 storage = injector.getInstance(Storage.class);
 storage.prepare();
    }


 // JMH warns heavily against using `Invocation` but this test seems to meet the requirements
 // of using it. Each benchmark will take more than one ms and it avoids awkward logic to
 // setup storage before the benchmark.
 @Setup(Level.Invocation)
 public void setUp() {
 storage.write(new Storage.MutateWork.NoResult.Quiet() {
 @Override
 public void execute(Storage.MutableStoreProvider storeProvider) throws RuntimeException {
 taskIds = bulkInsertTasks(numTasksToDelete, storeProvider.getUnsafeTaskStore());
        }
      });
    }


 @Benchmark
 public Set<String> run() {
 return storage.write((Storage.MutateWork.Quiet<Set<String>>) storeProvider -> {
 manager.deleteTasks(storeProvider, taskIds);
 return taskIds;
      });
    }
  }