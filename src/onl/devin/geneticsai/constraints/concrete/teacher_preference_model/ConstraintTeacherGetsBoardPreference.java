package onl.devin.geneticsai.constraints.concrete.teacher_preference_model;

import onl.devin.geneticsai.constraints.Constraint;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.util.BoardType;

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
