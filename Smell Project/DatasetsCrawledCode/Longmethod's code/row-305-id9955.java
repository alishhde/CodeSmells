 private int addManualRecord(Airing recAir, UIClient uiClient)
  {
 // Check to make sure we have an encoder that can receive this station
 Set<EncoderState> tryUs = new HashSet<EncoderState>(encoderStateMap.values());
 Iterator<EncoderState> walker = tryUs.iterator();
 // We only need to worry about conflicts with other recordings that occur within the same set of stations. If
 // encoder A has no intersection with the stations on encoder B; then there's no reason to prompt about conflicts from
 // that tuner since it won't help resolve scheduling issues. So this set will be all the stations that either directly or
 // indirectly could resolve a conflict with the new recording.
 // Due to the indirect nature of this; we have to keep checking through the encoders until this set stops growing in size
 Set<Integer> unifiedStationSet = new HashSet<Integer>();
 boolean encoderExists = false;
 while (walker.hasNext())
    {
 EncoderState es = walker.next();
 synchronized (es.stationSet) {
 if (es.stationSet.contains(recAir.stationID))
        {
 encoderExists = true;
 unifiedStationSet.addAll(es.stationSet);
 walker.remove(); // to avoid redundant checking below
 break;
        }
      }
    }
 if (!encoderExists)
 return VideoFrame.WATCH_FAILED_NO_ENCODERS_HAVE_STATION;


 int lastSetSize;
 do
    {
 lastSetSize = unifiedStationSet.size();
 walker = tryUs.iterator();
 while (walker.hasNext())
      {
 EncoderState es = walker.next();
 synchronized (es.stationSet) {
 if (unifiedStationSet.removeAll(es.stationSet))
          {
 // There was an intersection, so use all of these stations, then ignore this one for later
 unifiedStationSet.addAll(es.stationSet);
 walker.remove();
          }
        }
      }


    } while (lastSetSize != unifiedStationSet.size() && !tryUs.isEmpty());


 long defaultStartPadding = Sage.getLong("default_mr_start_padding", 0);
 long defaultStopPadding = Sage.getLong("default_mr_stop_padding", 0);
 long requestedStart = recAir.getStartTime() - defaultStartPadding;
 long requestedStop = recAir.getEndTime() + defaultStopPadding;
 long requestedDuration = requestedStop - requestedStart;


 Airing schedAir = recAir;
 if (defaultStartPadding != 0 || defaultStopPadding != 0)
    {
 schedAir = new Airing(0);
 schedAir.time = requestedStart;
 schedAir.duration = requestedDuration;
 schedAir.stationID = recAir.stationID;
 schedAir.showID = recAir.showID;
    }
 Vector<Airing> parallelRecords = new Vector<Airing>();
 Vector<Airing> lastParallel = null;
 do
    {
 parallelRecords.clear();
 ManualRecord[] manualMustSee = wiz.getManualRecordsSortedByTime();
 Vector<ManualRecord> parallelRecurs = new Vector<ManualRecord>();
 for (int i = 0; i < manualMustSee.length; i++)
      {
 ManualRecord currRec = manualMustSee[i];
 if (currRec.getContentAiring() == recAir)
 return VideoFrame.WATCH_OK;
 if (currRec.getEndTime() <= Sage.time()) continue;
 if (currRec.doRecurrencesOverlap(requestedStart, requestedDuration, 0))
        {
 parallelRecords.addElement(manualMustSee[i].getSchedulingAiring());
 if (currRec.recur != 0)
 parallelRecurs.add(currRec);
 else
 parallelRecurs.add(null);
        }
      }


 if (parallelRecords.isEmpty()) break;


 parallelRecords.addElement(schedAir);
 parallelRecurs.add(null);
 if (sched.testMultiTunerSchedulingPermutation(parallelRecords))
 break;
 // Remove any recurrence duplicates from the parallel list that is presented to the user
 for (int i = 0; i < parallelRecurs.size(); i++)
      {
 ManualRecord currRecur = parallelRecurs.get(i);
 if (currRecur == null) continue;
 for (int j = 0; j < parallelRecords.size(); j++)
        {
 if (i == j || parallelRecurs.get(j) == null) continue;


 ManualRecord otherRecur = parallelRecurs.get(j);
 if (currRecur.stationID == otherRecur.stationID && currRecur.duration == otherRecur.duration &&
 currRecur.recur == otherRecur.recur && currRecur.isSameRecurrence(otherRecur.startTime))
          {
 parallelRecurs.remove(j);
 parallelRecords.remove(j);
 j--;
          }
        }
      }


 // Conflict exists, we need to kill a recording that's on an encoder that's capable
 // of recording this
 // Conflict resolution, ask about what you're going to kill
 parallelRecords.remove(schedAir);


 // Remove any items from the conflict options that would not end up in station set overlap either directly or indirectly
 for (int i = 0; i < parallelRecords.size(); i++)
 if (!unifiedStationSet.contains(parallelRecords.get(i).stationID))
 parallelRecords.remove(i--);


 // If we have the same conflicts as when we just checked, then bail. Most likely they
 // aren't processing the Hook correctly and we'll be in an infinite loop.
 if (lastParallel != null && parallelRecords.equals(lastParallel))
 return VideoFrame.WATCH_FAILED_USER_REJECTED_CONFLICT;
 Object hookRes = (uiClient == null) ? null : uiClient.processUIClientHook("RecordRequestScheduleConflict", new Object[] { recAir, parallelRecords });
 if (!(hookRes instanceof Boolean) || !((Boolean) hookRes))
 return VideoFrame.WATCH_FAILED_USER_REJECTED_CONFLICT;
 lastParallel = new Vector<Airing>(parallelRecords);
    } while (true);


 ManualRecord newMR;
 if (schedAir.getStartTime() < Sage.time())
    {
 int[] errorReturn = new int[1];
 EncoderState es = findBestEncoderForNow(schedAir, true, uiClient, errorReturn);
 if (es == null)
      {
 if (errorReturn[0] == 0)
 errorReturn[0] = VideoFrame.WATCH_FAILED_GENERAL_CANT_FIND_ENCODER;
 return errorReturn[0];
      }
 synchronized (this)
      {
 es = checkForFoundBestEncoderNowRecordSwitch(es, recAir);
 // Set the acquisition state to manual if it has already started recording
 MediaFile mf = wiz.getFileForAiring(recAir);
 if (mf != null)
 mf.setAcquisitionTech(MediaFile.ACQUISITION_MANUAL);
 newMR = wiz.addManualRecord(requestedStart, requestedDuration, 0, recAir.stationID,
 "", "", recAir.id, 0);
 es.forceWatch = newMR.getSchedulingAiring();
 es.forceProcessed = false;
 work();
      }
    }
 else
 newMR = wiz.addManualRecord(requestedStart, requestedDuration, 0, recAir.stationID,
 "", "", recAir.id, 0);
 PluginEventManager.postEvent(PluginEventManager.MANUAL_RECORD_ADDED,
 new Object[] { PluginEventManager.VAR_AIRING, newMR.getSchedulingAiring() });
 return VideoFrame.WATCH_OK;
  }