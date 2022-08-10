 private ConfigurationInfo(CompositeData cd) {
 this.settings = createMap(cd.get("settings"));
 this.name = (String) cd.get("name");
 this.label = (String) cd.get("label");
 this.description = (String) cd.get("description");
 this.provider = (String) cd.get("provider");
 this.contents = (String) cd.get("contents");
    }