package constraints.concrete.basic_model;

import constraints.Constraint;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;

public class ConstraintNo4CreditSectionGets3CreditTime implements Constraint {

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection section = hyp.getCategory(CourseSection.class);
            TimeSlot timeSlot = hyp.getCategory(TimeSlot.class);
            if (section.getNumberOfCredits() == 4) {
                if (timeSlot.getNumberOfCredits() == 3) {
                    hyp.getViolationCount().addViolationUnacceptable();
                }
            }
        }
    }

}
