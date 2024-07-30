package onl.devin.geneticsai.constraints.concrete.basic_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.CourseSection;

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

