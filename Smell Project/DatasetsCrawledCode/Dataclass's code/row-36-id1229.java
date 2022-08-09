 static class DynamicPackageEntry {


 // public:
 //
 // DynamicPackageEntry() =default;


 DynamicPackageEntry(String package_name, int package_id) {
 this.package_name = package_name;
 this.package_id = package_id;
    }


 String package_name;
 int package_id = 0;
  }