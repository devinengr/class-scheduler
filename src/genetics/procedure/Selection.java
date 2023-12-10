package genetics.procedure;

import genetics.representation.Hypothesis;
import genetics.representation.Population;

public class Selection {

    private Population population;
    private int fitnessThreshold;

    public Selection(Population population, int fitnessThreshold) {
        this.population = population;
        this.fitnessThreshold = fitnessThreshold;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public void select() {
        for (Hypothesis hyp : population.getHypothesisList()) {
            if (hyp.getFitness() >= fitnessThreshold) {
                hyp.setPassesFitnessThreshold(true);
            } else {
                hyp.setPassesFitnessThreshold(false);
            }
        }
    }

}
