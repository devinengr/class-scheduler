package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.basic_model.*;
import onl.devin.geneticsai.constraints.concrete.basic_teacher_model.ConstraintTeacherGetsOneSectionPerTimeOfDay;
import onl.devin.geneticsai.constraints.concrete.teacher_difference_model.ConstraintTeacherAssignmentsWithinDeltaW;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherGetsBoardPreference;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherGetsTimeOfDayPreference;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherGetsWeekDayPreference;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherTeachingWithinMinAndMaxSections;
import onl.devin.geneticsai.constraints.concrete.teacher_satisfaction_model.ConstraintWeighTeacherSectionPreferences;

import java.util.ArrayList;
import java.util.List;

public class TeacherTricriteriaModel implements Model {

    private int dayDifferenceThreshold = 0;
    private int teachingLoadThreshold = 0;
    private int satisfactionWeight = 0;

    public TeacherTricriteriaModel(int dayDifferenceThreshold,
                                   int teachingLoadThreshold,
                                   int satisfactionWeight) {
        this.dayDifferenceThreshold = dayDifferenceThreshold;
        this.teachingLoadThreshold = teachingLoadThreshold;
        this.satisfactionWeight = satisfactionWeight;
    }

    @Override
    public List<Constraint> getConstraints() {
        ConstraintMWFAndTRCoursesWithinDeltaW.setThreshold(dayDifferenceThreshold);
        ConstraintTeacherAssignmentsWithinDeltaW.setThreshold(teachingLoadThreshold);
        ConstraintWeighTeacherSectionPreferences.setWeight(satisfactionWeight);

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
                new ConstraintTeacherGetsTimeOfDayPreference(),
                new ConstraintTeacherGetsWeekDayPreference(),
                new ConstraintTeacherTeachingWithinMinAndMaxSections(),
                // teacher difference model
                new ConstraintTeacherAssignmentsWithinDeltaW()
        ));
    }

}
