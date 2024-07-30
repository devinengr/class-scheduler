package onl.devin.geneticsai.implementation.util;

import java.time.DayOfWeek;

public enum WeekDay {

    NONE,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    ;

    public static WeekDay fromDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return MONDAY;
            case TUESDAY: return TUESDAY;
            case WEDNESDAY: return WEDNESDAY;
            case THURSDAY: return THURSDAY;
            case FRIDAY: return FRIDAY;
            default: return NONE;
        }
    }

}
