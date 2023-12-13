package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.concrete.util.RoomTimePair;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.TimeSlot;

public class ConstraintRoomAndTimeGetsAtMostOneSection implements Constraint {

    @Override
    public void evaluate(Population population) {
        RoomTimePair.reset();
        for (Hypothesis hyp : population.getHypothesisList()) {
            ClassRoom classRoom = hyp.getCategory(ClassRoom.class);
            TimeSlot timeSlot = hyp.getCategory(TimeSlot.class);
            if (RoomTimePair.findBy(classRoom, timeSlot) == null) {
                new RoomTimePair(classRoom, timeSlot);
            } else {
                hyp.getViolationCount().addViolationUnacceptable();
            }
        }

    }

}


