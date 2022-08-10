 @Override
 public ExitCode runWithoutHelp(CommandRunnerParams params) throws Exception {
 ProjectFilesystem projectFilesystem = params.getCell().getFilesystem();
 try (ProjectBuildFileParser parser =
 new DefaultProjectBuildFileParserFactory(
 new DefaultTypeCoercerFactory(),
 params.getConsole(),
 new ParserPythonInterpreterProvider(
 params.getCell().getBuckConfig(), params.getExecutableFinder()),
 params.getKnownRuleTypesProvider(),
 params.getManifestServiceSupplier(),
 params.getFileHashCache())
            .createBuildFileParser(
 params.getBuckEventBus(), params.getCell(), params.getWatchman())) {
 /*
       * The super console does a bunch of rewriting over the top of the console such that
       * simultaneously writing to stdout and stderr in an interactive session is problematic.
       * (Overwritten characters, lines never showing up, etc). As such, writing to stdout directly
       * stops superconsole rendering (no errors appear). Because of all of this, we need to
       * just buffer the output and print it to stdout at the end fo the run. The downside
       * is that we have to buffer all of the output in memory, and it could potentially be large,
       * however, we'll just have to accept that tradeoff for now to get both error messages
       * from the parser, and the final output
       */


 try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
 PrintStream out = new PrintStream(new BufferedOutputStream(byteOut))) {
 for (String pathToBuildFile : getArguments()) {
 // Print a comment with the path to the build file.
 out.printf("# %s\n\n", pathToBuildFile);


 // Resolve the path specified by the user.
 Path path = Paths.get(pathToBuildFile);
 if (!path.isAbsolute()) {
 Path root = projectFilesystem.getRootPath();
 path = root.resolve(path);
          }


 // Parse the rules from the build file.
 ImmutableMap<String, Map<String, Object>> rawRules =
 parser.getBuildFileManifest(path).getTargets();


 // Format and print the rules from the raw data, filtered by type.
 ImmutableSet<String> types = getTypes();
 Predicate<String> includeType = type -> types.isEmpty() || types.contains(type);
 printRulesToStdout(out, rawRules, includeType);
        }


 // Make sure we tell the event listener to flush, otherwise there is a race condition where
 // the event listener might not have flushed, we dirty the stream, and then it will not
 // render the last frame (see {@link SuperConsoleEventListener})
 params.getBuckEventBus().post(new FlushConsoleEvent());
 out.close();
 params.getConsole().getStdOut().write(byteOut.toByteArray());
      }
    }


 return ExitCode.SUCCESS;
  }