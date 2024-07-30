package onl.devin.geneticsai.constraints.concrete.basic_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.TimeSlot;

public class ConstraintNo3CreditSectionGets4CreditTime implements Constraint {

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection section = hyp.getCategory(CourseSection.class);
            TimeSlot timeSlot = hyp.getCategory(TimeSlot.class);
            if (section.getNumberOfCredits() == 3) {
                if (timeSlot.getNumberOfCredits() == 4) {
                    hyp.getViolationCount().addViolationUnacceptable();
                }
            }
        }
    }

}
