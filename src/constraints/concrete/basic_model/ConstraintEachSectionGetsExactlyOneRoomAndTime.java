package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;

public class ConstraintEachSectionGetsExactlyOneRoomAndTime implements Constraint {

    @Override
    public void evaluate(Population population, Hypothesis hypothesis, ViolationCount violationCount) {
        CourseSection section = hypothesis.getCategory(CourseSection.class);
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection section1 = hyp.getCategory(CourseSection.class);
            if (hyp != hypothesis) {
                if (section.getSectionAbsolute() == section1.getSectionAbsolute()) {
                    violationCount.addViolationUnacceptable();
                }
            }
        }
    }
}

