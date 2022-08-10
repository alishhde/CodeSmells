 @Override
 public void unitKept(IInstallableUnit unit) {
 super.unitKept(unit);
 logger.debug("  Keeping unit " + unit.getId() + "/" + unit.getVersion());
        }