 private static void printAllExcludes(ModuleDescriptor md, PrintWriter out) {
 ExcludeRule[] excludes = md.getAllExcludeRules();
 if (excludes.length > 0) {
 for (ExcludeRule exclude : excludes) {
 out.print(String.format("\t\t<exclude org=\"%s\" module=\"%s\" artifact=\"%s\" type=\"%s\" ext=\"%s\"",
 XMLHelper.escape(exclude.getId().getModuleId().getOrganisation()),
 XMLHelper.escape(exclude.getId().getModuleId().getName()),
 XMLHelper.escape(exclude.getId().getName()),
 XMLHelper.escape(exclude.getId().getType()),
 XMLHelper.escape(exclude.getId().getExt())));
 String[] ruleConfs = exclude.getConfigurations();
 if (!Arrays.asList(ruleConfs).equals(Arrays.asList(md.getConfigurationsNames()))) {
 out.print(listToPrefixedString(ruleConfs, " conf=\""));
                }
 out.print(" matcher=\"" + XMLHelper.escape(exclude.getMatcher().getName()) + "\"");
 out.println("/>");
            }
        }
    }