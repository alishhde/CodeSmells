 public void connected(SocketChannel channel) throws IOException, Exception {
 this.channel = channel;


 if( codec !=null ) {
 initializeCodec();
        }


 this.channel.configureBlocking(false);
 this.remoteAddress = channel.socket().getRemoteSocketAddress().toString();
 channel.socket().setSoLinger(true, 0);
 channel.socket().setTcpNoDelay(true);


 this.socketState = new CONNECTED();
    }