 private ArgumentProcessor getProcessor(
 Class< ? extends ArgumentProcessor> processorClass) {
 ArgumentProcessor processor;
 try {
 processor = processorClass.getConstructor().newInstance();
        } catch (Exception e) {
 throw new BuildException("The argument processor class"
                    + processorClass.getName()
                    + " could not be instantiated with a default constructor",
 e);
        }
 return processor;
    }