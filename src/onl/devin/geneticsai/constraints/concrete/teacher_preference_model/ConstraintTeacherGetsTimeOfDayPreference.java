package onl.devin.geneticsai.constraints.concrete.teacher_preference_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.implementation.util.TimeOfDay;

public class ConstraintTeacherGetsTimeOfDayPreference implements Constraint {

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            TimeSlot timeSlot = hyp.getCategory(TimeSlot.class);
            TimeOfDay timePref = prof.getTeacherPreference().getTimeOfDay();
            for (TimeOfDay timeActual : timeSlot.getTimesOfDay()) {
                if (timeActual != timePref) {
                    hyp.getViolationCount().addViolationAcceptable();
                    break;
                }
            }
        }
    }

}
