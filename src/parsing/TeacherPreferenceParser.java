package parsing;

import implementation.Professor;
import implementation.util.*;

public class TeacherPreferenceParser implements FileParser {

    public static void setPreferences(TeacherPreference preference,
                                      int boardPref, int timePref, int daysOfWeek,
                                      int minSections, int maxSections) {
        preference.setMinSections(minSections);
        preference.setMaxSections(maxSections);

        // board pref (0=none, 1=white, 2=chalk)
        if (boardPref == 1) {
            preference.setBoardType(BoardType.WHITE);
        } else if (boardPref == 2) {
            preference.setBoardType(BoardType.CHALK);
        }

        // time pref (0=none, 1=morning, 2=afternoon, 3=evening)
        if (timePref == 1) {
            preference.setTimeOfDay(TimeOfDay.MORNING);
        } else if (timePref == 2) {
            preference.setTimeOfDay(TimeOfDay.AFTERNOON);
        } else if (timePref == 3) {
            preference.setTimeOfDay(TimeOfDay.EVENING);
        }

        // days of week (0=none, 1=MWF, 2=TR) MWF same as MW, MF, etc
        if (daysOfWeek == 1) {
            preference.addDayOfWeek(WeekDay.MONDAY);
            preference.addDayOfWeek(WeekDay.WEDNESDAY);
            preference.addDayOfWeek(WeekDay.FRIDAY);
        } else if (daysOfWeek == 2) {
            preference.addDayOfWeek(WeekDay.TUESDAY);
            preference.addDayOfWeek(WeekDay.THURSDAY);
        }
    }

    @Override
    public void parseLine(String line, int lineNumber) {
        // ignore the headers
        if (lineNumber == 1) {
            return;
        }

        TeacherPreference preference = new TeacherPreference();
        String[] split = line.split(",");
        int teacherID = Integer.parseInt(split[0]);
        int minSections = Integer.parseInt(split[1]);
        int maxSections = Integer.parseInt(split[2]);
        int boardPref = Integer.parseInt(split[3]);
        int timePref = Integer.parseInt(split[4]);
        int daysOfWeek = Integer.parseInt(split[5]);
        int typePref = Integer.parseInt(split[6]);

        setPreferences(preference, boardPref, timePref,
                       daysOfWeek, minSections, maxSections);

        Professor.getProfessorByID(teacherID).setTeacherPreference(preference);

        // type pref (0=none, 1=pure, 2=applied)
        if (typePref == 1) {
            preference.setTypePref(TypePref.PURE);
        } else if (typePref == 2) {
            preference.setTypePref(TypePref.APPLIED);
        }
    }

}
