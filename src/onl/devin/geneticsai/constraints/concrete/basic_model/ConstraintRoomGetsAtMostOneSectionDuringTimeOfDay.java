package onl.devin.geneticsai.constraints.concrete.basic_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.constraints.concrete.util.RoomTimePair;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.TimeSlot;

public class ConstraintRoomGetsAtMostOneSectionDuringTimeOfDay implements Constraint {

    @Override
    public void evaluate(Population population) {
        RoomTimePair.reset();
        for (Hypothesis hyp : population.getHypothesisList()) {
            ClassRoom room = hyp.getCategory(ClassRoom.class);
            TimeSlot time = hyp.getCategory(TimeSlot.class);
            if (RoomTimePair.timeOfDayClashesOnTheSameDay(room, time)) {
                hyp.getViolationCount().addViolationUnacceptable();
            } else {
                new RoomTimePair(room, time);
            }
        }
    }

}
