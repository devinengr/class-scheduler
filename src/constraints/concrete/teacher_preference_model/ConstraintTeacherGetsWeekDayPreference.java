package constraints.concrete.teacher_preference_model;

import constraints.Constraint;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.Professor;
import implementation.category.TimeSlot;
import implementation.util.TimeOfDay;
import implementation.util.WeekDay;

import java.time.DayOfWeek;
import java.util.List;

public class ConstraintTeacherGetsWeekDayPreference implements Constraint {

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            TimeSlot timeSlot = hyp.getCategory(TimeSlot.class);
            List<WeekDay> weekDayPref = prof.getTeacherPreference().getDaysOfWeek();
            if (weekDayPref.contains(WeekDay.NONE)) {
                continue;
            }
            for (DayOfWeek dayOfWeek : timeSlot.getDaysOfWeek()) {
                WeekDay weekDayActual = WeekDay.fromDayOfWeek(dayOfWeek);
                if (!weekDayPref.contains(weekDayActual)) {
                    hyp.getViolationCount().addViolationAcceptable();
                    break;
                }
            }
        }
    }

}
