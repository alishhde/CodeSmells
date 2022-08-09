public class ProcessorUtils
{
 public static final String AVAILABLE_PROCESSORS = "JETTY_AVAILABLE_PROCESSORS";
 private static int __availableProcessors = init();


 static int init()
    {
 String processors = System.getProperty(AVAILABLE_PROCESSORS, System.getenv(AVAILABLE_PROCESSORS));
 if (processors != null)
        {
 try
            {
 return Integer.parseInt( processors );
            }
 catch (NumberFormatException ignored)
            {
            }
        }
 return Runtime.getRuntime().availableProcessors();
    }


 /**
     * Returns the number of available processors, from System Property "JETTY_AVAILABLE_PROCESSORS",
     * or if not set then from environment variable "JETTY_AVAILABLE_PROCESSORS" or if not set then
     * from {@link Runtime#availableProcessors()}.
     *
     * @return the number of processors
     */
 public static int availableProcessors()
    {
 return __availableProcessors;
    }


 public static void setAvailableProcessors(int processors)
    {
 if (processors < 1)
 throw new IllegalArgumentException("Invalid number of processors: " + processors);
 __availableProcessors = processors;
    }
}