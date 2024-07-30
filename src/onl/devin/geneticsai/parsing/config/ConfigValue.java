package onl.devin.geneticsai.parsing.config;

public enum ConfigValue {

    // Genetics
    FITNESS_THRESHOLD(85),
    HYPOTHESES_TO_PRUNE_PER_ITER(1),
    MUTATION_RATE(5),
    MUTATION_PROBABILITY_PER_ITER_OUT_OF_1000(1),
    ACCEPTABLE_FITNESS(85),

    // Tricriteria
    DAY_DIFFERENCE_THRESHOLD(3),
    TEACHER_LOAD_THRESHOLD(3),
    TEACHER_SATISFACTION_WEIGHT(2),
    ;

    private int value;

    ConfigValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return this.name();
    }

}
