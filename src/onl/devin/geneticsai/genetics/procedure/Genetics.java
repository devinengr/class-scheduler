package onl.devin.geneticsai.genetics.procedure;

import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.model.*;
import onl.devin.geneticsai.parsing.ConfigParser;
import onl.devin.geneticsai.parsing.ConfigValue;

public class Genetics {

    public Population run(Model model) {
        int populationSize = CourseSection.getNumberOfCourseSections();

        PopulationGenerator populationGenerator = new PopulationGenerator();
        Population popCurrent = null;

        if (model instanceof BasicModel) {
            popCurrent = populationGenerator.newPopulationBasicModel(populationSize, false);
        } else if (model instanceof BasicTeacherModel
                || model instanceof TeacherPreferenceModel
                || model instanceof TeacherDifferenceModel
                || model instanceof TeacherSatisfactionModel
                || model instanceof TeacherTricriteriaModel) {
            popCurrent = populationGenerator.newPopulationBasicModel(populationSize, true);
        }

        FitnessEvaluator evaluator = new FitnessEvaluator(model);
        Mutator mutator = new Mutator(ConfigValue.MUTATION_RATE.getValue());
        Selection selection = new Selection(ConfigValue.FITNESS_THRESHOLD.getValue());
        Crossover crossover = new Crossover(CrossoverMaskGenerator.singlePointCrossover(popCurrent));
        RouletteWheel rouletteWheel = new RouletteWheel();
        evaluator.evaluateFitness(popCurrent);

        while (!evaluator.populationIsAcceptable(popCurrent)) {
            evaluator.evaluateFitness(popCurrent);              // fitness eval
            selection.select(popCurrent);                       // selection and pruning

            popCurrent = rouletteWheel.pruneByLowestFitness(popCurrent);
            popCurrent = crossover.execute(popCurrent);         // crossover

            popCurrent = rouletteWheel.pruneInvalidBitStrings(popCurrent); // clear un-decodable hypotheses
            if (mutator.willMutateThisRound(ConfigValue.MUTATION_PROBABILITY_PER_ITER_OUT_OF_1000.getValue())) {
                mutator.mutate(popCurrent);
            }
            popCurrent = rouletteWheel.pruneInvalidBitStrings(popCurrent); // clear un-decodable hypotheses


            int size = popCurrent.getHypothesisList().size();   // add new random hypotheses to new generation
            int amountNeeded = populationSize - size;

            if (model instanceof BasicModel) {
                populationGenerator.addToPopulationBasicModel(popCurrent, amountNeeded, false);
            } else if (model instanceof BasicTeacherModel
                    || model instanceof TeacherPreferenceModel
                    || model instanceof TeacherDifferenceModel
                    || model instanceof TeacherSatisfactionModel
                    || model instanceof TeacherTricriteriaModel) {
                populationGenerator.addToPopulationBasicModel(popCurrent, amountNeeded, true);
            }

            evaluator.evaluateFitness(popCurrent);              // fitness eval

            int sumGood = 0;                                    // print progress
            for (Hypothesis hypothesis : popCurrent.getHypothesisList()) {
                if (hypothesis.getFitness() >= ConfigValue.ACCEPTABLE_FITNESS.getValue()) {
                    sumGood++;
                }
            }
            System.out.println(sumGood + " / " + popCurrent.getHypothesisList().size() + " hypotheses pass by fitness");
        }
        return popCurrent;
    }

}
