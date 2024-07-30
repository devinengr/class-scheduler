package onl.devin.geneticsai.genetics.procedure;

import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;

public class Selection {

    private int fitnessThreshold;

    public Selection(int fitnessThreshold) {
        this.fitnessThreshold = fitnessThreshold;
    }

    public void select(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            if (hyp.getFitness() >= fitnessThreshold) {
                hyp.setPassesFitnessThreshold(true);
            } else {
                hyp.setPassesFitnessThreshold(false);
            }
        }
    }

}
