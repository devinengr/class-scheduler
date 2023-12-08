package implementation.category;

import genetics.representation.Category;
import genetics.util.BitStringGenerator;
import implementation.util.TimeOfDay;
import implementation.util.WeekDay;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class TimeSlot extends Category {

    private static List<TimeSlot> timeSlotList = new ArrayList<>();

    private List<TimeOfDay> timesOfDay;
    private List<DayOfWeek> daysOfWeek;
    private int minutesStart;
    private int minutesEnd;

    public TimeSlot() {
        this.daysOfWeek = new ArrayList<>();
        this.timesOfDay = new ArrayList<>();
        timeSlotList.add(this);
    }

    public static TimeSlot getTimeSlotByIndex(int index) {
        return timeSlotList.get(index);
    }

    public static int getNumberOfTimeSlots() {
        return timeSlotList.size();
    }

    /**
     * call after initializing all objects
     */
    public static void initializeBitStringData() {
        BitStringGenerator bitGen = new BitStringGenerator(timeSlotList.get(0));
        timeSlotList.forEach(slot -> {
            slot.setBitString(bitGen.next());
        });
    }

    public void initializeTimesOfDay() {
        TimeOfDay.setTimesOfDay(this);
    }

    public void addTimeOfDay(TimeOfDay timeOfDay) {
        timesOfDay.add(timeOfDay);
    }

    public List<TimeOfDay> getTimesOfDay() {
        return new ArrayList<>(timesOfDay);
    }

    @Override
    public int getOutcomeCount() {
        return timeSlotList.size();
    }

    @Override
    public List<Category> getOutcomeList() {
        return new ArrayList<>(timeSlotList);
    }

    public void addDayOfWeek(DayOfWeek dayOfWeek) {
        if (!daysOfWeek.contains(dayOfWeek)) {
            daysOfWeek.add(dayOfWeek);
        }
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return new ArrayList<>(daysOfWeek);
    }

    public int getMinutesStart() {
        return minutesStart;
    }

    public void setMinutesStart(int minutesStart) {
        this.minutesStart = minutesStart;
    }

    public int getMinutesEnd() {
        return minutesEnd;
    }

    public void setMinutesEnd(int minutesEnd) {
        this.minutesEnd = minutesEnd;
    }

    public boolean collidesByTimeOfDay(TimeSlot other) {
        for (TimeOfDay time : timesOfDay) {
            if (other.getTimesOfDay().contains(time)) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesByTimeSlot(TimeSlot other) {
        int otherStart = other.getMinutesStart();
        int otherEnd = other.getMinutesEnd();
        if (otherStart <= minutesEnd && otherEnd >= minutesStart ||
                otherEnd >= minutesStart && otherStart <= minutesEnd) {
            return true;
        }
        return false;
    }

    public boolean collidesByDay(TimeSlot other) {
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            if (other.getDaysOfWeek().contains(dayOfWeek)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds up the total number of minutes in the course per
     * week, and converts it to hours. Gets the ceiling of
     * total hours for the number of credits the course is
     * worth.
     * @return number of credits
     */
    public int getNumberOfCredits() {
        int totalMinutes = 0;
        for (DayOfWeek days : daysOfWeek) {
            totalMinutes += (minutesEnd - minutesStart);
        }
        float hours = (float) totalMinutes / 60;
        return (int) Math.ceil(hours);
    }

}
