package constraints.concrete.teacher_satisfaction_model;

import constraints.Constraint;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.Professor;

public class ConstraintWeighTeacherSectionPreferences implements Constraint {

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            CourseSection sec = hyp.getCategory(CourseSection.class);
            int prefLevel = prof.getTeacherSatisfaction().getPreferenceLevelForCourseSection(sec);
            for (int i = 0; i < prefLevel; i++) {
                hyp.getViolationCount().addViolationLevelTeacherSatisfaction();
            }
        }
    }

}
