package implementation.model;

import constraints.Constraint;
import constraints.concrete.basic_model.*;
import constraints.concrete.basic_teacher_model.ConstraintTeacherGetsOneSectionPerTimeOfDay;
import constraints.concrete.teacher_difference_model.ConstraintTeacherAssignmentsWithinDeltaW;
import constraints.concrete.teacher_preference_model.*;
import constraints.concrete.teacher_satisfaction_model.ConstraintWeighTeacherSectionPreferences;

import java.util.ArrayList;
import java.util.List;

public class TeacherDifferenceModel implements Model {

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
                new ConstraintTeacherTeachingWithinMinAndMaxSections(),

                // teacher difference model
                new ConstraintTeacherAssignmentsWithinDeltaW()
        ));
    }

}
