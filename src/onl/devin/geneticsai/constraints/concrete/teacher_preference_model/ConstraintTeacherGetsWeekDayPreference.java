package onl.devin.geneticsai.constraints.concrete.teacher_preference_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.implementation.util.WeekDay;

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
