package genetics.procedure;

import genetics.representation.BitString;
import genetics.representation.Population;
import implementation.model.Model;

public class Crossover {

    private Model model;
    private BitString mask;
    private Population population;

    public Crossover(Model model, BitString mask, Population population) {
        this.model = model;
        this.mask = mask;
        this.population = population;
    }

    private void evaluateFitness() {
        FitnessEvaluator evaluator = new FitnessEvaluator(population, model);
        evaluator.evaluateFitness();
    }

    private void performCrossover() {
        // todo
    }

    public void execute() {
        evaluateFitness();
        // todo what to do after fitness eval?
        // todo how to choose hypotheses?
        // todo how to filter bad hypotheses when we have a 2:2 mapping?
        // todo check the slides and learn more
        performCrossover();
    }

}
