package parsing;

import implementation.TimeSlot;

import java.nio.CharBuffer;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeSlotParser implements FileParser {

    private List<TimeSlot> timeSlotList;

    public TimeSlotParser() {
        this.timeSlotList = new ArrayList<>();
    }

    public List<TimeSlot> getTimeSlotList() {
        return new ArrayList<>(timeSlotList);
    }

    public String[] getHoursAndMinutes(String time) {
        String hourFrame, minuteFrame;
        if (time.contains(":")) {
            String[] split = time.split(":");
            hourFrame = split[0];
            minuteFrame = split[1];
        } else {
            hourFrame = time;
            minuteFrame = "0";
        }
        return new String[] { hourFrame, minuteFrame };
    }

    public String removeAMAndPM(String frame) {
        if (frame.contains("am")) {
            return frame.split("am")[0];
        }
        if (frame.contains("pm")) {
            return frame.split("pm")[0];
        }
        return frame;
    }

    public int adjustHoursForPM(int hours, boolean timeIsPM) {
        if (timeIsPM) {
            // don't add 12 to 12. add 12 to ie 1, so 12+1 = 13
            if (hours != 12) {
                hours += 12;
            }
        } else {
            // in AM, 12 is not 12 hours ahead of midnight, so make it 0
            if (hours == 12) {
                hours = 0;
            }
        }
        return hours;
    }

    public int getTimeInMinutes(String timeHours, String timeMinutes, boolean timeIsPM) {
        int hours = adjustHoursForPM(Integer.parseInt(timeHours), timeIsPM);
        int minutes = Integer.parseInt(timeMinutes);
        return hours * 60 + minutes;
    }

    public void parseTime(TimeSlot timeSlot, String timeStart, String timeEnd) {
        String[] frameStart = getHoursAndMinutes(timeStart);
        String[] frameEnd = getHoursAndMinutes(timeEnd);

        String hourStartTrimmed = removeAMAndPM(frameStart[0]);
        String minStartTrimmed = removeAMAndPM(frameStart[1]);
        String hourEndTrimmed = removeAMAndPM(frameEnd[0]);
        String minEndTrimmed = removeAMAndPM(frameEnd[1]);

        int timeStartInMinutesAM = getTimeInMinutes(hourStartTrimmed, minStartTrimmed, false);
        int timeStartInMinutesPM = getTimeInMinutes(hourStartTrimmed, minStartTrimmed, true);
        int timeEndInMinutesAM = getTimeInMinutes(hourEndTrimmed, minEndTrimmed, false);
        int timeEndInMinutesPM = getTimeInMinutes(hourEndTrimmed, minEndTrimmed, true);

        boolean startHasAM = timeStart.contains("am");
        boolean startHasPM = timeStart.contains("pm");
        boolean startHasInd = startHasAM || startHasPM;
        boolean endHasAM = timeEnd.contains("am");
        boolean endHasPM = timeEnd.contains("pm");
        boolean endHasInd = endHasAM || endHasPM;

        // if neither time has an indicator, then we assume that
        // they are both PM
        if (!startHasInd && !endHasInd) {
            timeSlot.setMinutesStart(timeStartInMinutesPM);
            timeSlot.setMinutesEnd(timeEndInMinutesPM);
        }

        // if the start time has no AM or PM indicator, but the end time
        // has a PM indicator, then the start time is only PM if it is
        // greater than the end time. For instance, start time = 11, and
        // start time = 1. This means 11am - 1pm, because 11 > 1.
        else if (!startHasInd && endHasPM) {
            if (timeStartInMinutesAM > timeEndInMinutesAM) {
                // start time is AM
                timeSlot.setMinutesStart(timeStartInMinutesAM);
                timeSlot.setMinutesEnd(timeEndInMinutesPM);
            } else {
                // start time is PM
                timeSlot.setMinutesStart(timeStartInMinutesPM);
                timeSlot.setMinutesEnd(timeEndInMinutesPM);
            }
        }

        // if both times have an AM or PM indicator, then we can just
        // set them to AM / PM respectively
        else if (startHasInd && endHasInd) {
            // time start
            if (startHasAM) {
                timeSlot.setMinutesStart(timeStartInMinutesAM);
            } else {
                timeSlot.setMinutesStart(timeStartInMinutesPM);
            }
            // time end
            if (endHasAM) {
                timeSlot.setMinutesEnd(timeEndInMinutesAM);
            } else {
                timeSlot.setMinutesEnd(timeEndInMinutesPM);
            }
        }

        // if the start time has no indicator, but the end time
        // is AM, then we can set them both to AM
        else if (!startHasInd && endHasAM) {
            timeSlot.setMinutesStart(timeStartInMinutesAM);
            timeSlot.setMinutesEnd(timeEndInMinutesAM);
        }
    }

    public void parseDaysOfWeek(TimeSlot timeSlot, String daysOfWeek) {
        CharBuffer.wrap(daysOfWeek).chars().forEach(c -> {
            switch (c) {
                case 'M':
                    timeSlot.addDayOfWeek(DayOfWeek.MONDAY);
                    break;
                case 'T':
                    timeSlot.addDayOfWeek(DayOfWeek.TUESDAY);
                    break;
                case 'W':
                    timeSlot.addDayOfWeek(DayOfWeek.WEDNESDAY);
                    break;
                case 'R':
                    timeSlot.addDayOfWeek(DayOfWeek.THURSDAY);
                    break;
                case 'F':
                    timeSlot.addDayOfWeek(DayOfWeek.FRIDAY);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void parseLine(String line, int lineNumber) {
        // ignore header
        if (lineNumber == 1) {
            return;
        }
        TimeSlot timeSlot = new TimeSlot();
        String[] split = Arrays.stream(line.split("[, -]"))
                .filter(s -> !s.isEmpty()).toArray(String[]::new);
        String daysOfWeek = split[0];
        String timeStart = split[1];
        String timeEnd = split[2];
        parseDaysOfWeek(timeSlot, daysOfWeek);
        parseTime(timeSlot, timeStart, timeEnd);
        timeSlotList.add(timeSlot);
    }

}
