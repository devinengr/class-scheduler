package constraints.concrete.basic_model;

import constraints.Constraint;
import genetics.procedure.FitnessEvaluator;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;

import java.time.DayOfWeek;
import java.util.List;

public class ConstraintMWFAndTRCoursesWithinDeltaW implements Constraint {

    public static final int W_THRESHOLD = 4;

    private boolean withinDelta(int amount) {
        int numSec = CourseSection.getNumberOfCourseSections();
        if (amount - (numSec / 2) <= W_THRESHOLD) {
            return true;
        }
        return false;
    }

    @Override
    public void evaluate(Population population) {
        FitnessEvaluator evaluator = new FitnessEvaluator(null);
        int countMWF = evaluator.numberOfMWF(population);
        int countTR = evaluator.numberOfTR(population);
        for (Hypothesis hyp : population.getHypothesisList()) {
            List<DayOfWeek> days = hyp.getCategory(TimeSlot.class).getDaysOfWeek();
            if (!withinDelta(countMWF)) {
                if (days.contains(DayOfWeek.MONDAY)
                        || days.contains(DayOfWeek.WEDNESDAY)
                        || days.contains(DayOfWeek.FRIDAY)) {
                    hyp.getViolationCount().addViolationAcceptable();
                }
            }
            if (!withinDelta(countTR)) {
                if (days.contains(DayOfWeek.TUESDAY)
                        || days.contains(DayOfWeek.THURSDAY)) {
                    hyp.getViolationCount().addViolationAcceptable();
                }
            }
        }
    }

}
