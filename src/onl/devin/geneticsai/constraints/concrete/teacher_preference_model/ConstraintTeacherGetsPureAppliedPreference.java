package onl.devin.geneticsai.constraints.concrete.teacher_preference_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.util.TypePref;

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
