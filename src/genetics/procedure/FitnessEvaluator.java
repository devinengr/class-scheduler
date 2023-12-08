package genetics.procedure;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.model.Model;

public class FitnessEvaluator {

    private Population population;
    private Model model;

    public FitnessEvaluator(Population population, Model model) {
        this.population = population;
        this.model = model;
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
