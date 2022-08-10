 private static void assertFinishedOnce(boolean[] finishedOut, SSLEngineResult result) {
 if (result.getHandshakeStatus() == HandshakeStatus.FINISHED) {
 assertFalse("should only return FINISHED once", finishedOut[0]);
 finishedOut[0] = true;
        }
    }