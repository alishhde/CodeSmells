 private class NioServerListener implements Callable<ChannelFuture> {


 /**
     * Configure and start nio server
     */
 @Override
 public ChannelFuture call() throws Exception {
 SERVER.set(AsyncServer.this);
 // ServerBootstrap is a helper class that sets up a server
 ServerBootstrap b = new ServerBootstrap();
 b.group(bossGroup, workerGroup)
          .channel(EpollServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, backlogLength)
          .childOption(ChannelOption.MAX_MESSAGES_PER_READ, NIO_BUFFER_LIMIT)
          .childOption(ChannelOption.TCP_NODELAY, tcpNoDelay)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.SO_RCVBUF, 30 * 1024 * 1024)
          .childOption(ChannelOption.RCVBUF_ALLOCATOR,
 new FixedRecvByteBufAllocator(100 * 1024))


          .childHandler(new ChannelInitializer<SocketChannel>() {
 @Override
 public void initChannel(SocketChannel ch) throws Exception {
 ChannelPipeline p = ch.pipeline();
 // Register accumulation processing handler
 p.addLast(new NioFrameDecoder(100 * 1024 * 1024, 0, 4, 0, 0));
 // Register message processing handler
 p.addLast(new NioServerInboundHandler());
            }
          });


 // Bind and start to accept incoming connections.
 ChannelFuture f = b.bind(port).sync();
 LOG.info("AsyncServer startup");


 return f.channel().closeFuture();
    }
  }