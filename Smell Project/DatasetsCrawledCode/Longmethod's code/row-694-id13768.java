 private static void weaveDir(File dir, String consumerHeaderKey, String consumerHeaderValue, String bundleClassPath) throws Exception {
 Set<WeavingData> wd = ConsumerHeaderProcessor.processHeader(consumerHeaderKey, consumerHeaderValue);


 URLClassLoader cl = new URLClassLoader(new URL [] {dir.toURI().toURL()}, Main.class.getClassLoader());
 String dirName = dir.getAbsolutePath();


 DirTree dt = new DirTree(dir);
 for (File f : dt.getFiles()) {
 if (!f.getName().endsWith(".class"))
 continue;


 String className = f.getAbsolutePath().substring(dirName.length());
 if (className.startsWith(File.separator))
 className = className.substring(1);
 className = className.substring(0, className.length() - ".class".length());
 className = className.replace(File.separator, ".");


 InputStream is = new FileInputStream(f);
 byte[] b;
 try {
 ClassReader cr = new ClassReader(is);
 ClassWriter cw = new StaticToolClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES, cl);
 TCCLSetterVisitor cv = new TCCLSetterVisitor(cw, className, wd);
 cr.accept(cv, ClassReader.SKIP_FRAMES);
 if (cv.isWoven()) {
 b = cw.toByteArray();
                } else {
 // if not woven, store the original bytes
 b = Streams.suck(new FileInputStream(f));
                }
            } finally {
 is.close();
            }


 OutputStream os = new FileOutputStream(f);
 try {
 os.write(b);
            } finally {
 os.close();
            }
        }


 if (bundleClassPath != null) {
 for (String entry : bundleClassPath.split(",")) {
 File jarFile = new File(dir, entry.trim());
 if (jarFile.isFile()) {
 weaveBCPJar(jarFile, consumerHeaderKey, consumerHeaderValue);
                }
            }
        }
    }