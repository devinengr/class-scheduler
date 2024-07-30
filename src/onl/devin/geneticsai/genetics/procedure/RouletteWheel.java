package onl.devin.geneticsai.genetics.procedure;

import onl.devin.geneticsai.genetics.representation.Category;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.parsing.config.ConfigValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RouletteWheel {

    public Population pruneInvalidBitStrings(Population population) {
        Population popNew = new Population();
        for (Hypothesis hyp : population.getHypothesisList()) {
            boolean valid = true;
            for (Category cat : hyp.getCategories()) {
                if (cat == null) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                popNew.addHypothesis(hyp);
            }
        }
        return popNew;
    }

    private List<Integer> getFitnessInverted(Population population) {
        List<Integer> fitnessInverted = new ArrayList<>();
        for (Hypothesis hyp : population.getHypothesisList()) {
            int inverted = 100 - hyp.getFitness();
            if (!fitnessInverted.contains(inverted)) {
                fitnessInverted.add(inverted);
            }
        }
        return fitnessInverted;
    }

    public Population pruneByLowestFitness(Population population) {
        List<Hypothesis> hypList = population.getHypothesisList();
        Collections.sort(hypList);
        Population popNew = new Population();
        for (int i = ConfigValue.HYPOTHESES_TO_PRUNE_PER_ITER.getValue(); i < hypList.size(); i++) {
            popNew.addHypothesis(hypList.get(i));
        }
        return popNew;
    }

    // 10, 30, 50, 70
    // max 100
    // 90, 70, 50, 30
    // generate RNG from 0 - SUM(90,70,50,30)
    // if < 90, then prune fitness 10
    // if < 90+70, then prune fitness 30
    // if < 90+70+50, then prune fitness 50,
    // if < 90+70+50+30, then prune fitness 70
    public Population pruneByFitness(Population population) {
        List<Integer> fitnessInverted = getFitnessInverted(population);
        int sumInverted = 0;
        for (int f : fitnessInverted) {
            sumInverted += f;
        }
        Collections.sort(fitnessInverted);

        List<Hypothesis> prunedHypotheses = new ArrayList<>();
        Random rng = new Random();
        int pruned = 0;
        int maxTries = 100;
        while (pruned < ConfigValue.HYPOTHESES_TO_PRUNE_PER_ITER.getValue() && maxTries > 0) {
            maxTries--;
            int randomValue = rng.nextInt(0, sumInverted);
            int subSumInv = 0;
            for (int j = 0; j < fitnessInverted.size(); j++) {
                subSumInv += fitnessInverted.get(j);
                if (randomValue < subSumInv) {
                    int fitnessToPrune = 100 - fitnessInverted.get(j);
                    for (Hypothesis hypothesis : population.getHypothesisList()) {
                        if (hypothesis.getFitness() == fitnessToPrune) {
                            if (!prunedHypotheses.contains(hypothesis)) {
                                prunedHypotheses.add(hypothesis);
                                pruned++;
                                break;
                            }
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
