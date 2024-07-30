package onl.devin.geneticsai.constraints;

import onl.devin.geneticsai.genetics.representation.Population;

public interface Constraint {

    void evaluate(Population population);

}
