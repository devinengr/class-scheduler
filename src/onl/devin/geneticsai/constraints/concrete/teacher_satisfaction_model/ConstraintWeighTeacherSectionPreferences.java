package onl.devin.geneticsai.constraints.concrete.teacher_satisfaction_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;

public class ConstraintWeighTeacherSectionPreferences implements Constraint {

    private static int W_WEIGHT = 1;

    public static void setWeight(int weight) {
        W_WEIGHT = weight;
    }

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            CourseSection sec = hyp.getCategory(CourseSection.class);
            int prefLevel = prof.getTeacherSatisfaction().getPreferenceLevelForCourseSection(sec);
            for (int i = 0; i < prefLevel; i++) {
                for (int j = 0; j < W_WEIGHT; j++) {
                    hyp.getViolationCount().addViolationLevelTeacherSatisfaction();
                }
            }
        }
    }

}
