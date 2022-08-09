 private void processSelectedKeys() {
 for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) {
 SelectionKey key = i.next();
 i.remove();
 final SelectableChannel sc = key.channel();
 // do not attempt to read/write until handle is set (e.g. after handshake is completed)
 if (key.isReadable() && key.attachment() != null) {
 read(key);
                } else if (key.isWritable() && key.attachment() != null) {
 write(key);
                } else if (key.isAcceptable()) {
 assert sc == serverSocketChannel;
 accept();
                } else if (key.isConnectable()) {
 finishConnect(key);
                }
            }
        }