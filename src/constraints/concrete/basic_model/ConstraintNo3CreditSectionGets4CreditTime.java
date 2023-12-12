package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;

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
