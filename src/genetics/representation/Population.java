package genetics.representation;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Hypothesis> hypothesisList;

    public Population() {
        hypothesisList = new ArrayList<>();
    }

    public void addHypothesis(Hypothesis hypothesis) {
        hypothesisList.add(hypothesis);
    }

    public List<Hypothesis> getHypothesisList() {
        return new ArrayList<>(hypothesisList);
    }

}
