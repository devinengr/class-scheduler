package onl.devin.geneticsai.genetics.representation;

import onl.devin.geneticsai.constraints.ViolationCount;

import java.util.ArrayList;
import java.util.List;

public class Hypothesis implements Comparable<Hypothesis> {

    private List<Category> categories;
    private int fitness;
    private ViolationCount violationCount;
    private boolean passesFitnessThreshold = false;
    private BitString fullBitString;

    public Hypothesis() {
        this.categories = new ArrayList<>();
        this.violationCount = new ViolationCount();
    }

    @Override
    public Hypothesis clone() {
        Hypothesis clone = new Hypothesis();
        clone.categories = new ArrayList<>(categories);
        clone.fitness = fitness;
        clone.passesFitnessThreshold = passesFitnessThreshold;
        clone.fullBitString = fullBitString;
        clone.violationCount = violationCount;
        return clone;
    }

    public ViolationCount getViolationCount() {
        return violationCount;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public <T extends Category> T getCategory(Class<T> type) {
        for (Category category : categories) {
            if (category.getClass().equals(type)) {
                return type.cast(category);
            }
        }
        return null;
    }

    public BitString getFullBitString() {
        return fullBitString;
    }

    public void setFullBitString(BitString fullBitString) {
        this.fullBitString = fullBitString;
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getFitness() {
        return fitness;
    }

    public boolean passesFitnessThreshold() {
        return passesFitnessThreshold;
    }

    public void setPassesFitnessThreshold(boolean passesFitnessThreshold) {
        this.passesFitnessThreshold = passesFitnessThreshold;
    }

    @Override
    public int compareTo(Hypothesis other) {
        return Integer.compare(fitness, other.getFitness());
    }

}
