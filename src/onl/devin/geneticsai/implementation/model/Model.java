package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Population;

import java.util.List;

public interface Model {

    List<Constraint> getConstraints();

    void printResults(Population population);

}
