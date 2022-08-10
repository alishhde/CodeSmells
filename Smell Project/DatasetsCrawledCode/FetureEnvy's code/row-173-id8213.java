 public ImmediatelyThrowsEvent makeImmediatelyThrowsEvent(final int processId,
 final int invokeId,
 final Throwable throwable) {
 if (null == immediatelyThrowsEvent) {
 immediatelyThrowsEvent = new ImmediatelyThrowsEvent(ILLEGAL_PROCESS_ID, ILLEGAL_INVOKE_ID, null);
        }
 unsafe.putInt(immediatelyThrowsEvent, processIdFieldInInvokeEventOffset, processId);
 unsafe.putInt(immediatelyThrowsEvent, invokeIdFieldInInvokeEventOffset, invokeId);
 unsafe.putObject(immediatelyThrowsEvent, throwableFieldInThrowsEventOffset, throwable);
 return immediatelyThrowsEvent;
    }