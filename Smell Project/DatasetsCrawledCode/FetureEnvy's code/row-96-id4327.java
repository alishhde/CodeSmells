 private void write(NIOConnection c) {
 try {
 c.writeByQueue();
            } catch (Throwable e) {
 c.error(ErrorCode.ERR_WRITE_BY_QUEUE, e);
            }
        }