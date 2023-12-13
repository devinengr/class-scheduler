package genetics.representation;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Hypothesis> hypothesisList;

    public Population() {
        hypothesisList = new ArrayList<>();
    }

    public Population(List<Hypothesis> hypothesisList) {
        this.hypothesisList = hypothesisList;
    }

    public void addHypothesis(Hypothesis hypothesis) {
        hypothesisList.add(hypothesis);
    }

    public void removeHypothesis(Hypothesis hypothesis) {
        hypothesisList.remove(hypothesis);
    }

    public List<Hypothesis> getHypothesisList() {
        return new ArrayList<>(hypothesisList);
    }

}
