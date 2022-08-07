public class TwitterPullRecordReader implements IRecordReader<char[]> {


 private Query query;
 private Twitter twitter;
 private int requestInterval = 5; // seconds
 private QueryResult result;
 private int nextTweetIndex = 0;
 private long lastTweetIdReceived = 0;
 private CharArrayRecord record;
 private boolean stopped = false;


 public TwitterPullRecordReader(Twitter twitter, String keywords, int requestInterval) {
 this.twitter = twitter;
 this.requestInterval = requestInterval;
 this.query = new Query(keywords);
 this.query.setCount(100);
 this.record = new CharArrayRecord();
    }


 @Override
 public void close() throws IOException {
 // do nothing
    }


 @Override
 public boolean hasNext() throws Exception {
 return !stopped;
    }


 @Override
 public IRawRecord<char[]> next() throws IOException, InterruptedException {
 if (result == null || nextTweetIndex >= result.getTweets().size()) {
 Thread.sleep(1000 * requestInterval);
 query.setSinceId(lastTweetIdReceived);
 try {
 result = twitter.search(query);
            } catch (TwitterException e) {
 throw HyracksDataException.create(e);
            }
 nextTweetIndex = 0;
        }
 if (result != null && !result.getTweets().isEmpty()) {
 List<Status> tw = result.getTweets();
 Status tweet = tw.get(nextTweetIndex++);
 if (lastTweetIdReceived < tweet.getId()) {
 lastTweetIdReceived = tweet.getId();
            }
 String jsonTweet = TwitterObjectFactory.getRawJSON(tweet); // transform tweet obj to json
 record.set(jsonTweet);
 return record;
        } else {
 return null;
        }
    }


 @Override
 public boolean stop() {
 stopped = true;
 return true;
    }


 @Override
 public void setFeedLogManager(FeedLogManager feedLogManager) {
 // do nothing
    }


 @Override
 public void setController(AbstractFeedDataFlowController controller) {
 // do nothing
    }


 @Override
 public boolean handleException(Throwable th) {
 return false;
    }
}