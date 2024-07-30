package onl.devin.geneticsai.genetics.procedure;

import onl.devin.geneticsai.genetics.representation.BitString;
import onl.devin.geneticsai.genetics.representation.BitStringDecoder;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutator {

    private int mutationRate;

    public Mutator(int mutationRate) {
        this.mutationRate = mutationRate;
    }

    private List<Integer> getIndicesToMutate(Population population) {
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

    public boolean willMutateThisRound(int probability) {
        Random rng = new Random();
        int rand = rng.nextInt(0, 1000);
        if (rand < probability) {
            return true;
        }
        return false;
    }

    public void mutate(Population population) {
        Random rng = new Random();
        List<Integer> indicesToMutate = getIndicesToMutate(population);
        List<Hypothesis> hyps = population.getHypothesisList();
        BitStringDecoder decoder = new BitStringDecoder();
        for (int index : indicesToMutate) {
            String bit = hyps.get(index).getFullBitString().getBitString();
            char[] bits = bit.toCharArray();
            int bitIndex = rng.nextInt(0, bits.length);
            if (bits[bitIndex] == '1') {
                bits[bitIndex] = '0';
            } else {
                bits[bitIndex] = '1';
            }
            BitString newBits = new BitString(new String(bits));
            Hypothesis hypNew = decoder.decodeFullBitString(hyps.get(index), newBits);
            population.removeHypothesis(hyps.get(index));
            population.addHypothesis(hypNew);
        }
    }

}
