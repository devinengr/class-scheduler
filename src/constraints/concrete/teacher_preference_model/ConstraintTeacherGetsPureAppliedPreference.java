package constraints.concrete.teacher_preference_model;

import constraints.Constraint;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.CourseSection;
import implementation.category.Professor;
import implementation.util.TypePref;

public class ConstraintTeacherGetsPureAppliedPreference implements Constraint {

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            TypePref typePref = prof.getTeacherPreference().getTypePref();
            TypePref typeActual = hyp.getCategory(CourseSection.class).getTypePref();
            if (typePref == TypePref.NONE) {
                continue;
            }
            if (typePref != typeActual) {
                hyp.getViolationCount().addViolationAcceptable();
            }
        }
    }

}
