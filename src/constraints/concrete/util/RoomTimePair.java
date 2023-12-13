package constraints.concrete.util;

import implementation.category.ClassRoom;
import implementation.category.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class RoomTimePair {
    private static List<RoomTimePair> roomTimePairList = new ArrayList<>();
    private ClassRoom classRoom;
    private TimeSlot timeSlot;

    public RoomTimePair(ClassRoom classRoom, TimeSlot timeSlot) {
        this.classRoom = classRoom;
        this.timeSlot = timeSlot;
    }

    public static void reset() {
        roomTimePairList.clear();
    }

    public static RoomTimePair findBy(ClassRoom classRoom, TimeSlot timeSlot) {
        for (RoomTimePair rtp : roomTimePairList) {
            if (classRoom.equals(rtp.classRoom)) {
                if (timeSlot.equals(rtp.timeSlot)) {
                    return rtp;
                }
            }
        }
        return null;
    }

    public static boolean timeOfDayClashesOnTheSameDay(ClassRoom classRoom, TimeSlot timeSlot) {
        for (RoomTimePair rtp : roomTimePairList) {
            if (rtp.classRoom.equals(classRoom)) {
                if (rtp.timeSlot.collidesByDay(timeSlot)) {
                    if (rtp.timeSlot.collidesByTimeOfDay(timeSlot)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
