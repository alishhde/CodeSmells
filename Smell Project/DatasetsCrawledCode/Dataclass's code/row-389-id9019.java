 private static class MigrationLink {
 int source;
 int target;
 Migration[] migrations;
 public MigrationLink(int source, int target, Migration[] migrations) {
 this.source = source;
 this.target = target;
 this.migrations = migrations;
        }
    }