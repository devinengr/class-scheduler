package genetics.procedure;

import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.model.*;

public class Genetics {

    public static final int FITNESS_THRESHOLD = 90;
    public static int POPULATION_SIZE = 0;
    public static final int HYPOTHESES_TO_PRUNE_PER_ITER = 0;
    public static final int MUTATION_RATE = 40;
    public static final int ACCEPTABLE_FITNESS = 93;

    public Population run(Model model) {
        POPULATION_SIZE = CourseSection.getNumberOfCourseSections();
        PopulationGenerator populationGenerator = new PopulationGenerator();
        Population popCurrent = null;

        if (model instanceof BasicModel) {
            popCurrent = populationGenerator.newPopulationBasicModel(POPULATION_SIZE);
        } else if (model instanceof BasicTeacherModel
                || model instanceof TeacherPreferenceModel
                || model instanceof TeacherDifferenceModel) {
            popCurrent = populationGenerator.newPopulationBasicTeacherModel(POPULATION_SIZE);
        }

        FitnessEvaluator evaluator = new FitnessEvaluator(model);
        Mutator mutator = new Mutator(MUTATION_RATE);
        Selection selection = new Selection(FITNESS_THRESHOLD);
        Crossover crossover = new Crossover(CrossoverMaskGenerator.singlePointCrossover(popCurrent));
        RouletteWheel rouletteWheel = new RouletteWheel();
        evaluator.evaluateFitness(popCurrent);
        while (!evaluator.populationIsAcceptable(popCurrent)) {
            evaluator.evaluateFitness(popCurrent);              // fitness eval
            selection.select(popCurrent);                       // selection and pruning
            popCurrent = rouletteWheel.pruneByLowestFitness(popCurrent);
            popCurrent = crossover.execute(popCurrent);         // crossover
            mutator.mutate(popCurrent);                         // mutation
            popCurrent = rouletteWheel.pruneInvalidBitStrings(popCurrent); // clear un-decodable hypotheses
            int size = popCurrent.getHypothesisList().size();   // add new random hypotheses to new generation
            int amountNeeded = POPULATION_SIZE - size;

            if (model instanceof BasicModel) {
                populationGenerator.addToPopulationBasicModel(popCurrent, amountNeeded);
            } else if (model instanceof BasicTeacherModel
                    || model instanceof TeacherPreferenceModel
                    || model instanceof TeacherDifferenceModel) {
                populationGenerator.addToPopulationBasicTeacherModel(popCurrent, amountNeeded);
            }

            evaluator.evaluateFitness(popCurrent);              // fitness eval
            int sumGood = 0;                                    // print progress
            for (Hypothesis hypothesis : popCurrent.getHypothesisList()) {
                if (hypothesis.getFitness() >= ACCEPTABLE_FITNESS) {
                    sumGood++;
                }
            }
            System.out.println(sumGood + " / " + popCurrent.getHypothesisList().size() + " hypotheses pass by fitness");
        }
        return popCurrent;
    }

}
