public class CrunchInputFormat<K, V> extends InputFormat<K, V> {


 @Override
 public List<InputSplit> getSplits(JobContext job) throws IOException, InterruptedException {
 List<InputSplit> splits = Lists.newArrayList();
 Configuration base = job.getConfiguration();
 Map<FormatBundle, Map<Integer, List<Path>>> formatNodeMap = CrunchInputs.getFormatNodeMap(job);


 // First, build a map of InputFormats to Paths
 for (Map.Entry<FormatBundle, Map<Integer, List<Path>>> entry : formatNodeMap.entrySet()) {
 FormatBundle inputBundle = entry.getKey();
 Configuration conf = new Configuration(base);
 inputBundle.configure(conf);
 Job jobCopy = new Job(conf);
 InputFormat<?, ?> format = (InputFormat<?, ?>) ReflectionUtils.newInstance(inputBundle.getFormatClass(),
 jobCopy.getConfiguration());
 if (format instanceof FileInputFormat && !conf.getBoolean(RuntimeParameters.DISABLE_COMBINE_FILE, true)) {
 format = new CrunchCombineFileInputFormat<Object, Object>(jobCopy);
      }
 for (Map.Entry<Integer, List<Path>> nodeEntry : entry.getValue().entrySet()) {
 Integer nodeIndex = nodeEntry.getKey();
 List<Path> paths = nodeEntry.getValue();
 FileInputFormat.setInputPaths(jobCopy, paths.toArray(new Path[paths.size()]));


 // Get splits for each input path and tag with InputFormat
 // and Mapper types by wrapping in a TaggedInputSplit.
 List<InputSplit> pathSplits = format.getSplits(jobCopy);
 for (InputSplit pathSplit : pathSplits) {
 splits.add(new CrunchInputSplit(pathSplit, inputBundle, nodeIndex, jobCopy.getConfiguration()));
        }
      }
    }
 return splits;
  }


 @Override
 public RecordReader<K, V> createRecordReader(InputSplit inputSplit, TaskAttemptContext context) throws IOException,
 InterruptedException {
 return new CrunchRecordReader<K, V>(inputSplit, context);
  }
}