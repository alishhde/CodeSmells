 public void run()
    {
 try
      {
 IThreadContext threadContext = ThreadContextFactory.make();
 while (true)
        {
 try
          {
 if (Thread.currentThread().isInterrupted())
 throw new ManifoldCFException("Interrupted",ManifoldCFException.INTERRUPTED);


 checkAgents(threadContext);
 ManifoldCF.sleep(5000L);
          }
 catch (InterruptedException e)
          {
 break;
          }
 catch (ManifoldCFException e)
          {
 if (e.getErrorCode() == ManifoldCFException.INTERRUPTED)
 break;
 if (e.getErrorCode() == ManifoldCFException.SETUP_ERROR)
            {
 System.err.println("Misconfigured ManifoldCF agents - shutting down");
 Logging.agents.fatal("AgentThread configuration exception tossed: "+e.getMessage(),e);
 System.exit(-200);
            }
 Logging.agents.error("Exception tossed: "+e.getMessage(),e);
          }
 catch (OutOfMemoryError e)
          {
 System.err.println("Agents process ran out of memory - shutting down");
 e.printStackTrace(System.err);
 System.exit(-200);
          }
 catch (Throwable e)
          {
 Logging.agents.fatal("Error tossed: "+e.getMessage(),e);
          }
        }
      }
 catch (Throwable e)
      {
 // Severe error on initialization
 System.err.println("Agents process could not start - shutting down");
 Logging.agents.fatal("AgentThread initialization error tossed: "+e.getMessage(),e);
 System.exit(-300);
      }
    }