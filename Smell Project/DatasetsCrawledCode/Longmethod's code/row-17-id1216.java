 @Implementation(minSdk = LOLLIPOP)
 @HiddenApi
 protected static void nativeGetPointerCoords(
 long nativePtr, int pointerIndex, int historyPos, PointerCoords outPointerCoordsObj) {
 NativeInput.MotionEvent event = getNativeMotionEvent(nativePtr);
 int pointerCount = event.getPointerCount();
 validatePointerIndex(pointerIndex, pointerCount);
 validatePointerCoords(outPointerCoordsObj);


 NativeInput.PointerCoords rawPointerCoords;
 if (historyPos == HISTORY_CURRENT) {
 rawPointerCoords = event.getRawPointerCoords(pointerIndex);
    } else {
 int historySize = event.getHistorySize();
 validateHistoryPos(historyPos, historySize);
 rawPointerCoords = event.getHistoricalRawPointerCoords(pointerIndex, historyPos);
    }
 pointerCoordsFromNative(
 rawPointerCoords, event.getXOffset(), event.getYOffset(), outPointerCoordsObj);
  }