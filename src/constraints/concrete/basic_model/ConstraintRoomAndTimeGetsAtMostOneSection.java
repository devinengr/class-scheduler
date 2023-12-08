package constraints.concrete.basic_model;

import constraints.Constraint;
import constraints.ViolationCount;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;

public class ConstraintRoomAndTimeGetsAtMostOneSection implements Constraint {

    @Override
    public void evaluate(Population population, Hypothesis hypothesis, ViolationCount violationCount) {
        ClassRoom classRoom = hypothesis.getCategory(ClassRoom.class);
        TimeSlot timeSlot = hypothesis.getCategory(TimeSlot.class);
        CourseSection courseSection = hypothesis.getCategory(CourseSection.class);
        for (Hypothesis hyp : population.getHypothesisList()) {
            ClassRoom classRoom1 = hyp.getCategory(ClassRoom.class);
            TimeSlot timeSlot1 = hyp.getCategory(TimeSlot.class);
            if (classRoom1 == classRoom && timeSlot1 == timeSlot) {
                CourseSection courseSection1 = hypothesis.getCategory(CourseSection.class);
                if (courseSection1 != courseSection) {
                    violationCount.addViolationUnacceptable();
                }
            }
        }
    }

}
