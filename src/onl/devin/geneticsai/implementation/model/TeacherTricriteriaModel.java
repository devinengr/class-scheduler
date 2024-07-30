package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.basic_model.ConstraintMWFAndTRCoursesWithinDeltaW;
import onl.devin.geneticsai.constraints.concrete.teacher_difference_model.ConstraintTeacherAssignmentsWithinDeltaW;
import onl.devin.geneticsai.constraints.concrete.teacher_satisfaction_model.ConstraintWeighTeacherSectionPreferences;

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
        return new TeacherSatisfactionModel().getConstraints();
    }

}
