package genetics.procedure;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.Professor;
import implementation.category.TimeSlot;
import implementation.model.Model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FitnessEvaluator {

    public static final int ACCEPTABLE_PROPORTION = 95;

    private Model model;

    public FitnessEvaluator(Model model) {
        this.model = model;
    }

    public Map<Professor, Integer> numberOfSectionsTeaching(Population population) {
        Map<Professor, Integer> numberOfSectionsTeaching = new HashMap<>();
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            numberOfSectionsTeaching.put(prof, 1 + numberOfSectionsTeaching.getOrDefault(prof, 0));
        }
        return numberOfSectionsTeaching;
    }

    public int numberOfTR(Population population) {
        int sum = 0;
        for (Hypothesis hyp : population.getHypothesisList()) {
            List<DayOfWeek> days = hyp.getCategory(TimeSlot.class).getDaysOfWeek();
            if (days.contains(DayOfWeek.TUESDAY) && days.contains(DayOfWeek.THURSDAY)) {
                sum++;
            }
        }
        return sum;
    }

    public int numberOfMWF(Population population) {
        int size = population.getHypothesisList().size();
        int tr = numberOfTR(population);
        return size - tr;
    }

    public int numberOfMissingSections(Population population) {
        return getMissingSectionIDs(population).size();
    }

    public List<Integer> getMissingSectionIDs(Population population) {
        List<Integer> found = new ArrayList<>();
        List<Integer> missing = new ArrayList<>();
        for (Hypothesis hyp : population.getHypothesisList()) {
            found.add(hyp.getCategory(CourseSection.class).getSectionAbsolute());
        }
        for (int i = 1; i <= CourseSection.getNumberOfCourseSections(); i++) {
            if (!found.contains(i)) {
                missing.add(i);
            }
        }
        return missing;
    }

    public int numberOfMissingProfessors(Population population) {
        return getMissingProfessorIDs(population).size();
    }

    public List<Integer> getMissingProfessorIDs(Population population) {
        List<Integer> found = new ArrayList<>();
        List<Integer> missing = new ArrayList<>();
        for (Hypothesis hyp : population.getHypothesisList()) {
            found.add(hyp.getCategory(Professor.class).getTeacherID());
        }
        for (int i = 1; i <= Professor.getNumberOfProfessors(); i++) {
            if (!found.contains(i)) {
                missing.add(i);
            }
        }
        return missing;
    }

    public void destroyFitnessForDuplicateSections(Population population) {
        List<Integer> secsFound = new ArrayList<>();
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection sec = hyp.getCategory(CourseSection.class);
            if (secsFound.contains(sec.getSectionAbsolute())) {
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
                hyp.getViolationCount().addViolationUnacceptable();
            } else {
                secsFound.add(sec.getSectionAbsolute());
            }
        }
    }

    public boolean populationIsAcceptable(Population population) {
        int amountSuccess = 0;
        for (Hypothesis hypothesis : population.getHypothesisList()) {
            if (hypothesis.getFitness() >= Genetics.ACCEPTABLE_FITNESS) {
                amountSuccess++;
            }
        }
        int size = population.getHypothesisList().size();
        int acceptedProportion = (int) ((float) amountSuccess / (float) size * 100);
        if (acceptedProportion >= ACCEPTABLE_PROPORTION) {
            int missingSecs = numberOfMissingSections(population);
            int missingProfs = numberOfMissingProfessors(population);
            if (missingSecs != 0 || missingProfs != 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Evaluates the fitness level of each hypothesis
     * in the population and assigns the level to each
     * respective hypothesis.
     */
    public void evaluateFitness(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            hyp.getViolationCount().reset();
        }
        for (Constraint constraint : model.getConstraints()) {
            constraint.evaluate(population);
        }
        destroyFitnessForDuplicateSections(population); // bias against duplicates
        for (Hypothesis hypothesis : population.getHypothesisList()) {
            ViolationCount vc = hypothesis.getViolationCount();
            int vioNet = vc.getViolationsUnacceptable() * 8
                        + vc.getViolationsAcceptable() * 3
                        + vc.getViolationLevelTeacherSatisfaction() * 2;
            int fitness = 100 - vioNet;
            if (fitness < 0) {
                fitness = 0;
            }
            hypothesis.setFitness(fitness);
        }
    }

}
