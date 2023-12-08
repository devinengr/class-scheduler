package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;

public class ConstraintNo3CreditSectionGets4CreditTime implements Constraint {

    @Override
    public void evaluate(Population population, Hypothesis hypothesis, ViolationCount violationCount) {
        CourseSection courseSection = hypothesis.getCategory(CourseSection.class);
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection courseSection1 = hyp.getCategory(CourseSection.class);
            if (courseSection == courseSection1) {
                TimeSlot timeSlot1 = hyp.getCategory(TimeSlot.class);
                if (courseSection1.getNumberOfCredits() == 3) {
                    if (timeSlot1.getNumberOfCredits() == 4) {
                        violationCount.addViolationUnacceptable();
                    }
                }
            }
        }
    }

}
