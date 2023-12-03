package parsing;

import implementation.TimeSlot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotParserTest {

    @Test
    void getTimeInMinutes() {
        TimeSlotParser parser = new TimeSlotParser();
        assertEquals(8*60+15, parser.getTimeInMinutes("8", "15", false));
        assertEquals((8+12)*60+15, parser.getTimeInMinutes("8", "15", true));
        assertEquals(0, parser.getTimeInMinutes("12", "0", false));
        assertEquals(12*60, parser.getTimeInMinutes("12", "0", true));
        assertEquals(11*60+59, parser.getTimeInMinutes("11", "59", false));
        assertEquals((11+12)*60+59, parser.getTimeInMinutes("11", "59", true));
        assertEquals(1*60, parser.getTimeInMinutes("1", "0", false));
        assertEquals((1+12)*60, parser.getTimeInMinutes("1", "0", true));
        assertEquals(59, parser.getTimeInMinutes("12", "59", false));
        assertEquals(12*60+59, parser.getTimeInMinutes("12", "59", true));
    }

    @Test
    void adjustHoursForPM() {
        TimeSlotParser parser = new TimeSlotParser();
        assertEquals(0, parser.adjustHoursForPM(12, false));
        assertEquals(1, parser.adjustHoursForPM(1, false));
        assertEquals(13, parser.adjustHoursForPM(1, true));
        assertEquals(12, parser.adjustHoursForPM(12, true));
        assertEquals(18, parser.adjustHoursForPM(6, true));
        assertEquals(6, parser.adjustHoursForPM(6, false));
    }

    @Test
    void getHoursAndMinutes() {
        TimeSlotParser parser = new TimeSlotParser();
        assertEquals("7", parser.getHoursAndMinutes("7")[0]);
        assertEquals("0", parser.getHoursAndMinutes("7")[1]);
        assertEquals("8", parser.getHoursAndMinutes("8:15pm")[0]);
        assertEquals("15pm", parser.getHoursAndMinutes("8:15pm")[1]);
    }

    @Test
    void removeAMAndPM() {
        TimeSlotParser parser = new TimeSlotParser();
        assertEquals("7", parser.removeAMAndPM("7"));
        assertEquals("8:15", parser.removeAMAndPM("8:15pm"));
        assertEquals("11:50", parser.removeAMAndPM("11:50am"));
        assertEquals("8:30", parser.removeAMAndPM("8:30"));
        assertEquals("5", parser.removeAMAndPM("5pm"));
    }

    @Test
    void parseTime() {
        TimeSlotParser parser = new TimeSlotParser();
        TimeSlot timeSlot = new TimeSlot();
        parser.parseTime(timeSlot, "1", "2:15");
        assertEquals(780, timeSlot.getMinutesStart());
        assertEquals(855, timeSlot.getMinutesEnd());
        parser.parseTime(timeSlot, "11", "11:50am");
        assertEquals(660, timeSlot.getMinutesStart());
        assertEquals(710, timeSlot.getMinutesEnd());
        parser.parseTime(timeSlot, "11:30", "12:45pm");
        assertEquals(690, timeSlot.getMinutesStart());
        assertEquals(765, timeSlot.getMinutesEnd());
        parser.parseTime(timeSlot, "11am", "12:50pm");
        assertEquals(660, timeSlot.getMinutesStart());
        assertEquals(770, timeSlot.getMinutesEnd());
    }

    @Test
    void parseLineMultiple() {
        TimeSlotParser parser = new TimeSlotParser();
        parser.parseLine("1,MW 1 - 2:15", 2);
        parser.parseLine("1,MWF 11-11:50am", 3);
        // 1st time slot
        {
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(780, timeSlot.getMinutesStart());
            assertEquals(855, timeSlot.getMinutesEnd());
        }
        // 2nd time slot
        {
            TimeSlot timeSlot = parser.getTimeSlotList().get(1);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(3, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(2));
            assertEquals(660, timeSlot.getMinutesStart());
            assertEquals(710, timeSlot.getMinutesEnd());
        }
    }

    @Test
    void parseLineIgnoresHeader() {
        TimeSlotParser parser = new TimeSlotParser();
        parser.parseLine("1,MW 11:30 - 12:45pm", 1);
        Executable exec = () -> parser.getTimeSlotList().get(0);
        assertThrows(IndexOutOfBoundsException.class, exec);

        parser.parseLine("1,MWF 11-11:50am", 2);
        TimeSlot timeSlot = parser.getTimeSlotList().get(0);
        List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
        assertEquals(3, daysOfWeek.size());
        assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
        assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
        assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(2));
        assertEquals(660, timeSlot.getMinutesStart());
        assertEquals(710, timeSlot.getMinutesEnd());
    }

    @Test
    void parseDaysOfWeek() {
        TimeSlotParser parser = new TimeSlotParser();
        // MF
        {
            TimeSlot timeSlot = new TimeSlot();
            parser.parseDaysOfWeek(timeSlot, "MF");
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(1));
        }
        // MW
        {
            TimeSlot timeSlot = new TimeSlot();
            parser.parseDaysOfWeek(timeSlot, "MW");
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
        }
        // MWF
        {
            TimeSlot timeSlot = new TimeSlot();
            parser.parseDaysOfWeek(timeSlot, "MWF");
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(3, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(2));
        }
        // TR
        {
            TimeSlot timeSlot = new TimeSlot();
            parser.parseDaysOfWeek(timeSlot, "TR");
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.TUESDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.THURSDAY, daysOfWeek.get(1));
        }
    }

    @Test
    void parseLine() {
        // MW 11:30 - 12:45pm
        // daysOfWeek: [Monday, Wednesday]
        // minutesStart: 690
        // minutesEnd: 765
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,MW 11:30 - 12:45pm", 2);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(690, timeSlot.getMinutesStart());
            assertEquals(765, timeSlot.getMinutesEnd());
        }
        // MW 1 - 2:15
        // daysOfWeek: [Monday, Wednesday]
        // minutesStart: 780
        // minutesEnd: 855
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,MW 1 - 2:15", 0);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(780, timeSlot.getMinutesStart());
            assertEquals(855, timeSlot.getMinutesEnd());
        }
        // MF 7 - 8:15pm
        // daysOfWeek: [Monday, Friday]
        // minutesStart: 1140
        // minutesEnd: 1215
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,MF 7 - 8:15pm", 3);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(1));
            assertEquals(1140, timeSlot.getMinutesStart());
            assertEquals(1215, timeSlot.getMinutesEnd());
        }
        // MWF 11-11:50am
        // daysOfWeek: [Monday, Wednesday, Friday]
        // minutesStart: 660
        // minutesEnd: 710
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,MWF 11-11:50am", 4);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(3, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(2));
            assertEquals(660, timeSlot.getMinutesStart());
            assertEquals(710, timeSlot.getMinutesEnd());
        }
        // MWF 12 - 12:50pm
        // daysOfWeek: [Monday, Wednesday, Friday]
        // minutesStart: 720
        // minutesEnd: 770
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,MWF 12-12:50pm", 5);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(3, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(2));
            assertEquals(720, timeSlot.getMinutesStart());
            assertEquals(770, timeSlot.getMinutesEnd());
        }
        // TR 8:30 - 9:15am
        // daysOfWeek: [Tuesday, Thursday]
        // minutesStart: 510
        // minutesEnd: 555
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,TR 8:30 - 9:15am", 5);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.TUESDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.THURSDAY, daysOfWeek.get(1));
            assertEquals(510, timeSlot.getMinutesStart());
            assertEquals(555, timeSlot.getMinutesEnd());
        }
        // MW 11am - 12:50pm
        // daysOfWeek: [Monday, Wednesday]
        // minutesStart: 660
        // minutesEnd: 770
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,MW 11am - 12:50pm", 6);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.WEDNESDAY, daysOfWeek.get(1));
            assertEquals(660, timeSlot.getMinutesStart());
            assertEquals(770, timeSlot.getMinutesEnd());
        }
        // TR 10 - 11:50am
        // daysOfWeek: [Tuesday, Thursday]
        // minutesStart: 600
        // minutesEnd: 710
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,TR 10 - 11:50am", 7);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.TUESDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.THURSDAY, daysOfWeek.get(1));
            assertEquals(600, timeSlot.getMinutesStart());
            assertEquals(710, timeSlot.getMinutesEnd());
        }
        // MF 4 - 5:15
        // daysOfWeek: [Monday, Friday]
        // minutesStart: 960
        // minutesEnd: 1035
        {
            TimeSlotParser parser = new TimeSlotParser();
            parser.parseLine("1,MF 4 - 5:15", 8);
            TimeSlot timeSlot = parser.getTimeSlotList().get(0);
            List<DayOfWeek> daysOfWeek = timeSlot.getDaysOfWeek();
            assertEquals(2, daysOfWeek.size());
            assertEquals(DayOfWeek.MONDAY, daysOfWeek.get(0));
            assertEquals(DayOfWeek.FRIDAY, daysOfWeek.get(1));
            assertEquals(960, timeSlot.getMinutesStart());
            assertEquals(1035, timeSlot.getMinutesEnd());
        }
    }

}
