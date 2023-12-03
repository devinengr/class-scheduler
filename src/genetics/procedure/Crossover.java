package genetics.procedure;

import genetics.representation.BitString;
import genetics.representation.Hypothesis;

import java.util.ArrayList;
import java.util.List;

public class Crossover {

    private BitString mask;
    private List<Hypothesis> hypotheses;

    public Crossover(BitString mask) {
        this.hypotheses = new ArrayList<>();
        this.mask = mask;
    }

}
