package genetics.representation;

public class Hypothesis {

    private Rule rule;
    private int fitness;

    public Hypothesis(Rule rule, int fitness) {
        this.rule = rule;
        this.fitness = fitness;
    }

}
