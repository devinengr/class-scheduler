package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.HypothesisSorter;
import onl.devin.geneticsai.ModelFinalLogBuilder;
import onl.devin.geneticsai.ModelLogBuilder;
import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.basic_model.*;
import onl.devin.geneticsai.constraints.concrete.basic_teacher_model.ConstraintTeacherGetsOneSectionPerTimeOfDay;
import onl.devin.geneticsai.constraints.concrete.teacher_difference_model.ConstraintTeacherAssignmentsWithinDeltaW;
import onl.devin.geneticsai.constraints.concrete.teacher_preference_model.*;
import onl.devin.geneticsai.constraints.concrete.teacher_satisfaction_model.ConstraintWeighTeacherSectionPreferences;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;

import java.util.ArrayList;
import java.util.List;


public class TeacherSatisfactionModel implements Model {

    @Override
    public List<Constraint> getConstraints() {
        return new ArrayList<>(List.of(
                // basic model
                new ConstraintRoomAndTimeGetsAtMostOneSection(),
                new ConstraintEachSectionGetsExactlyOneRoomAndTime(),
                new ConstraintNo3CreditSectionGets4CreditTime(),
                new ConstraintNo4CreditSectionGets3CreditTime(),
                new ConstraintRoomGetsAtMostOneSectionDuringTimeOfDay(),
                new ConstraintEachSectionAssignedExactlyOnce(),
                // basic teacher model
                new ConstraintTeacherGetsOneSectionPerTimeOfDay(),
                // teacher preference model
                new ConstraintTeacherGetsBoardPreference(),
                new ConstraintTeacherGetsTimeOfDayPreference(),
                new ConstraintTeacherGetsWeekDayPreference(),
                new ConstraintTeacherTeachingWithinMinAndMaxSections(),
                // teacher difference model
                new ConstraintTeacherAssignmentsWithinDeltaW(),
                // teacher satisfaction model
                new ConstraintWeighTeacherSectionPreferences()
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
