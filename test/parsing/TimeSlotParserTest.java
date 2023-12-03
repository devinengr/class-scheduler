package parsing;

import implementation.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotParserTest {

    private List<DayOfWeek> listMW;
    private List<DayOfWeek> listMF;
    private List<DayOfWeek> listMWF;
    private List<DayOfWeek> listTR;

    void testParser(TimeSlotParser parser, int index,
                    String line, int lineNumber,
                    int minutesStart, int minutesEnd,
                    List<DayOfWeek> expectedDaysOfWeek) {
        parser.parseLine(line, lineNumber);
        TimeSlot timeSlot = parser.getTimeSlotList().get(index);
        List<DayOfWeek> actualDaysOfWeek = timeSlot.getDaysOfWeek();
        assertEquals(expectedDaysOfWeek.size(), actualDaysOfWeek.size());
        for (int i = 0; i < expectedDaysOfWeek.size(); i++) {
            assertEquals(expectedDaysOfWeek.get(i), actualDaysOfWeek.get(i));
        }
        assertEquals(minutesStart, timeSlot.getMinutesStart());
        assertEquals(minutesEnd, timeSlot.getMinutesEnd());
    }

    void testDaysOfWeek(TimeSlotParser parser, String days,
                        List<DayOfWeek> expectedDaysOfWeek) {
        TimeSlot timeSlot = new TimeSlot();
        parser.parseDaysOfWeek(timeSlot, days);
        List<DayOfWeek> actualDaysOfWeek = timeSlot.getDaysOfWeek();
        assertEquals(expectedDaysOfWeek.size(), actualDaysOfWeek.size());
        for (int i = 0; i < expectedDaysOfWeek.size(); i++) {
            assertEquals(expectedDaysOfWeek.get(i), actualDaysOfWeek.get(i));
        }
    }

    @BeforeEach
    void init() {
        listMW = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        listMF = List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        listMWF = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        listTR = List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);
    }

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
        List<DayOfWeek> list1 = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        List<DayOfWeek> list2 = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        testParser(parser, 0, "1,MW 1 - 2:15", 2, 780, 855, list1);
        testParser(parser, 1, "1,MWF 11-11:50am", 3, 660, 710, list2);
    }

    @Test
    void parseLineIgnoresHeader() {
        TimeSlotParser parser = new TimeSlotParser();
        Executable exec = () -> testParser(parser, 0, "1,MW 11:30 - 12:45pm", 1, 690, 765, listMW);
        assertThrows(IndexOutOfBoundsException.class, exec);
        testParser(parser, 0, "1,MWF 11-11:50am", 2, 660, 710, listMWF);
    }

    @Test
    void parseDaysOfWeek() {
        testDaysOfWeek(new TimeSlotParser(), "MF", listMF);
        testDaysOfWeek(new TimeSlotParser(), "MW", listMW);
        testDaysOfWeek(new TimeSlotParser(), "MWF", listMWF);
        testDaysOfWeek(new TimeSlotParser(), "TR", listTR);
    }

    @Test
    void parseLine() {
        testParser(new TimeSlotParser(), 0, "1,MW 11:30 - 12:45pm", 2, 690, 765, listMW);
        testParser(new TimeSlotParser(), 0, "1,MW 1 - 2:15", 0, 780, 855, listMW);
        testParser(new TimeSlotParser(), 0, "1,MF 7 - 8:15pm", 3, 1140, 1215, listMF);
        testParser(new TimeSlotParser(), 0, "1,MWF 11-11:50am", 4, 660, 710, listMWF);
        testParser(new TimeSlotParser(), 0, "1,MWF 12-12:50pm", 5, 720, 770, listMWF);
        testParser(new TimeSlotParser(), 0, "1,TR 8:30 - 9:15am", 5, 510, 555, listTR);
        testParser(new TimeSlotParser(), 0, "1,MW 11am - 12:50pm", 6, 660, 770, listMW);
        testParser(new TimeSlotParser(), 0, "1,TR 10 - 11:50am", 7, 600, 710, listTR);
        testParser(new TimeSlotParser(), 0, "1,MF 4 - 5:15", 8, 960, 1035, listMF);
    }

}
