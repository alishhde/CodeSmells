public class ConstRateAdaptor extends AbstractAdaptor implements Runnable {


 private int SLEEP_VARIANCE = 200;
 private int MIN_SLEEP = 300;


 private long offset;
 private int bytesPerSec;


 Random timeCoin;
 long seed;
 
 private volatile boolean stopping = false;


 public String getCurrentStatus() {
 return type.trim() + " " + bytesPerSec + " " + seed;
  }


 public void start(long offset) throws AdaptorException {


 this.offset = offset;
 Configuration conf = control.getConfiguration();
 MIN_SLEEP = conf.getInt("constAdaptor.minSleep", MIN_SLEEP);
 SLEEP_VARIANCE = conf.getInt("constAdaptor.sleepVariance", SLEEP_VARIANCE);
 
 timeCoin = new Random(seed);
 long o =0;
 while(o < offset)
 o += (int) ((timeCoin.nextInt(SLEEP_VARIANCE) + MIN_SLEEP) *
          (long) bytesPerSec / 1000L) + 8;
 new Thread(this).start(); // this is a Thread.start
  }


 public String parseArgs(String bytesPerSecParam) {
 try {
 Matcher m = Pattern.compile("([0-9]+)(?:\\s+([0-9]+))?\\s*").matcher(bytesPerSecParam);
 if(!m.matches())
 return null;
 bytesPerSec = Integer.parseInt(m.group(1));
 String rate = m.group(2);
 if(rate != null)
 seed = Long.parseLong(m.group(2));
 else
 seed = System.currentTimeMillis();
    } catch (NumberFormatException e) {
 //("bad argument to const rate adaptor: ["  + bytesPerSecParam + "]");
 return null;
    }
 return bytesPerSecParam;
  }


 public void run() {
 try {
 while (!stopping) {
 int MSToSleep = timeCoin.nextInt(SLEEP_VARIANCE) + MIN_SLEEP; 
 int arraySize = (int) (MSToSleep * (long) bytesPerSec / 1000L) + 8;
 ChunkImpl evt = nextChunk(arraySize );


 dest.add(evt);


 Thread.sleep(MSToSleep);
      } // end while
    } catch (InterruptedException ie) {
    } // abort silently
  }


 public ChunkImpl nextChunk(int arraySize) {
 byte[] data = new byte[arraySize];
 Random dataPattern = new Random(offset ^ seed);
 long s = this.seed;
 offset += data.length;
 dataPattern.nextBytes(data);
 for(int i=0; i < 8; ++i)  {
 data[7-i] = (byte) (s & 0xFF);
 s >>= 8;
    }
 ChunkImpl evt = new ChunkImpl(type, "random ("+ this.seed+")", offset, data,
 this);
 return evt;
  }


 public String toString() {
 return "const rate " + type;
  }


 @Override
 public long shutdown(AdaptorShutdownPolicy shutdownPolicy) {
 stopping = true;
 return offset;
  }
 
 public static boolean checkChunk(Chunk chunk) {
 byte[] data = chunk.getData();
 byte[] correctData = new byte[data.length];
 
 long seed = 0;
 for(int i=0; i < 8; ++i) 
 seed = (seed << 8) | (0xFF & data[i] );


 seed ^= (chunk.getSeqID() - data.length);
 Random dataPattern = new Random(seed);
 dataPattern.nextBytes(correctData);
 for(int i=8; i < data.length ; ++i) 
 if(data [i] != correctData[i])
 return false;
 
 return true;
  }
 
 void test_init(String type) {
 this.type = type;
 seed = System.currentTimeMillis();
  }
}