 public CurrentLiveVersion(ReloadableType reloadableType, String versionstamp, byte[] newbytedata) {
 if (GlobalConfiguration.logging && log.isLoggable(Level.FINER)) {
 log.entering("CurrentLiveVersion", "<init>", " new version of " + reloadableType.getName()
					+ " loaded, version stamp '"
					+ versionstamp + "'");
		}
 this.reloadableType = reloadableType;
 this.typeDescriptor = reloadableType.getTypeRegistry().getExtractor().extract(newbytedata, true);
 this.versionstamp = versionstamp;


 if (GlobalConfiguration.assertsMode) {
 if (!this.typeDescriptor.getName().equals(reloadableType.typedescriptor.getName())) {
 throw new IllegalStateException("New version has wrong name.  Expected "
						+ reloadableType.typedescriptor.getName()
						+ " but was " + typeDescriptor.getName());
			}
		}


 newbytedata = GlobalConfiguration.callsideRewritingOn ? MethodInvokerRewriter.rewrite(
 reloadableType.typeRegistry,
 newbytedata) : newbytedata;


 this.incrementalTypeDescriptor = new IncrementalTypeDescriptor(reloadableType.typedescriptor);
 this.incrementalTypeDescriptor.setLatestTypeDescriptor(this.typeDescriptor);


 // Executors for interfaces simply hold annotations
 this.executor = reloadableType.getTypeRegistry().executorBuilder.createFor(reloadableType, versionstamp,
 typeDescriptor,
 newbytedata);


 if (GlobalConfiguration.classesToDump != null
				&& GlobalConfiguration.classesToDump.contains(reloadableType.getSlashedName())) {
 Utils.dump(Utils.getExecutorName(reloadableType.getName(), versionstamp).replace('.', '/'), this.executor);
		}
 // DEFAULT METHODS - REMOVE THE IF
 if (!typeDescriptor.isInterface()) {
 this.dispatcherName = Utils.getDispatcherName(reloadableType.getName(), versionstamp);
 this.executorName = Utils.getExecutorName(reloadableType.getName(), versionstamp);
 this.dispatcher = DispatcherBuilder.createFor(reloadableType, incrementalTypeDescriptor, versionstamp);
		}
 reloadableType.typeRegistry.checkChildClassLoader(reloadableType);
 define();
	}