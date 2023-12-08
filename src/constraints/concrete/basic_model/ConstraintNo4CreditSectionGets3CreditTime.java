package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.TimeSlot;

public class ConstraintNo4CreditSectionGets3CreditTime implements Constraint {

    @Override
    public void evaluate(Population population, Hypothesis hypothesis, ViolationCount violationCount) {
        TimeSlot timeSlot = hypothesis.getCategory(TimeSlot.class);
        for (Hypothesis hyp : population.getHypothesisList()) {

        }
    }

}
