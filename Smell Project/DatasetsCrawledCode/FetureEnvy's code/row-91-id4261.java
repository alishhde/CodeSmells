 @SuppressWarnings("try")
 private void doRun(Map<Method, CEntryPointData> entryPoints, Method mainEntryPoint,
 JavaMainSupport javaMainSupport, String imageName, AbstractBootImage.NativeImageKind k,
 SubstitutionProcessor harnessSubstitutions,
 ForkJoinPool compilationExecutor, ForkJoinPool analysisExecutor) {
 List<HostedMethod> hostedEntryPoints = new ArrayList<>();


 OptionValues options = HostedOptionValues.singleton();
 SnippetReflectionProvider originalSnippetReflection = GraalAccess.getOriginalSnippetReflection();
 try (DebugContext debug = DebugContext.create(options, new GraalDebugHandlersFactory(originalSnippetReflection))) {
 setupNativeImage(imageName, options, entryPoints, javaMainSupport, harnessSubstitutions, analysisExecutor, originalSnippetReflection, debug);


 boolean returnAfterAnalysis = runPointsToAnalysis(imageName, options, debug);
 if (returnAfterAnalysis) {
 return;
            }


 NativeImageHeap heap;
 HostedMethod mainEntryPointHostedStub;
 HostedMetaAccess hMetaAccess;
 SharedRuntimeConfigurationBuilder runtime;
 try (StopTimer t = new Timer(imageName, "universe").start()) {
 hUniverse = new HostedUniverse(bigbang);
 hMetaAccess = new HostedMetaAccess(hUniverse, bigbang.getMetaAccess());


 new UniverseBuilder(aUniverse, bigbang.getMetaAccess(), hUniverse, hMetaAccess, HostedConfiguration.instance().createStaticAnalysisResultsBuilder(bigbang, hUniverse),
 bigbang.getUnsupportedFeatures()).build(debug);


 runtime = new HostedRuntimeConfigurationBuilder(options, bigbang.getHostVM(), hUniverse, hMetaAccess, bigbang.getProviders()).build();
 registerGraphBuilderPlugins(featureHandler, runtime.getRuntimeConfig(), (HostedProviders) runtime.getRuntimeConfig().getProviders(), bigbang.getMetaAccess(), aUniverse,
 hMetaAccess, hUniverse,
 nativeLibraries, loader, false, true, bigbang.getAnnotationSubstitutionProcessor(), new SubstrateClassInitializationPlugin((SVMHost) aUniverse.hostVM()),
 bigbang.getHostVM().getClassInitializationSupport());


 if (NativeImageOptions.PrintUniverse.getValue()) {
 printTypes();
                }


 /* Find the entry point methods in the hosted world. */
 for (AnalysisMethod m : aUniverse.getMethods()) {
 if (m.isEntryPoint()) {
 HostedMethod found = hUniverse.lookup(m);
 assert found != null;
 hostedEntryPoints.add(found);
                    }
                }
 /* Find main entry point */
 if (mainEntryPoint != null) {
 AnalysisMethod analysisStub = CEntryPointCallStubSupport.singleton().getStubForMethod(mainEntryPoint);
 mainEntryPointHostedStub = (HostedMethod) hMetaAccess.getUniverse().lookup(analysisStub);
 assert hostedEntryPoints.contains(mainEntryPointHostedStub);
                } else {
 mainEntryPointHostedStub = null;
                }
 if (hostedEntryPoints.size() == 0) {
 throw UserError.abort("Warning: no entry points found, i.e., no method annotated with @" + CEntryPoint.class.getSimpleName());
                }


 heap = new NativeImageHeap(aUniverse, hUniverse, hMetaAccess);


 BeforeCompilationAccessImpl config = new BeforeCompilationAccessImpl(featureHandler, loader, aUniverse, hUniverse, hMetaAccess, heap, debug);
 featureHandler.forEachFeature(feature -> feature.beforeCompilation(config));


 bigbang.getUnsupportedFeatures().report(bigbang);
            } catch (UnsupportedFeatureException ufe) {
 throw UserError.abort(ufe.getMessage());
            }


 recordMethodsWithStackValues();
 recordRestrictHeapAccessCallees(aUniverse.getMethods());


 /*
             * After this point, all TypeFlow (and therefore also TypeState) objects are unreachable
             * and can be garbage collected. This is important to keep the overall memory footprint
             * low. However, this also means we no longer have complete call chain information. Only
             * the summarized information stored in the StaticAnalysisResult objects is available
             * after this point.
             */
 bigbang.cleanupAfterAnalysis();


 NativeImageCodeCache codeCache;
 CompileQueue compileQueue;
 try (StopTimer t = new Timer(imageName, "compile").start()) {
 compileQueue = HostedConfiguration.instance().createCompileQueue(debug, featureHandler, hUniverse, runtime, DeoptTester.enabled(), bigbang.getProviders().getSnippetReflection(),
 compilationExecutor);
 compileQueue.finish(debug);


 /* release memory taken by graphs for the image writing */
 hUniverse.getMethods().forEach(HostedMethod::clear);


 codeCache = NativeImageCodeCacheFactory.get().newCodeCache(compileQueue, heap);
 codeCache.layoutConstants();
 codeCache.layoutMethods(debug, imageName);


 AfterCompilationAccessImpl config = new AfterCompilationAccessImpl(featureHandler, loader, aUniverse, hUniverse, hMetaAccess, heap, debug);
 featureHandler.forEachFeature(feature -> feature.afterCompilation(config));
            }


 try (Indent indent = debug.logAndIndent("create native image")) {
 try (DebugContext.Scope buildScope = debug.scope("CreateBootImage")) {
 try (StopTimer t = new Timer(imageName, "image").start()) {


 // Start building the model of the native image heap.
 heap.addInitialObjects();
 // Then build the model of the code cache, which can
 // add objects to the native image heap.
 codeCache.addConstantsToHeap();
 // Finish building the model of the native image heap.
 heap.addTrailingObjects();


 AfterHeapLayoutAccessImpl config = new AfterHeapLayoutAccessImpl(featureHandler, loader, hMetaAccess, debug);
 featureHandler.forEachFeature(feature -> feature.afterHeapLayout(config));


 this.image = AbstractBootImage.create(k, hUniverse, hMetaAccess, nativeLibraries, heap, codeCache, hostedEntryPoints, mainEntryPointHostedStub, loader.getClassLoader());
 image.build(debug);
 if (NativeImageOptions.PrintUniverse.getValue()) {
 /*
                             * This debug output must be printed _after_ and not _during_ image
                             * building, because it adds some PrintStream objects to static fields,
                             * which disrupts the heap.
                             */
 codeCache.printCompilationResults();
                        }
                    }
                }
            }


 BeforeImageWriteAccessImpl beforeConfig = new BeforeImageWriteAccessImpl(featureHandler, loader, imageName, image,
 runtime.getRuntimeConfig(), aUniverse, hUniverse, optionProvider, hMetaAccess, debug);
 featureHandler.forEachFeature(feature -> feature.beforeImageWrite(beforeConfig));


 try (StopTimer t = new Timer(imageName, "write").start()) {
 /*
                 * This will write the debug info too -- i.e. we may be writing more than one file,
                 * if the debug info is in a separate file. We need to push writing the file to the
                 * image implementation, because whether the debug info and image share a file or
                 * not is an implementation detail of the image.
                 */
 Path tmpDir = tempDirectory();
 Path imagePath = image.write(debug, generatedFiles(HostedOptionValues.singleton()), tmpDir, imageName, beforeConfig).getOutputFile();


 AfterImageWriteAccessImpl afterConfig = new AfterImageWriteAccessImpl(featureHandler, loader, hUniverse, imagePath, tmpDir, image.getBootImageKind(), debug);
 featureHandler.forEachFeature(feature -> feature.afterImageWrite(afterConfig));
            }
        }
    }