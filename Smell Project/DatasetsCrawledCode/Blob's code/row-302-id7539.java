public class CoapEncoder implements StatelessProtocolEncoder<CoapMessage, ByteBuffer> {


 private static final Logger LOG = LoggerFactory.getLogger(CoapEncoder.class);


 private static final int HEADER_SIZE = 4;


 /**
     * {@inheritDoc}
     */
 @Override
 public Void createEncoderState() {
 return null;
    }


 /**
     * {@inheritDoc}
     */
 @Override
 public ByteBuffer encode(CoapMessage message, Void context) {


 LOG.debug("encoding {}", message);


 // compute size of the needed buffer
 int size = HEADER_SIZE + message.getToken().length;


 int delta = 0;
 for (CoapOption opt : message.getOptions()) {
 // compute delta encoding size
 int code = opt.getType().getCode();
 delta = code - delta;
 if (delta < 13) {
 size += 1;
            } else if (delta < 256 + 13) {
 size += 2;
            } else if (delta < 65536 + 269) {
 size += 3;
            }


 // compute option length encoding size
 int optLength = opt.getData().length;
 if (optLength < 13) {
 size += 0;
            } else if (optLength < 256 + 13) {
 size += 1;
            } else if (optLength < 65536 + 269) {
 size += 2;
            }


 // compute option data encoding size
 size += optLength;
        }


 // if we have a payload, we place the marker and the payload
 if (message.getPayload() != null && message.getPayload().length > 0) {
 // payload marker
 size += 1;
 size += message.getPayload().length;
        }


 ByteBuffer buffer = ByteBuffer.allocate(size);


 buffer.order(ByteOrder.BIG_ENDIAN);


 // encode header
 buffer.put((byte) (((message.getVersion() & 0x03) << 6) | (message.getType().getCode() & 0x03) << 4 | (message
                .getToken().length & 0x0F)));
 buffer.put((byte) message.getCode());
 buffer.putShort((short) message.getId());
 buffer.put(message.getToken());


 // encode options
 int lastOptCode = 0;


 for (CoapOption opt : message.getOptions()) {
 int optionDelta = opt.getType().getCode() - lastOptCode;
 int deltaQuartet = getQuartet(optionDelta);
 int optionLength = opt.getData().length;


 int optionQuartet = getQuartet(optionLength);


 buffer.put((byte) ((deltaQuartet << 4) | optionQuartet));


 // write extended option delta field (0 - 2 bytes)
 if (deltaQuartet == 13) {
 buffer.put((byte) (optionDelta - 13));
            } else if (deltaQuartet == 14) {
 buffer.putShort((short) (optionDelta - 269));
            }


 if (optionQuartet == 13) {
 buffer.put((byte) (optionLength - 13));
            } else if (optionQuartet == 14) {
 buffer.putShort((short) (optionLength - 269));
            }
 buffer.put(opt.getData());


 lastOptCode = opt.getType().getCode();
        }


 if (message.getPayload() != null && message.getPayload().length > 0) {
 buffer.put((byte) 0xFF);
 buffer.put(message.getPayload());
        }


 buffer.flip();
 return buffer;
    }


 private int getQuartet(int value) {
 if (value <= 12) {
 return value;
        } else if (value <= 255 + 13) {
 return 13;
        } else {
 return 14;
        }
    }
}