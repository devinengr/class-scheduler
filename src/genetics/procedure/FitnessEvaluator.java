package genetics.procedure;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.model.Model;

import java.util.ArrayList;
import java.util.List;

public class FitnessEvaluator {

    public static final int ACCEPTABLE_PROPORTION = 100;

    private Model model;

    public FitnessEvaluator(Model model) {
        this.model = model;
    }

    public int numberOfMissingSections(Population population) {
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
        return missing.size();
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
            if (numberOfMissingSections(population) != 0) {
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
        for (Constraint constraint : model.getConstraints()) {
            constraint.evaluate(population);
        }

        for (Hypothesis hypothesis : population.getHypothesisList()) {
            ViolationCount vc = hypothesis.getViolationCount();
            int vioNet = vc.getViolationsUnacceptable() * 8 + vc.getViolationsAcceptable();
            int fitness = 100 - vioNet;
            if (fitness < 0) {
                fitness = 0;
            }
            hypothesis.setFitness(fitness);
        }
    }

}
