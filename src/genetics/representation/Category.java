package genetics.representation;

import java.util.ArrayList;
import java.util.List;

public abstract class Category {

    private List<Outcome> outcomeList;
    private boolean binary;

    public Category(int outcomeCount, boolean binary) {
        this.outcomeList = new ArrayList<>();
        this.binary = binary;
    }

    public int getOutcomeCount() {
        return outcomeList.size();
    }

    public boolean isBinary() {
        return binary;
    }

}
