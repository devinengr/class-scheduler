package onl.devin.geneticsai.constraints.concrete.teacher_difference_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.procedure.FitnessEvaluator;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;

import java.util.Map;

public class ConstraintTeacherAssignmentsWithinDeltaW implements Constraint {

    private static int W_THRESHOLD = 4;

    public static void setThreshold(int threshold) {
        W_THRESHOLD = threshold;
    }

    private boolean withinDelta(int amount) {
        int numProf = Professor.getNumberOfProfessors();
        int numSec = CourseSection.getNumberOfCourseSections();
        int ideal = Math.round((float) numSec / (float) numProf);
        if (Math.abs(ideal - amount) <= W_THRESHOLD) {
            return true;
        }
        return false;
    }

    @Override
    public void evaluate(Population population) {
        FitnessEvaluator evaluator = new FitnessEvaluator(null);
        Map<Professor, Integer> numberOfSectionsTeaching;
        numberOfSectionsTeaching = evaluator.numberOfSectionsTeaching(population);
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            int sectionsTeaching = numberOfSectionsTeaching.get(prof);
            if (!withinDelta(sectionsTeaching)) {
                hyp.getViolationCount().addViolationUnacceptable();
            }
        }
    }

}
