 public static TimeOfYear fromDateTime(DateTime dateTime) {
 DateTime nextYear = dateTime.plusYears(1);  // This turns February 29 into February 28.
 TimeOfYear instance = new TimeOfYear();
 instance.timeString = String.format(
 "%02d %02d %08d",
 nextYear.getMonthOfYear(),
 nextYear.getDayOfMonth(),
 nextYear.getMillisOfDay());
 return instance;
  }