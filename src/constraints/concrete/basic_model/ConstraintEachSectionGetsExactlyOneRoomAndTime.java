package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;

import java.util.ArrayList;
import java.util.List;

public class ConstraintEachSectionGetsExactlyOneRoomAndTime implements Constraint {

    @Override
    public void evaluate(Population population) {
        List<CourseSection> sectionsFound = new ArrayList<>();
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection section = hyp.getCategory(CourseSection.class);
            for (CourseSection secFound : sectionsFound) {
                if (secFound.equals(section)) {
                    hyp.getViolationCount().addViolationUnacceptable();
                    break;
                }
            }
        }
    }
}

