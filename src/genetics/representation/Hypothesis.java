package genetics.representation;

import implementation.category.ClassRoom;
import implementation.category.CourseSection;
import implementation.category.Professor;
import implementation.category.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class Hypothesis {

    private List<Category> categories;
    private int fitness;

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

    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getFitness() {
        return fitness;
    }

}
