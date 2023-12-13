package constraints.concrete.teacher_preference_model;

import constraints.Constraint;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.Professor;
import implementation.category.TimeSlot;
import implementation.util.TimeOfDay;

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
