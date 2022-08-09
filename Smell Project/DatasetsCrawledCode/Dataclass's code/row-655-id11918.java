@InterfaceAudience.Private
public class HFileBlockDefaultEncodingContext implements
 HFileBlockEncodingContext {
 private BlockType blockType;
 private final DataBlockEncoding encodingAlgo;


 private byte[] dummyHeader;


 // Compression state


 /** Compressor, which is also reused between consecutive blocks. */
 private Compressor compressor;
 /** Compression output stream */
 private CompressionOutputStream compressionStream;
 /** Underlying stream to write compressed bytes to */
 private ByteArrayOutputStream compressedByteStream;


 private HFileContext fileContext;
 private TagCompressionContext tagCompressionContext;


 // Encryption state


 /** Underlying stream to write encrypted bytes to */
 private ByteArrayOutputStream cryptoByteStream;
 /** Initialization vector */
 private byte[] iv;


 private EncodingState encoderState;


 /**
   * @param encoding encoding used
   * @param headerBytes dummy header bytes
   * @param fileContext HFile meta data
   */
 public HFileBlockDefaultEncodingContext(DataBlockEncoding encoding, byte[] headerBytes,
 HFileContext fileContext) {
 this.encodingAlgo = encoding;
 this.fileContext = fileContext;
 Compression.Algorithm compressionAlgorithm =
 fileContext.getCompression() == null ? NONE : fileContext.getCompression();
 if (compressionAlgorithm != NONE) {
 compressor = compressionAlgorithm.getCompressor();
 compressedByteStream = new ByteArrayOutputStream();
 try {
 compressionStream =
 compressionAlgorithm.createPlainCompressionStream(
 compressedByteStream, compressor);
      } catch (IOException e) {
 throw new RuntimeException(
 "Could not create compression stream for algorithm "
                + compressionAlgorithm, e);
      }
    }


 Encryption.Context cryptoContext = fileContext.getEncryptionContext();
 if (cryptoContext != Encryption.Context.NONE) {
 cryptoByteStream = new ByteArrayOutputStream();
 iv = new byte[cryptoContext.getCipher().getIvLength()];
 new SecureRandom().nextBytes(iv);
    }


 dummyHeader = Preconditions.checkNotNull(headerBytes,
 "Please pass HConstants.HFILEBLOCK_DUMMY_HEADER instead of null for param headerBytes");
  }


 /**
   * prepare to start a new encoding.
   * @throws IOException
   */
 public void prepareEncoding(DataOutputStream out) throws IOException {
 if (encodingAlgo != null && encodingAlgo != DataBlockEncoding.NONE) {
 encodingAlgo.writeIdInBytes(out);
    }
  }


 @Override
 public void postEncoding(BlockType blockType)
 throws IOException {
 this.blockType = blockType;
  }


 @Override
 public Bytes compressAndEncrypt(byte[] data, int offset, int length) throws IOException {
 return compressAfterEncoding(data, offset, length, dummyHeader);
  }


 private Bytes compressAfterEncoding(byte[] uncompressedBytesWithHeaderBuffer,
 int uncompressedBytesWithHeaderOffset, int uncompressedBytesWithHeaderLength, byte[] headerBytes)
 throws IOException {
 Encryption.Context cryptoContext = fileContext.getEncryptionContext();
 if (cryptoContext != Encryption.Context.NONE) {


 // Encrypted block format:
 // +--------------------------+
 // | byte iv length           |
 // +--------------------------+
 // | iv data ...              |
 // +--------------------------+
 // | encrypted block data ... |
 // +--------------------------+


 cryptoByteStream.reset();
 // Write the block header (plaintext)
 cryptoByteStream.write(headerBytes);


 InputStream in;
 int plaintextLength;
 // Run any compression before encryption
 if (fileContext.getCompression() != Compression.Algorithm.NONE) {
 compressedByteStream.reset();
 compressionStream.resetState();
 compressionStream.write(uncompressedBytesWithHeaderBuffer,
 headerBytes.length + uncompressedBytesWithHeaderOffset, uncompressedBytesWithHeaderLength - headerBytes.length);
 compressionStream.flush();
 compressionStream.finish();
 byte[] plaintext = compressedByteStream.toByteArray();
 plaintextLength = plaintext.length;
 in = new ByteArrayInputStream(plaintext);
      } else {
 plaintextLength = uncompressedBytesWithHeaderLength - headerBytes.length;
 in = new ByteArrayInputStream(uncompressedBytesWithHeaderBuffer,
 headerBytes.length + uncompressedBytesWithHeaderOffset, plaintextLength);
      }


 if (plaintextLength > 0) {


 // Set up the cipher
 Cipher cipher = cryptoContext.getCipher();
 Encryptor encryptor = cipher.getEncryptor();
 encryptor.setKey(cryptoContext.getKey());


 // Set up the IV
 int ivLength = iv.length;
 Preconditions.checkState(ivLength <= Byte.MAX_VALUE, "IV length out of range");
 cryptoByteStream.write(ivLength);
 if (ivLength > 0) {
 encryptor.setIv(iv);
 cryptoByteStream.write(iv);
        }


 // Encrypt the data
 Encryption.encrypt(cryptoByteStream, in, encryptor);


 // Increment the IV given the final block size
 Encryption.incrementIv(iv, 1 + (cryptoByteStream.size() / encryptor.getBlockSize()));
 return new Bytes(cryptoByteStream.getBuffer(), 0, cryptoByteStream.size());
      } else {


 cryptoByteStream.write(0);
 return new Bytes(cryptoByteStream.getBuffer(), 0, cryptoByteStream.size());
      }


    } else {


 if (this.fileContext.getCompression() != NONE) {
 compressedByteStream.reset();
 compressedByteStream.write(headerBytes);
 compressionStream.resetState();
 compressionStream.write(uncompressedBytesWithHeaderBuffer,
 headerBytes.length + uncompressedBytesWithHeaderOffset, uncompressedBytesWithHeaderLength
              - headerBytes.length);
 compressionStream.flush();
 compressionStream.finish();
 return new Bytes(compressedByteStream.getBuffer(), 0, compressedByteStream.size());
      } else {
 return null;
      }
    }
  }


 @Override
 public BlockType getBlockType() {
 return blockType;
  }


 /**
   * Releases the compressor this writer uses to compress blocks into the
   * compressor pool.
   */
 @Override
 public void close() {
 if (compressor != null) {
 this.fileContext.getCompression().returnCompressor(compressor);
 compressor = null;
    }
  }


 @Override
 public DataBlockEncoding getDataBlockEncoding() {
 return this.encodingAlgo;
  }


 @Override
 public HFileContext getHFileContext() {
 return this.fileContext;
  }


 public TagCompressionContext getTagCompressionContext() {
 return tagCompressionContext;
  }


 public void setTagCompressionContext(TagCompressionContext tagCompressionContext) {
 this.tagCompressionContext = tagCompressionContext;
  }


 @Override
 public EncodingState getEncodingState() {
 return this.encoderState;
  }


 @Override
 public void setEncodingState(EncodingState state) {
 this.encoderState = state;
  }
}