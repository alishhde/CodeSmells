 static void registerConfigOptions(IConfigManager configManager) {
 AsterixProperties.registerConfigOptions(configManager);
 ControllerConfig.Option.DEFAULT_DIR
                .setDefaultValue(FileUtil.joinPath(System.getProperty(ConfigurationUtil.JAVA_IO_TMPDIR), "asterixdb"));
 NCConfig.Option.APP_CLASS.setDefaultValue(NCApplication.class.getName());
 CCConfig.Option.APP_CLASS.setDefaultValue(CCApplication.class.getName());
 try {
 InputStream propertyStream =
 ApplicationConfigurator.class.getClassLoader().getResourceAsStream("git.properties");
 if (propertyStream != null) {
 Properties gitProperties = new Properties();
 gitProperties.load(propertyStream);
 StringWriter sw = new StringWriter();
 gitProperties.store(sw, null);
 configManager.setVersionString(sw.toString());
            }
        } catch (IOException e) {
 throw new IllegalStateException(e);
        }


    }