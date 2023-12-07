package genetics.representation;

import java.util.ArrayList;
import java.util.List;

public class Hypothesis {

    private List<Category> categories;
    private int fitness;

    public void addCategory(Category category) {
        categories.add(category);
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

}
