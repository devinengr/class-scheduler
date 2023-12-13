package constraints.concrete.teacher_difference_model;

import constraints.Constraint;
import genetics.procedure.FitnessEvaluator;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.Professor;
import implementation.category.TimeSlot;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class ConstraintTeacherAssignmentsWithinDeltaW implements Constraint {

    public static final int W_THRESHOLD = 4;

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
