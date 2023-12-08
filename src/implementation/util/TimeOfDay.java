package implementation.util;

import implementation.category.TimeSlot;

public enum TimeOfDay {

    NONE,
    MORNING, // starts (am), ends (am/pm)
    AFTERNOON, // starts before or at 4, ends in PM
    EVENING, // starts 4pm or later
    ;

    public static void setTimesOfDay(TimeSlot timeSlot) {
        int minutesStart = timeSlot.getMinutesStart();
        int minutesEnd = timeSlot.getMinutesEnd();
        int noon = 12 * 60;
        int fourPM = noon + 4 * 60;
        if (minutesStart >= fourPM) {
            timeSlot.addTimeOfDay(EVENING);
        }
        if ((minutesStart <= fourPM) && (minutesEnd >= noon)) {
            timeSlot.addTimeOfDay(AFTERNOON);
        }
        if (minutesStart < noon) {
            timeSlot.addTimeOfDay(MORNING);
        }
    }

}
