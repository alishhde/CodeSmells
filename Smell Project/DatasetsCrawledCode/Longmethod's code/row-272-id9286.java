 LogFileLoader(final String path, @Nullable Instant start, @Nullable Instant end) {
 super(path);
 this.grokCompiler = GrokCompiler.newInstance();
 this.grokCompiler.registerDefaultPatterns();
 this.grokCompiler.registerPatternFromClasspath("/patterns/log-patterns");
 this.grok = grokCompiler.compile(logFormat, true);
 this.start = start;
 this.end = end;
 this.parsingErrors = new StringListColumn(
 new ColumnDescription(parseErrorColumn, ContentsKind.String));
 this.lineNumber = new IntListColumn(
 new ColumnDescription(lineNumberColumn, ContentsKind.Integer));
 String originalPattern = this.grok.getOriginalGrokPattern();
 String timestampPattern = GrokExtra.extractGroupPattern(
 this.grokCompiler.getPatternDefinitions(),
 originalPattern, GenericLogs.timestampColumnName);
 if (timestampPattern == null) {
 HillviewLogger.instance.warn("Pattern does not contain column named 'Timestamp'",
 "{0}", originalPattern);
 this.dateTime = null;
            } else {
 this.dateTime = this.grokCompiler.compile(
 "%{" + timestampPattern + ":" + GenericLogs.timestampColumnName + "}", true);
            }
        }