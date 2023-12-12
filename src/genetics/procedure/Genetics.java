package genetics.procedure;

import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;
import implementation.model.Model;

public class Genetics {

    public static final int FITNESS_THRESHOLD = 100;
    public static final int POPULATION_SIZE = 29;
    public static final int HYPOTHESES_TO_PRUNE_PER_ITER = 2;
    public static final int MUTATION_RATE = 1;
    public static final int ACCEPTABLE_FITNESS = 100;

    public void run(Model model) {
        PopulationGenerator populationGenerator = new PopulationGenerator();
        Population popCurrent = populationGenerator.newPopulationBasicModel(POPULATION_SIZE);

        FitnessEvaluator evaluator = new FitnessEvaluator(model);
        Mutator mutator = new Mutator(MUTATION_RATE);
        Selection selection = new Selection(FITNESS_THRESHOLD);
        Crossover crossover = new Crossover(CrossoverMaskGenerator.singlePointCrossover(popCurrent));
        RouletteWheel rouletteWheel = new RouletteWheel();

        evaluator.evaluateFitness(popCurrent);
        while (!evaluator.populationIsAcceptable(popCurrent)) {
            // fitness eval
            evaluator.evaluateFitness(popCurrent);

            // selection and pruning
            selection.select(popCurrent);
            //popCurrent = rouletteWheel.pruneByFitness(popCurrent);
            popCurrent = rouletteWheel.pruneByLowestFitness(popCurrent);

            // crossover
            popCurrent = crossover.execute(popCurrent);

            // mutation
            mutator.mutate(popCurrent);

            // clear hypotheses that can't be decoded
            popCurrent = rouletteWheel.pruneInvalidBitStrings(popCurrent);

            // add new random hypotheses to new generation to make up for
            // previously pruned hypotheses
            int size = popCurrent.getHypothesisList().size();
            int amountNeeded = POPULATION_SIZE - size;
            populationGenerator.addToPopulationBasicModel(popCurrent, amountNeeded);

            // fitness eval
            evaluator.evaluateFitness(popCurrent);

            // temp
            int sumGood = 0;
            for (Hypothesis hypothesis : popCurrent.getHypothesisList()) {
                if (hypothesis.getFitness() >= ACCEPTABLE_FITNESS) {
                    sumGood++;
                }
            }
            System.out.println(sumGood + " / " + popCurrent.getHypothesisList().size() + " hypotheses pass by fitness");
        }

        System.out.println("DONE");

        for (Hypothesis hyp : popCurrent.getHypothesisList()) {
            Integer secID = hyp.getCategory(CourseSection.class).getSectionAbsolute();
            Integer roomNum = hyp.getCategory(ClassRoom.class).getRoomNumber();
            TimeSlot slot = hyp.getCategory(TimeSlot.class);

            System.out.print("Sec: " + secID);
            System.out.print("\t| Room: " + roomNum);
            System.out.print("\t| T_Start: " + slot.getMinutesStart());
            System.out.print("\t| T_End: " + slot.getMinutesEnd());
            System.out.print("\t\t| T_TOD: " + slot.getTimesOfDay());
            System.out.print("\t\t\t\t| T_Days: " + slot.getDaysOfWeek());
            System.out.println();
        }
    }

}
