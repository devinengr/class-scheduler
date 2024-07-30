package onl.devin.geneticsai.constraints.concrete.teacher_preference_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.procedure.FitnessEvaluator;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.Professor;

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
