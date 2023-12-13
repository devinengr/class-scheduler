package constraints.concrete.teacher_preference_model;

import constraints.Constraint;
import genetics.procedure.FitnessEvaluator;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.Professor;
import java.util.Map;

public class ConstraintTeacherTeachingWithinMinAndMaxSections implements Constraint {

    @Override
    public void evaluate(Population population) {
        Map<Professor, Integer> numberOfSectionsTeaching;
        FitnessEvaluator eval = new FitnessEvaluator(null);
        numberOfSectionsTeaching = eval.numberOfSectionsTeaching(population);
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            int sectionsTeaching = numberOfSectionsTeaching.get(prof);
            int minPref = prof.getTeacherPreference().getMinSections();
            int maxPref = prof.getTeacherPreference().getMaxSections();
            if (!(minPref <= sectionsTeaching && sectionsTeaching <= maxPref)) {
                hyp.getViolationCount().addViolationAcceptable();
            }
        }
    }

}
