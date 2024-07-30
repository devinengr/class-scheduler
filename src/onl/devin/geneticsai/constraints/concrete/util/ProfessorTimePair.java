package onl.devin.geneticsai.constraints.concrete.util;

import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class ProfessorTimePair {

    private static List<ProfessorTimePair> professorTimePairList = new ArrayList<>();
    private Professor professor;
    private TimeSlot timeSlot;

    public ProfessorTimePair(Professor professor, TimeSlot timeSlot) {
        this.professor = professor;
        this.timeSlot = timeSlot;
        professorTimePairList.add(this);
    }

    public static void reset() {
        professorTimePairList.clear();
    }

    public static boolean timeOfDayClashesOnTheSameDay(Professor professor, TimeSlot timeSlot) {
        for (ProfessorTimePair ptp : professorTimePairList) {
            if (ptp.professor.equals(professor)) {
                if (ptp.timeSlot.collidesByDay(timeSlot)) {
                    if (ptp.timeSlot.collidesByTimeOfDay(timeSlot)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
