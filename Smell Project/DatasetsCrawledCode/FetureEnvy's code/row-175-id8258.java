 protected Server createJettyServer(JettyHttpHandlerAdapter servlet) {
 int port = (getPort() >= 0) ? getPort() : 0;
 InetSocketAddress address = new InetSocketAddress(getAddress(), port);
 Server server = new Server(getThreadPool());
 server.addConnector(createConnector(address, server));
 ServletHolder servletHolder = new ServletHolder(servlet);
 servletHolder.setAsyncSupported(true);
 ServletContextHandler contextHandler = new ServletContextHandler(server, "",
 false, false);
 contextHandler.addServlet(servletHolder, "/");
 server.setHandler(addHandlerWrappers(contextHandler));
 JettyReactiveWebServerFactory.logger
				.info("Server initialized with port: " + port);
 if (getSsl() != null && getSsl().isEnabled()) {
 customizeSsl(server, address);
		}
 for (JettyServerCustomizer customizer : getServerCustomizers()) {
 customizer.customize(server);
		}
 if (this.useForwardHeaders) {
 new ForwardHeadersCustomizer().customize(server);
		}
 return server;
	}