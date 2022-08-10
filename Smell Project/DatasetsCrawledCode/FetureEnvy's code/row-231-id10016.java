 private boolean hasCmpPersistenceUnit(final Persistence persistence) {
 for (final PersistenceUnit unit : persistence.getPersistenceUnit()) {
 if (unit.getName().startsWith("cmp")) {
 return true;
            }
        }
 return false;
    }