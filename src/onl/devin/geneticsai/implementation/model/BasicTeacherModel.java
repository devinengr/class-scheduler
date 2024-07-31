package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.HypothesisSorter;
import onl.devin.geneticsai.ModelFinalLogBuilder;
import onl.devin.geneticsai.ModelLogBuilder;
import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.basic_model.*;
import onl.devin.geneticsai.constraints.concrete.basic_teacher_model.ConstraintTeacherGetsOneSectionPerTimeOfDay;
import onl.devin.geneticsai.genetics.procedure.FitnessEvaluator;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BasicTeacherModel implements Model {

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
                new ConstraintTeacherGetsOneSectionPerTimeOfDay()
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
