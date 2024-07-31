package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.HypothesisSorter;
import onl.devin.geneticsai.ModelFinalLogBuilder;
import onl.devin.geneticsai.ModelLogBuilder;
import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.basic_model.*;
import onl.devin.geneticsai.constraints.concrete.basic_teacher_model.ConstraintTeacherGetsOneSectionPerTimeOfDay;
import onl.devin.geneticsai.constraints.concrete.teacher_difference_model.ConstraintTeacherAssignmentsWithinDeltaW;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherGetsBoardPreference;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherGetsTimeOfDayPreference;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherGetsWeekDayPreference;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.ConstraintTeacherTeachingWithinMinAndMaxSections;
import onl.devin.geneticsai.constraints.concrete.teacher_satisfaction_model.ConstraintWeighTeacherSectionPreferences;
import onl.devin.geneticsai.genetics.procedure.FitnessEvaluator;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.implementation.util.BoardType;
import onl.devin.geneticsai.implementation.util.TimeOfDay;
import onl.devin.geneticsai.implementation.util.TypePref;
import onl.devin.geneticsai.implementation.util.WeekDay;
import onl.devin.geneticsai.parsing.config.ConfigValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TeacherTricriteriaModel implements Model {

    private int dayDifferenceThreshold;
    private int teachingLoadThreshold;
    private int satisfactionWeight;

    public TeacherTricriteriaModel() {
        this.dayDifferenceThreshold = ConfigValue.DAY_DIFFERENCE_THRESHOLD.getValue();
        this.teachingLoadThreshold = ConfigValue.TEACHER_LOAD_THRESHOLD.getValue();
        this.satisfactionWeight = ConfigValue.TEACHER_SATISFACTION_WEIGHT.getValue();
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

    @Override
    public void printResults(Population population) {
        List<Hypothesis> hypList = population.getHypothesisList();
        HypothesisSorter.sort(hypList);
        population = new Population(hypList);
        ModelLogBuilder.log("\nDONE\n");
        for (Hypothesis hyp : population.getHypothesisList()) {
            ModelLogBuilder modelLogBuilder = new ModelLogBuilder(hyp, this, population);
            modelLogBuilder
                    .appendSection()
                    .appendTeacherID()
                    .appendRoomNumber()
                    .appendStartTime()
                    .appendEndTime()
                    .appendTeacherSatisfaction()
                    .appendTimeOfDay()
                    .appendDayOfWeek()
                    .appendBoardType()
                    .appendRoomType()
                    .appendClassCount()
                    .printBuiltLog();
        }
        ModelFinalLogBuilder modelFinalLogBuilder = new ModelFinalLogBuilder(this, population);
        modelFinalLogBuilder
                .appendFinalNumberOfTR()
                .appendFinalNumberOfMWF()
                .appendFinalNumberOfUniqueSections()
                .appendFinalNumberOfMissingSections()
                .appendFinalNumberOfProfessors()
                .appendFinalNumberOfMissingProfessors()
                .printBuiltLog();
    }

}
