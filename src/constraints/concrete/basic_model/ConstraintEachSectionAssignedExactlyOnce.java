package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;

public class ConstraintEachSectionAssignedExactlyOnce implements Constraint {

    @Override
    public void evaluate(Population population, Hypothesis hypothesis, ViolationCount violationCount) {
        CourseSection section = hypothesis.getCategory(CourseSection.class);
        for (Hypothesis hyp : population.getHypothesisList()) {
            if (hyp != hypothesis) {
                CourseSection sec = hyp.getCategory(CourseSection.class);
                if (sec.getSectionAbsolute() == section.getSectionAbsolute()) {
                    violationCount.addViolationUnacceptable();
                }
            }
            // todo needs some work
        }
    }

}
