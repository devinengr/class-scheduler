package onl.devin.geneticsai.constraints.concrete.basic_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.CourseSection;

import java.util.ArrayList;
import java.util.List;

public class ConstraintEachSectionAssignedExactlyOnce implements Constraint {

    @Override
    public void evaluate(Population population) {
        List<Integer> sectionIDs = new ArrayList<>();
        int numberOfCourseSections = 0;
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection section = hyp.getCategory(CourseSection.class);
            numberOfCourseSections = section.getOutcomeCount();
            if (sectionIDs.contains(section.getSectionAbsolute())) {
                hyp.getViolationCount().addViolationUnacceptable();
            } else {
                sectionIDs.add(section.getSectionAbsolute());
            }
        }
        if (numberOfCourseSections != sectionIDs.size()) {
            // this is handled by FitnessEvaluator
        }
    }

}
