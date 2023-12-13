package constraints.concrete.basic_teacher_model;

import constraints.Constraint;
import constraints.concrete.util.ProfessorTimePair;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.Professor;
import implementation.category.TimeSlot;

public class ConstraintTeacherGetsOneSectionPerTimeOfDay implements Constraint {

    @Override
    public void evaluate(Population population) {
        ProfessorTimePair.reset();
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor professor = hyp.getCategory(Professor.class);
            TimeSlot time = hyp.getCategory(TimeSlot.class);
            if (ProfessorTimePair.timeOfDayClashesOnTheSameDay(professor, time)) {
                hyp.getViolationCount().addViolationUnacceptable();
            } else {
                new ProfessorTimePair(professor, time);
            }
        }
    }

}
