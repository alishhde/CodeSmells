public class JavaTimeSupplementary_es_AR extends OpenListResourceBundle {
 @Override
 protected final Object[][] getContents() {
 final String[] sharedAmPmMarkers = {
 "a.m.",
 "p.m.",
        };


 final String[] sharedDatePatterns = {
 "GGGG y MMMM d, EEEE",
 "GGGG y MMMM d",
 "GGGG y MMM d",
 "dd/MM/yy G",
        };


 final String[] sharedDayNarrows = {
 "d",
 "l",
 "m",
 "m",
 "j",
 "v",
 "s",
        };


 final String[] sharedTimePatterns = {
 "HH:mm:ss zzzz",
 "HH:mm:ss z",
 "HH:mm:ss",
 "HH:mm",
        };


 final String[] sharedJavaTimeDatePatterns = {
 "G y MMMM d, EEEE",
 "G y MMMM d",
 "G y MMM d",
 "dd/MM/yy GGGGG",
        };


 return new Object[][] {
            { "field.dayperiod",
 "a.m./p.m." },
            { "islamic.AmPmMarkers",
 sharedAmPmMarkers },
            { "islamic.DatePatterns",
 sharedDatePatterns },
            { "islamic.DayNarrows",
 sharedDayNarrows },
            { "islamic.TimePatterns",
 sharedTimePatterns },
            { "islamic.abbreviated.AmPmMarkers",
 sharedAmPmMarkers },
            { "islamic.narrow.AmPmMarkers",
 sharedAmPmMarkers },
            { "java.time.buddhist.DatePatterns",
 sharedJavaTimeDatePatterns },
            { "java.time.islamic.DatePatterns",
 sharedJavaTimeDatePatterns },
            { "java.time.roc.DatePatterns",
 sharedJavaTimeDatePatterns },
            { "roc.AmPmMarkers",
 sharedAmPmMarkers },
            { "roc.DatePatterns",
 sharedDatePatterns },
            { "roc.DayNarrows",
 sharedDayNarrows },
            { "roc.MonthAbbreviations",
 new String[] {
 "ene.",
 "feb.",
 "mar.",
 "abr.",
 "may.",
 "jun.",
 "jul.",
 "ago.",
 "sep.",
 "oct.",
 "nov.",
 "dic.",
 "",
                }
            },
            { "roc.MonthNarrows",
 new String[] {
 "e",
 "f",
 "m",
 "a",
 "m",
 "j",
 "j",
 "a",
 "s",
 "o",
 "n",
 "d",
 "",
                }
            },
            { "roc.TimePatterns",
 sharedTimePatterns },
            { "roc.abbreviated.AmPmMarkers",
 sharedAmPmMarkers },
            { "roc.narrow.AmPmMarkers",
 sharedAmPmMarkers },
        };
    }
}