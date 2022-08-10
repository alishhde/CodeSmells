 private static void writeFinalRule(Writer writer, boolean isDst, AnnualTimeZoneRule rule,
 int fromRawOffset, int fromDSTSavings, long startTime) throws IOException{
 DateTimeRule dtrule = toWallTimeRule(rule.getRule(), fromRawOffset, fromDSTSavings);


 // If the rule's mills in a day is out of range, adjust start time.
 // Olson tzdata supports 24:00 of a day, but VTIMEZONE does not.
 // See ticket#7008/#7518


 int timeInDay = dtrule.getRuleMillisInDay();
 if (timeInDay < 0) {
 startTime = startTime + (0 - timeInDay);
        } else if (timeInDay >= Grego.MILLIS_PER_DAY) {
 startTime = startTime - (timeInDay - (Grego.MILLIS_PER_DAY - 1));
        }


 int toOffset = rule.getRawOffset() + rule.getDSTSavings();
 switch (dtrule.getDateRuleType()) {
 case DateTimeRule.DOM:
 writeZonePropsByDOM(writer, isDst, rule.getName(), fromRawOffset + fromDSTSavings, toOffset,
 dtrule.getRuleMonth(), dtrule.getRuleDayOfMonth(), startTime, MAX_TIME);
 break;
 case DateTimeRule.DOW:
 writeZonePropsByDOW(writer, isDst, rule.getName(), fromRawOffset + fromDSTSavings, toOffset,
 dtrule.getRuleMonth(), dtrule.getRuleWeekInMonth(), dtrule.getRuleDayOfWeek(), startTime, MAX_TIME);
 break;
 case DateTimeRule.DOW_GEQ_DOM:
 writeZonePropsByDOW_GEQ_DOM(writer, isDst, rule.getName(), fromRawOffset + fromDSTSavings, toOffset,
 dtrule.getRuleMonth(), dtrule.getRuleDayOfMonth(), dtrule.getRuleDayOfWeek(), startTime, MAX_TIME);
 break;
 case DateTimeRule.DOW_LEQ_DOM:
 writeZonePropsByDOW_LEQ_DOM(writer, isDst, rule.getName(), fromRawOffset + fromDSTSavings, toOffset,
 dtrule.getRuleMonth(), dtrule.getRuleDayOfMonth(), dtrule.getRuleDayOfWeek(), startTime, MAX_TIME);
 break;
        }
    }