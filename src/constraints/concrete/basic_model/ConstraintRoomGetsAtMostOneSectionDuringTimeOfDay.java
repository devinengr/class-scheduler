package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.concrete.util.RoomTimePair;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.TimeSlot;

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
