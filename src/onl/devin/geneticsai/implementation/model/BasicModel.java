package onl.devin.geneticsai.implementation.model;

import onl.devin.geneticsai.HypothesisSorter;
import onl.devin.geneticsai.ModelFinalLogBuilder;
import onl.devin.geneticsai.ModelLogBuilder;
import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.basic_model.*;
import onl.devin.geneticsai.genetics.procedure.FitnessEvaluator;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.TimeSlot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Assign each other:
 *      course section i
 *      classroom j
 *      time module k
 *
 * Goal: minimize difference of # of course sections between these 2 rows:
 * MW, WF, MF, MWF     (50% of course sections)
 * TR                  (50% of course sections)
 */
public class BasicModel implements Model {

    @Override
    public List<Constraint> getConstraints() {
        return new ArrayList<>(List.of(
                new ConstraintRoomAndTimeGetsAtMostOneSection(),
                new ConstraintEachSectionGetsExactlyOneRoomAndTime(),
                new ConstraintNo3CreditSectionGets4CreditTime(),
                new ConstraintNo4CreditSectionGets3CreditTime(),
                new ConstraintRoomGetsAtMostOneSectionDuringTimeOfDay(),
                new ConstraintMWFAndTRCoursesWithinDeltaW(),
                new ConstraintEachSectionAssignedExactlyOnce()
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
                .printBuiltLog();
    }

}
