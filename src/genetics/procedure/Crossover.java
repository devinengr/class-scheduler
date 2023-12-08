package genetics.procedure;

import constraints.Constraint;
import genetics.representation.BitString;
import genetics.representation.Category;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.model.Model;

import java.util.List;

public class Crossover {

    private Model model;
    private BitString mask;
    private Population population;

    public Crossover(Model model, BitString mask, Population population) {
        this.model = model;
        this.mask = mask;
        this.population = population;
    }

    public void evaluate() {
        List<Constraint> constraints = model.getConstraints();
    }

    public void execute() {
        for (Hypothesis hypothesis : population.getHypothesisList()) {
            List<Category> categories = hypothesis.getCategories();


            // evaluate with ConstraintEvaluator
            // assign a fitness value
        }
    }

}
