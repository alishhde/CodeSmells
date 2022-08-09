public class MultiDexConfig {


 private String name;


 public MultiDexConfig(String name) {
 this.name = name;
    }


 @Config(title = "Whether to enable fast", message = "Enable atlas , true/false", order = 0, group = "atlas")
 private boolean fastMultiDex = false;


 @Config(title = "The extra first dex class list", message = "The custom needs to be placed in the entry class in the first dex", order = 3, group = "atlas")
 private Set<String> firstDexClasses = Sets.newHashSet();
 /**
     * dex The number of subcontracting, 0 No restrictions, no two merges
     */
 @Config(title = "dexThe number of", message = "0unlimited", order = 1, group = "atlas")
 private int dexCount;


 public int getMainDexListCount() {
 return mainDexListCount;
    }


 public void setMainDexListCount(int mainDexListCount) {
 this.mainDexListCount = mainDexListCount;
    }


 private int mainDexListCount;


 @Config(title = "dexSeparated rules", message = "a,b;c,d", order = 2, group = "atlas")
 private String dexSplitRules;


 @Config(title = "Does not enter the list of the first dex's blacklist", message = "a", order = 2, group = "atlas")
 private Set<String> mainDexBlackList = Sets.newHashSet();


 public String getName() {
 return name;
    }


 public void setName(String name) {
 this.name = name;
    }


 public boolean isFastMultiDex() {
 return fastMultiDex;
    }


 public void setFastMultiDex(boolean fastMultiDex) {
 this.fastMultiDex = fastMultiDex;
    }


 public Set<String> getMainDexBlackList() {
 return mainDexBlackList;
    }


 public void setMainDexBlackList(Set<String> mainDexBlackList) {
 this.mainDexBlackList = mainDexBlackList;
    }


 public Set<String> getFirstDexClasses() {
 return firstDexClasses;
    }


 public void setFirstDexClasses(Set<String> firstDexClasses) {
 this.firstDexClasses = firstDexClasses;
    }


 public int getDexCount() {
 return dexCount;
    }


 public void setDexCount(int dexCount) {
 this.dexCount = dexCount;
    }


 public String getDexSplitRules() {
 return dexSplitRules;
    }


 public void setDexSplitRules(String dexSplitRules) {
 this.dexSplitRules = dexSplitRules;
    }
}