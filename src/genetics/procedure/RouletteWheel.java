package genetics.procedure;

import genetics.representation.Hypothesis;
import genetics.representation.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RouletteWheel {

    // 10, 30, 50, 70
    // max 100
    // 90, 70, 50, 30
    // generate RNG from 0 - SUM(90,70,50,30)
    // if < 90, then prune fitness 10
    // if < 90+70, then prune fitness 30
    // if < 90+70+50, then prune fitness 50,
    // if < 90+70+50+30, then prune fitness 70
    public Population pruneByFitness(Population population) {
        List<Hypothesis> prunedHypotheses = new ArrayList<>();
        List<Integer> fitnessInverted = new ArrayList<>();
        int sumInverted = 0;
        for (Hypothesis hyp : population.getHypothesisList()) {
            int inverted = 100 - hyp.getFitness();
            if (!fitnessInverted.contains(inverted)) {
                fitnessInverted.add(inverted);
                sumInverted += inverted;
            }
        }
        Random rng = new Random();
        int pruned = 0;
        while (pruned < Genetics.HYPOTHESES_TO_PRUNE_PER_ITER) {
            int randomValue = rng.nextInt(0, sumInverted);
            int subSumInv = 0;
            for (int j = 0; j < fitnessInverted.size(); j++) {
                subSumInv += fitnessInverted.get(j);
                if (randomValue >= subSumInv) {
                    int fitnessToPrune = 100 - fitnessInverted.get(j - 1);
                    for (Hypothesis hypothesis : population.getHypothesisList()) {
                        if (hypothesis.getFitness() == fitnessToPrune) {
                            prunedHypotheses.add(hypothesis);
                            pruned++;
                        }
                    }
                }
            }
        }
        Population popNew = new Population();
        for (Hypothesis hypothesis : population.getHypothesisList()) {
            if (!prunedHypotheses.contains(hypothesis)) {
                popNew.addHypothesis(hypothesis);
            }
        }
        return popNew;
    }

}
