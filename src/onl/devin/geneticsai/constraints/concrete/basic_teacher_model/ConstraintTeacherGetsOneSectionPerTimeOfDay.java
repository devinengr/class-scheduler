package onl.devin.geneticsai.constraints.concrete.basic_teacher_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.util.ProfessorTimePair;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;

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
