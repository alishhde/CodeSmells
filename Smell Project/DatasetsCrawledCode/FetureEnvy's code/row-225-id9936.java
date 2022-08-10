 @Override
 public void transition(JobImpl job, JobEvent event) {
 job.addDiagnostic(((JobDiagnosticsUpdateEvent) event)
          .getDiagnosticUpdate());
    }