package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.basic_model.*;
import onl.devin.geneticsai.constraints.concrete.basic_teacher_model.ConstraintTeacherGetsOneSectionPerTimeOfDay;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.*;
import onl.devin.geneticsai.genetics.representation.Population;

import java.util.ArrayList;
import java.util.List;

public class TeacherPreferenceModel implements Model {

    @Override
    public List<Constraint> getConstraints() {
        return new ArrayList<>(List.of(
                // basic model
                new ConstraintRoomAndTimeGetsAtMostOneSection(),
                new ConstraintEachSectionGetsExactlyOneRoomAndTime(),
                new ConstraintNo3CreditSectionGets4CreditTime(),
                new ConstraintNo4CreditSectionGets3CreditTime(),
                new ConstraintRoomGetsAtMostOneSectionDuringTimeOfDay(),
                new ConstraintMWFAndTRCoursesWithinDeltaW(),
                new ConstraintEachSectionAssignedExactlyOnce(),
                // basic teacher model
                new ConstraintTeacherGetsOneSectionPerTimeOfDay(),
                // teacher preference model
                new ConstraintTeacherGetsBoardPreference(),
                new ConstraintTeacherGetsPureAppliedPreference(),
                new ConstraintTeacherGetsTimeOfDayPreference(),
                new ConstraintTeacherGetsWeekDayPreference(),
                new ConstraintTeacherTeachingWithinMinAndMaxSections()
        ));
    }

    @Override
    public void printResults(Population population) {

    }

}
