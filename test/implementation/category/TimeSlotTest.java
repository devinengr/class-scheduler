package implementation.category;

import implementation.util.TimeOfDay;
import org.junit.jupiter.api.Test;
import parsing.TimeSlotParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    private void testCollidesByTimeSlot(String line1, String line2, boolean collisionExpected) {
        TimeSlotParser parser = new TimeSlotParser();
        parser.parseLine(line1, 2);
        parser.parseLine(line2, 2);
        TimeSlot slot1 = parser.getTimeSlotList().get(0);
        TimeSlot slot2 = parser.getTimeSlotList().get(1);
        assertEquals(collisionExpected, slot1.collidesByTimeSlot(slot2));
        assertEquals(collisionExpected, slot2.collidesByTimeSlot(slot1));
    }

    private void testCollidesByTimeOfDay(String line1, String line2, boolean collisionExpected) {
        TimeSlotParser parser = new TimeSlotParser();
        parser.parseLine(line1, 2);
        parser.parseLine(line2, 2);
        TimeSlot slot1 = parser.getTimeSlotList().get(0);
        TimeSlot slot2 = parser.getTimeSlotList().get(1);
        assertEquals(collisionExpected, slot1.collidesByTimeOfDay(slot2));
        assertEquals(collisionExpected, slot2.collidesByTimeOfDay(slot1));
    }

    private void testCollidesByDay(String line1, String line2, boolean collisionExpected) {
        TimeSlotParser parser = new TimeSlotParser();
        parser.parseLine(line1, 2);
        parser.parseLine(line2, 2);
        TimeSlot slot1 = parser.getTimeSlotList().get(0);
        TimeSlot slot2 = parser.getTimeSlotList().get(1);
        assertEquals(collisionExpected, slot1.collidesByDay(slot2));
        assertEquals(collisionExpected, slot2.collidesByDay(slot1));
    }

    private void testInitTimesOfDay(String line, List<TimeOfDay> expectedTimesOfDay) {
        TimeSlotParser parser = new TimeSlotParser();
        parser.parseLine(line, 2);
        TimeSlot slot = parser.getTimeSlotList().get(0);
        for (TimeOfDay actualDay : slot.getTimesOfDay()) {
            assertTrue(expectedTimesOfDay.contains(actualDay));
        }
        for (TimeOfDay expectedDay : expectedTimesOfDay) {
            assertTrue(slot.getTimesOfDay().contains(expectedDay));
        }
    }

    private void testNumberOfCredits(String line, int expectedCredits) {
        TimeSlotParser parser = new TimeSlotParser();
        parser.parseLine(line, 2);
        TimeSlot slot1 = parser.getTimeSlotList().get(0);
        assertEquals(expectedCredits, slot1.getNumberOfCredits());
    }

    @Test
    void numberOfCredits() {
        testNumberOfCredits("1,MW 1 - 2:50pm", 4);
        testNumberOfCredits("1,MWF 1:15pm - 2:20pm", 4);
        testNumberOfCredits("1,MW 11:30 - 12:45pm", 3);
        testNumberOfCredits("1,MWF 7 - 7:50am", 3);
        testNumberOfCredits("1,MW 7 - 8:01am", 3);
        testNumberOfCredits("1,MW 7 - 8am", 2);
    }

    @Test
    void initializeTimesOfDay() {
        testInitTimesOfDay("1,MW 11:59am - 12:01pm", List.of(TimeOfDay.MORNING, TimeOfDay.AFTERNOON));
        testInitTimesOfDay("1,MW 11:00am - 11:59am", List.of(TimeOfDay.MORNING));
        testInitTimesOfDay("1,MW 12:00pm - 3:59pm", List.of(TimeOfDay.AFTERNOON));
        testInitTimesOfDay("1,MW 4pm - 5pm", List.of(TimeOfDay.AFTERNOON, TimeOfDay.EVENING));
        testInitTimesOfDay("1,MW 4:01pm - 5pm", List.of(TimeOfDay.EVENING));
    }

    @Test
    void collidesByTimeOfDay() {
        testCollidesByTimeOfDay("1,MW 4-5pm", "1,TR 6-7pm", true);
        testCollidesByTimeOfDay("1,MW 3-4pm", "1,TR 6-7pm", false);
        testCollidesByTimeOfDay("1,MW 11-12pm", "1,MW 1-2pm", true);
        testCollidesByTimeOfDay("1,MW 11-11:59am", "1,MW 1-2pm", false);
        testCollidesByTimeOfDay("1,MW 11-11:30am", "1,MW 8-9", false);
    }

    @Test
    void collidesByDay() {
        testCollidesByDay("1,MW 1-2pm", "1,WF 1-2pm", true);
        testCollidesByDay("1,M 1-2pm", "1,F 1-2pm", false);
        testCollidesByDay("1,T 1-2pm", "1,R 1-2pm", false);
        testCollidesByDay("1,TR 1-2pm", "1,TR 1-2pm", true);
        testCollidesByDay("1,MWF 1-2pm", "1,MW 1-2pm", true);
        testCollidesByDay("1,MWF 1-2pm", "1,TR 1-2pm", false);
    }

    @Test
    void collidesByTimeSlot() {
        testCollidesByTimeSlot("1,MW 11:30 - 12:45pm", "1,MW 1 - 2:15", false);
        testCollidesByTimeSlot("1,MF 7 - 8:15pm", "1,MWF 11-11:50am", false);
        testCollidesByTimeSlot("1,MWF 11-11:50am", "1,WF 11:51 - 2pm", false);
        testCollidesByTimeSlot("1,TR 10 - 11:50am", "1,MW 10:30 - 12pm", true);
        testCollidesByTimeSlot("1,TR 4 - 6", "1,TR 5:00pm-7pm", true);
        testCollidesByTimeSlot("1,MWF 1-2pm", "1,TR 2 - 3pm", true);
        testCollidesByTimeSlot("1,MW 1pm-2pm", "1,MW 1:01pm - 1:59pm", true);
    }

}
