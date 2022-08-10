 public static ConcurrentCompositeConfiguration createLocalConfig() {
 MicroserviceConfigLoader loader = new MicroserviceConfigLoader();
 loader.loadAndSort();
 if (localConfig.size() > 0) {
 ConfigModel model = new ConfigModel();
 model.setConfig(localConfig);
 loader.getConfigModels().add(model);
    }


 LOGGER.info("create local config:");
 for (ConfigModel configModel : loader.getConfigModels()) {
 LOGGER.info(" {}.", configModel.getUrl());
    }


 ConcurrentCompositeConfiguration config = ConfigUtil.createLocalConfig(loader.getConfigModels());
 ConfigUtil.setMicroserviceConfigLoader(config, loader);
 return config;
  }