public class ICEDocCleaner {
 
 public static void main(String[] args) throws IOException {


 // Get a handle on the file
 System.out.println("Enter full filepath: ");
 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 String path = br.readLine();
 if (path.contains("exit")) {
 return;
    	}
 File javaFile = new File(path);
 
 if (javaFile != null && javaFile.exists() && javaFile.isFile()) {
 
 System.out.println("File found, beginning cleaning...");
 FileInputStream inputStream = null;
 Scanner scanner = null;
 StringBuilder sb = null;
 
 try {
 
 // Set up a scanner (should be more efficient than Files.nio for very big files)
 inputStream = new FileInputStream(javaFile);
 scanner = new Scanner(inputStream, "UTF-8");
 String line = "";
 sb = new StringBuilder();
 
 while (scanner.hasNextLine() && (line = scanner.nextLine()) != null) {
 
 if (line.matches("\\s*\\*\\s*<!-- (begin|end)-UML-doc -->\\s*")
    		    			|| line.matches("\\s*\\*\\s*<!-- (begin|end)-UML-doc -->\\s*<!-- (begin|end)-UML-doc -->")
    		    			|| line.matches("\\s*// (begin|end)-user-code\\s*")
    		    			|| line.contains("@generated")
    		    			|| line.contains("UML to Java")) {


 // Skip over the line
 continue;
 
    		    	} else {


 // Append the line to the StringBuilder
 sb.append(line + "\n");
    		    	}
    		    }
 
 System.out.println("Done cleaning.");
 
 // Note that Scanner suppresses exceptions, throw manually
 if (scanner.ioException() != null) {
 throw scanner.ioException();
    		    }
 
    		} finally {
 
 // Close this MF dooown
 if (inputStream != null) {
 inputStream.close();
    		    }
 if (scanner != null) {
 scanner.close();
    		    }
    		}
 
 // Create a backup file in case something goes wrong
 int fileExtIndex = javaFile.getPath().lastIndexOf(".");
 String bakFilePath = javaFile.getPath().substring(0, fileExtIndex) + "_bak" + javaFile.getPath().substring(fileExtIndex);
 
 File bakFile = new File(bakFilePath);
 if (bakFile.exists()) {
 bakFile.delete();
    		}
 Files.copy(javaFile.toPath(), bakFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
 System.out.println("Creating back-up file... (will delete on successful exit)\n" + bakFile.getPath());
 
 // Overwrite the original file
 BufferedWriter writer = new BufferedWriter(new FileWriter(javaFile));
 writer.write(sb.toString());
 System.out.println("Overwriting original file...");
 if (writer != null) {
 writer.close();
        	}
 
 System.out.println("Process complete!");
 bakFile.deleteOnExit();
 
    	} else {
 System.out.println("Could not find file.");
 main(args);
    	}
 
 // k bai.
 return;
    }


}