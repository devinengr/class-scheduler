package genetics.representation;

import java.util.ArrayList;
import java.util.List;

public class Hypothesis implements Comparable<Hypothesis> {

    private List<Category> categories;
    private int fitness;
    private boolean passesFitnessThreshold = false;

    public Hypothesis() {
        this.categories = new ArrayList<>();
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
        return new BitString(this);
    }

    public void setFullBitString(BitString bitString) {
        // todo update categories with new data using decoder
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
