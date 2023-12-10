package genetics.procedure;

import genetics.representation.BitString;
import genetics.representation.Hypothesis;
import genetics.representation.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutator {

    private Population population;
    private int mutationRate;

    public Mutator(Population population, int mutationRate) {
        this.population = population;
        this.mutationRate = mutationRate;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    private List<Integer> getIndicesToMutate() {
        Random rng = new Random();
        int listSize = population.getHypothesisList().size();
        int mutationCount = (int) ((float) listSize * ((float) mutationRate / 100.0f));
        List<Integer> indicesToMutate = new ArrayList<>();
        for (int i = 0; i < mutationCount; i++) {
            int toMutate = rng.nextInt(0, listSize);
            while (indicesToMutate.contains(toMutate)) {
                toMutate = rng.nextInt(0, listSize);
            }
            indicesToMutate.add(toMutate);
        }
        return indicesToMutate;
    }

    public void mutate() {
        Random rng = new Random();
        List<Integer> indicesToMutate = getIndicesToMutate();
        List<Hypothesis> hyps = population.getHypothesisList();
        for (int index : indicesToMutate) {
            String bit = hyps.get(index).getFullBitString().getBitString();
            char[] bits = bit.toCharArray();
            int bitIndex = rng.nextInt(0, bits.length);
            if (bits[bitIndex] == '1') {
                bits[bitIndex] = '0';
            } else {
                bits[bitIndex] = '1';
            }
            String newBits = new String(bits);
            hyps.get(index).setFullBitString(new BitString(newBits));
        }
    }

}
