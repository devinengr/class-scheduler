package genetics.procedure;

import genetics.representation.BitString;
import genetics.representation.BitStringDecoder;
import genetics.representation.Hypothesis;
import genetics.representation.Population;

import java.util.Collections;
import java.util.List;

public class Crossover {

    private BitString mask;
    private Population population;

    public Crossover(BitString mask, Population population) {
        this.mask = mask;
        this.population = population;
    }

    public void setPopulation(Population population) {
        this.population = population;
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

    private Population performCrossover() {
        List<Hypothesis> hypList = population.getHypothesisList();
        Collections.sort(hypList);
        Collections.reverse(hypList);
        Population popNew = new Population();
        BitStringDecoder decoder = new BitStringDecoder();
        for (int i = 0; i + 1 < hypList.size(); i += 1) {
            Hypothesis hyp1 = hypList.get(i);
            if (hyp1.passesFitnessThreshold()) {
                popNew.addHypothesis(hyp1);
                continue;
            }
            Hypothesis hyp2 = hypList.get(i + 1);
            BitString bit1 = hyp1.getFullBitString();
            BitString bit2 = hyp2.getFullBitString();
            BitString child1 = createChild(bit1, bit2, true);
            BitString child2 = createChild(bit1, bit2, false);
            Hypothesis child1New = decoder.decodeFullBitString(hyp2, child1);
            Hypothesis child2New = decoder.decodeFullBitString(hyp2, child2);
            popNew.addHypothesis(child1New);
            popNew.addHypothesis(child2New);
            // temp
            System.out.println(child1.getBitString());
            System.out.println(child2.getBitString());
            // end temp
            i += 1;
        }
        return popNew;
    }

    public Population execute() {
        return performCrossover();
    }

}
