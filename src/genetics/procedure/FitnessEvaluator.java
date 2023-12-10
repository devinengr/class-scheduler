package genetics.procedure;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.model.Model;

public class FitnessEvaluator {

    public static final int ACCEPTABLE_PROPORTION = 95;

    private Population population;
    private Model model;

    public FitnessEvaluator(Population population, Model model) {
        this.population = population;
        this.model = model;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public boolean populationIsAcceptable() {
        int amountSuccess = 0;
        for (Hypothesis hypothesis : population.getHypothesisList()) {
            if (hypothesis.passesFitnessThreshold()) {
                amountSuccess++;
            }
        }
        int size = population.getHypothesisList().size();
        if ((float) amountSuccess / (float) size >= ACCEPTABLE_PROPORTION) {
            return true;
        }
        return false;
    }

    /**
     * Evaluates the fitness level of each hypothesis
     * in the population and assigns the level to each
     * respective hypothesis.
     */
    public void evaluateFitness() {
        for (Hypothesis hypothesis : population.getHypothesisList()) {
            ViolationCount violationCount = new ViolationCount();
            for (Constraint constraint : model.getConstraints()) {
                constraint.evaluate(population, hypothesis, violationCount);

                // todo this might need some work
                // weight unacceptable violations higher
                int vioNet = violationCount.getViolationsUnacceptable() * 2
                                + violationCount.getViolationsAcceptable();
                int fitness = 100 - vioNet;

                hypothesis.setFitness(fitness);
            }
        }
    }

}
