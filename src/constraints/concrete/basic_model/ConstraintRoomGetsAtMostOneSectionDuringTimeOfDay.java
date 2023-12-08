package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.TimeSlot;

public class ConstraintRoomGetsAtMostOneSectionDuringTimeOfDay implements Constraint {

    @Override
    public void evaluate(Population population, Hypothesis hypothesis, ViolationCount violationCount) {
        ClassRoom classRoom = hypothesis.getCategory(ClassRoom.class);
        for (Hypothesis hyp : population.getHypothesisList()) {
            ClassRoom room = hyp.getCategory(ClassRoom.class);
            if (classRoom.getRoomNumber() == room.getRoomNumber()) {
                TimeSlot time1 = hypothesis.getCategory(TimeSlot.class);
                TimeSlot time2 = hyp.getCategory(TimeSlot.class);
                if (time1.collidesByDay(time2)) {
                    if (time1.collidesByTimeOfDay(time2)) {
                        violationCount.addViolationUnacceptable();
                    }
                }
            }
        }
    }

}
