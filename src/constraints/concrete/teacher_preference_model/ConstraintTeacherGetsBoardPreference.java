package constraints.concrete.teacher_preference_model;

import constraints.Constraint;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.Professor;
import implementation.util.BoardType;

public class ConstraintTeacherGetsBoardPreference implements Constraint {

    @Override
    public void evaluate(Population population) {
        for (Hypothesis hyp : population.getHypothesisList()) {
            Professor prof = hyp.getCategory(Professor.class);
            ClassRoom room = hyp.getCategory(ClassRoom.class);
            BoardType boardPref = prof.getTeacherPreference().getBoardType();
            BoardType boardRoom = room.getBoardType();
            if (boardPref == BoardType.NONE) {
                continue;
            }
            if (boardPref != boardRoom) {
                hyp.getViolationCount().addViolationAcceptable();
            }
        }
    }

}
