@InterfaceStability.Evolving
public class S3FileMerger implements Operator, Operator.CheckpointNotificationListener
{
 private static final Logger LOG = LoggerFactory.getLogger(S3FileMerger.class);
 @NotNull
 private String bucketName;
 @NotNull
 private String accessKey;
 @NotNull
 private String secretAccessKey;
 private String endPoint;
 protected transient List<String> uploadedFiles = new ArrayList<>();
 private WindowDataManager windowDataManager = new FSWindowDataManager();
 @FieldSerializer.Bind(JavaSerializer.class)
 private Map<String, List<PartETag>> uploadParts = new HashMap<>();
 private Map<String, S3InitiateFileUploadOperator.UploadFileMetadata> fileMetadatas = new HashMap<>();
 protected transient long currentWindowId;
 protected transient AmazonS3 s3Client;


 /**
   * Input port to receive UploadBlockMetadata
   */
 public final transient DefaultInputPort<S3BlockUploadOperator.UploadBlockMetadata> uploadMetadataInput = new DefaultInputPort<S3BlockUploadOperator.UploadBlockMetadata>()
  {
 @Override
 public void process(S3BlockUploadOperator.UploadBlockMetadata tuple)
    {
 processUploadBlock(tuple);
    }
  };


 /**
   * Process to merge the uploaded block into a file.
   * @param tuple uploaded block meta data
   */
 protected void processUploadBlock(S3BlockUploadOperator.UploadBlockMetadata tuple)
  {
 List<PartETag> listOfUploads = uploadParts.get(tuple.getKeyName());
 if (listOfUploads == null) {
 listOfUploads = new ArrayList<>();
 uploadParts.put(tuple.getKeyName(), listOfUploads);
    }
 listOfUploads.add(tuple.getPartETag());
 if (fileMetadatas.get(tuple.getKeyName()) != null) {
 verifyAndEmitFileMerge(tuple.getKeyName());
    }
  }


 /**
   * Input port to receive UploadFileMetadata
   */
 public final transient DefaultInputPort<S3InitiateFileUploadOperator.UploadFileMetadata> filesMetadataInput = new DefaultInputPort<S3InitiateFileUploadOperator.UploadFileMetadata>()
  {
 @Override
 public void process(S3InitiateFileUploadOperator.UploadFileMetadata tuple)
    {
 processFileMetadata(tuple);
    }
  };


 /**
   * Process to merge the uploaded blocks for the given file metadata.
   * @param tuple file metadata
   */
 protected void processFileMetadata(S3InitiateFileUploadOperator.UploadFileMetadata tuple)
  {
 String keyName = tuple.getKeyName();
 fileMetadatas.put(keyName, tuple);
 if (uploadParts.get(keyName) != null) {
 verifyAndEmitFileMerge(keyName);
    }
  }


 @Override
 public void setup(Context.OperatorContext context)
  {
 windowDataManager.setup(context);
 s3Client = createClient();
  }


 /**
   * Create AmazonS3 client using AWS credentials
   * @return AmazonS3
   */
 protected AmazonS3 createClient()
  {
 AmazonS3 client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretAccessKey));
 if (endPoint != null) {
 client.setEndpoint(endPoint);
    }
 return client;
  }




 @Override
 public void beginWindow(long windowId)
  {
 currentWindowId = windowId;
  }


 @Override
 public void endWindow()
  {
 if (uploadedFiles.size() > 0) {
 for (String keyName: uploadedFiles) {
 uploadParts.remove(keyName);
 fileMetadatas.remove(keyName);
      }
 uploadedFiles.clear();
    }
 if (currentWindowId > windowDataManager.getLargestCompletedWindow()) {
 try {
 windowDataManager.save("Uploaded Files", currentWindowId);
      } catch (IOException e) {
 throw new RuntimeException("Unable to save recovery", e);
      }
    }
  }


 @Override
 public void teardown()
  {
 windowDataManager.teardown();
  }


 /**
   * Send the CompleteMultipartUploadRequest to S3 if all the blocks of a file are uploaded into S3.
   * @param keyName file to upload into S3
   */
 private void verifyAndEmitFileMerge(String keyName)
  {
 if (currentWindowId <= windowDataManager.getLargestCompletedWindow()) {
 return;
    }
 S3InitiateFileUploadOperator.UploadFileMetadata uploadFileMetadata = fileMetadatas.get(keyName);
 List<PartETag> partETags = uploadParts.get(keyName);
 if (partETags == null || uploadFileMetadata == null ||
 uploadFileMetadata.getFileMetadata().getNumberOfBlocks() != partETags.size()) {
 return;
    }


 if (partETags.size() <= 1) {
 uploadedFiles.add(keyName);
 LOG.debug("Uploaded file {} successfully", keyName);
 return;
    }


 CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName,
 keyName, uploadFileMetadata.getUploadId(), partETags);
 CompleteMultipartUploadResult result = s3Client.completeMultipartUpload(compRequest);
 if (result.getETag() != null) {
 uploadedFiles.add(keyName);
 LOG.debug("Uploaded file {} successfully", keyName);
    }
  }


 @Override
 public void beforeCheckpoint(long windowId)
  {
  }


 @Override
 public void checkpointed(long windowId)
  {
  }


 @Override
 public void committed(long windowId)
  {
 try {
 windowDataManager.committed(windowId);
    } catch (IOException e) {
 throw new RuntimeException(e);
    }
  }


 /**
   * Return the name of the bucket in which to upload the files
   * @return name of the bucket
   */
 public String getBucketName()
  {
 return bucketName;
  }


 /**
   * Sets the name of the bucket in which to upload the files.
   * @param bucketName name of the bucket
   */
 public void setBucketName(@NotNull String bucketName)
  {
 this.bucketName = Preconditions.checkNotNull(bucketName);
  }


 /**
   * Return the AWS access key
   * @return the access key
   */
 public String getAccessKey()
  {
 return accessKey;
  }


 /**
   * Sets the AWS access key
   * @param accessKey AWS access key
   */
 public void setAccessKey(@NotNull String accessKey)
  {
 this.accessKey = Preconditions.checkNotNull(accessKey);
  }


 /**
   * Returns the AWS secret access key
   * @return AWS secret access key
   */
 public String getSecretAccessKey()
  {
 return secretAccessKey;
  }


 /**
   * Sets the AWS secret access key
   * @param secretAccessKey AWS secret access key
   */
 public void setSecretAccessKey(@NotNull String secretAccessKey)
  {
 this.secretAccessKey = Preconditions.checkNotNull(secretAccessKey);
  }


 /**
   * Get the AWS S3 end point
   * @return the AWS S3 end point
   */
 public String getEndPoint()
  {
 return endPoint;
  }


 /**
   * Set the S3 end point
   * @param endPoint end point
   */
 public void setEndPoint(String endPoint)
  {
 this.endPoint = endPoint;
  }
}