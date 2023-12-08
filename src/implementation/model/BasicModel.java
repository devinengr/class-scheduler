package implementation.model;

import constraints.Constraint;
import constraints.concrete.basic_model.*;

import java.util.ArrayList;
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

    // todo do something with the goal?

    @Override
    public List<Constraint> getConstraints() {
        return new ArrayList<>(List.of(
                new ConstraintRoomAndTimeGetsAtMostOneSection(),
                new ConstraintEachSectionGetsExactlyOneRoomAndTime(),
                new ConstraintNo3CreditSectionGets4CreditTime(),
                new ConstraintNo4CreditSectionGets3CreditTime(),
                new ConstraintRoomGetsAtMostOneSectionDuringTimeOfDay(),
                new ConstraintMWFAndTRCoursesWithinDeltaW(),
                new ConstraintEachSectionAssignedExactlyOnce(),
                new ConstraintAllCombosIJKAssigned0Or1()
        ));
    }

}
