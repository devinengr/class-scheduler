package constraints;

import genetics.representation.Hypothesis;
import genetics.representation.Population;

public interface Constraint {

    void evaluate(Population population, Hypothesis hypothesis, ViolationCount violationCount);

}
