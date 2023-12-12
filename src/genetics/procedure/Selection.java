package genetics.procedure;

import genetics.representation.Hypothesis;
import genetics.representation.Population;

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
