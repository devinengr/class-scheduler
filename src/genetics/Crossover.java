package genetics;

import genetics.Bitstring;
import genetics.Hypothesis;

import java.util.ArrayList;
import java.util.List;

public class Crossover {

    private Bitstring mask;
    private List<Hypothesis> hypotheses;

    public Crossover(Bitstring mask) {
        this.hypotheses = new ArrayList<>();
        this.mask = mask;
    }

}
