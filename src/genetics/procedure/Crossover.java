package genetics.procedure;

import genetics.representation.BitString;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.model.Model;

import java.util.Collections;
import java.util.List;

public class Crossover {

    public static final int FITNESS_THRESHOLD = 97;
    public static final int POPULATION_SIZE = 100;
    public static final int HYPOTHESES_TO_REPLACE_EACH_CROSSOVER = 50;
    public static final int MUTATION_RATE = 5;

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

    private BitString createChild(BitString bit1, BitString bit2, boolean maskMode) {
        String s1 = bit1.getBitString();
        String s2 = bit2.getBitString();
        String sMask = mask.getBitString();
        String child = "";
        for (int i = 0; i < sMask.length(); i++) {
            if (sMask.charAt(i) == '1') {
                if (maskMode) {
                    child += s1.charAt(i);
                } else {
                    child += s2.charAt(i);
                }
            } else {
                if (maskMode) {
                    child += s2.charAt(i);
                } else {
                    child += s1.charAt(i);
                }
            }
        }
        return new BitString(child);
    }

    private void performCrossover() {
        List<Hypothesis> hypList = population.getHypothesisList();
        Collections.sort(hypList);
        Collections.reverse(hypList);

        for (int i = 0; i + 1 < hypList.size(); i += 1) {
            // SELECTION
            Hypothesis hyp1 = hypList.get(i);
            if (hyp1.getFitness() >= FITNESS_THRESHOLD) {
                continue;
            }
            i += 1;
            Hypothesis hyp2 = hypList.get(i + 1);

            // CROSSOVER
            BitString bit1 = hyp1.getFullBitString();
            BitString bit2 = hyp2.getFullBitString();
            BitString child1 = createChild(bit1, bit2, true);
            BitString child2 = createChild(bit1, bit2, false);

            // temp
            System.out.println(child1.getBitString());
            System.out.println(child2.getBitString());

            // todo generate a new population
        }

        // MUTATION
        // todo (invert 1 random bit on m random hypotheses)
    }

    // todo create a way to decode bitstrings back into
    // todo the new category combinations that they represent
    // todo if a combination doesn't exist, low fitness value
    // todo or something else

    public void execute() {
        // todo generate p hypotheses at random

        evaluateFitness();

        // todo hypotheses below the threshold: perform roulette wheel selection
        // todo to account for hypotheses pruned, randomly generate new hypotheses

        performCrossover();
    }

}
