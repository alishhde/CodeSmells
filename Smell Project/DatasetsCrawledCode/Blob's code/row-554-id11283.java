@SuppressWarnings("all")
public abstract class AbstractEntitiesRuntimeModule extends DefaultXbaseRuntimeModule {


 protected Properties properties = null;


 @Override
 public void configure(Binder binder) {
 properties = tryBindProperties(binder, "org/eclipse/xtext/idea/example/entities/Entities.properties");
 super.configure(binder);
	}
 
 public void configureLanguageName(Binder binder) {
 binder.bind(String.class).annotatedWith(Names.named(Constants.LANGUAGE_NAME)).toInstance("org.eclipse.xtext.idea.example.entities.Entities");
	}
 
 public void configureFileExtensions(Binder binder) {
 if (properties == null || properties.getProperty(Constants.FILE_EXTENSIONS) == null)
 binder.bind(String.class).annotatedWith(Names.named(Constants.FILE_EXTENSIONS)).toInstance("entities");
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessFragment2
 public ClassLoader bindClassLoaderToInstance() {
 return getClass().getClassLoader();
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.grammarAccess.GrammarAccessFragment2
 public Class<? extends IGrammarAccess> bindIGrammarAccess() {
 return EntitiesGrammarAccess.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.serializer.SerializerFragment2
 public Class<? extends ISemanticSequencer> bindISemanticSequencer() {
 return EntitiesSemanticSequencer.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.serializer.SerializerFragment2
 public Class<? extends ISyntacticSequencer> bindISyntacticSequencer() {
 return EntitiesSyntacticSequencer.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.serializer.SerializerFragment2
 public Class<? extends ISerializer> bindISerializer() {
 return Serializer.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
 public Class<? extends IParser> bindIParser() {
 return EntitiesParser.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
 public Class<? extends ITokenToStringConverter> bindITokenToStringConverter() {
 return AntlrTokenToStringConverter.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
 public Class<? extends IAntlrTokenFileProvider> bindIAntlrTokenFileProvider() {
 return EntitiesAntlrTokenFileProvider.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
 public Class<? extends Lexer> bindLexer() {
 return InternalEntitiesLexer.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
 public Class<? extends ITokenDefProvider> bindITokenDefProvider() {
 return AntlrTokenDefProvider.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
 public Provider<InternalEntitiesLexer> provideInternalEntitiesLexer() {
 return LexerProvider.create(InternalEntitiesLexer.class);
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
 public void configureRuntimeLexer(Binder binder) {
 binder.bind(Lexer.class)
			.annotatedWith(Names.named(LexerBindings.RUNTIME))
			.to(InternalEntitiesLexer.class);
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.validation.ValidatorFragment2
 @SingletonBinding(eager=true)
 public Class<? extends EntitiesValidator> bindEntitiesValidator() {
 return EntitiesValidator.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.scoping.ImportNamespacesScopingFragment2
 public Class<? extends IBatchScopeProvider> bindIBatchScopeProvider() {
 return EntitiesScopeProvider.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.scoping.ImportNamespacesScopingFragment2
 public void configureIScopeProviderDelegate(Binder binder) {
 binder.bind(IScopeProvider.class).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(XImportSectionNamespaceScopeProvider.class);
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.scoping.ImportNamespacesScopingFragment2
 public void configureIgnoreCaseLinking(Binder binder) {
 binder.bindConstant().annotatedWith(IgnoreCaseLinking.class).to(false);
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
 public Class<? extends IContainer.Manager> bindIContainer$Manager() {
 return StateBasedContainerManager.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
 public Class<? extends IAllContainersState.Provider> bindIAllContainersState$Provider() {
 return ResourceSetBasedAllContainersStateProvider.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
 public void configureIResourceDescriptions(Binder binder) {
 binder.bind(IResourceDescriptions.class).to(ResourceSetBasedResourceDescriptions.class);
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.builder.BuilderIntegrationFragment2
 public void configureIResourceDescriptionsPersisted(Binder binder) {
 binder.bind(IResourceDescriptions.class).annotatedWith(Names.named(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS)).to(ResourceSetBasedResourceDescriptions.class);
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.formatting.Formatter2Fragment2
 public Class<? extends IFormatter2> bindIFormatter2() {
 return EntitiesFormatter.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.formatting.Formatter2Fragment2
 public void configureFormatterPreferences(Binder binder) {
 binder.bind(IPreferenceValuesProvider.class).annotatedWith(FormatterPreferences.class).to(FormatterPreferenceValuesProvider.class);
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
 return XbaseQualifiedNameProvider.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
 return JvmLocationInFileProvider.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
 return TypesAwareDefaultGlobalScopeProvider.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends FeatureNameValidator> bindFeatureNameValidator() {
 return LogicalContainerAwareFeatureNameValidator.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends DefaultBatchTypeResolver> bindDefaultBatchTypeResolver() {
 return LogicalContainerAwareBatchTypeResolver.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends DefaultReentrantTypeResolver> bindDefaultReentrantTypeResolver() {
 return LogicalContainerAwareReentrantTypeResolver.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends IResourceValidator> bindIResourceValidator() {
 return DerivedStateAwareResourceValidator.class;
	}
 
 // contributed by org.eclipse.xtext.xtext.generator.xbase.XbaseGeneratorFragment2
 public Class<? extends IJvmModelInferrer> bindIJvmModelInferrer() {
 return EntitiesJvmModelInferrer.class;
	}
 
}