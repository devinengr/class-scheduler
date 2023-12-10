package genetics.procedure;

import genetics.representation.Population;
import implementation.model.Model;

public class Genetics {

    public static final int FITNESS_THRESHOLD = 97;
    public static final int POPULATION_SIZE = 100;
    public static final int HYPOTHESES_TO_PRUNE_PER_ITER = 20;
    public static final int MUTATION_RATE = 5;

    public void run(Model model) {
        PopulationGenerator populationGenerator = new PopulationGenerator(POPULATION_SIZE);
        Population popCurrent = populationGenerator.newPopulationBasicModel(POPULATION_SIZE);

        FitnessEvaluator evaluator = new FitnessEvaluator(popCurrent, model);
        Mutator mutator = new Mutator(popCurrent, MUTATION_RATE);
        Selection selection = new Selection(popCurrent, Genetics.FITNESS_THRESHOLD);
        Crossover crossover = new Crossover(
                    CrossoverMaskGenerator.singlePointCrossover(popCurrent),
                    popCurrent);

        evaluator.evaluateFitness();
        while (!evaluator.populationIsAcceptable()) {
            selection.select();

            // todo prune lowest 10% of hypotheses
            popCurrent = crossover.execute();
            crossover.setPopulation(popCurrent);
            evaluator.setPopulation(popCurrent);
            mutator.setPopulation(popCurrent);
            selection.setPopulation(popCurrent);

            mutator.mutate();
            evaluator.evaluateFitness();
            populationGenerator.addToPopulationBasicModel(popCurrent);
        }
    }

}
