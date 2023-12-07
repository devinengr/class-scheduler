package implementation;

import genetics.representation.Category;
import genetics.util.BitStringGenerator;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class TimeSlot extends Category {

    private static List<TimeSlot> timeSlotList = new ArrayList<>();

    private List<DayOfWeek> daysOfWeek;
    private int minutesStart;
    private int minutesEnd;

    public TimeSlot() {
        this.daysOfWeek = new ArrayList<>();
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

    @Override
    public int getOutcomeCount() {
        return timeSlotList.size();
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

}
