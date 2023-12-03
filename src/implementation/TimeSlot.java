package implementation;

import genetics.representation.Category;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class TimeSlot extends Category {

    private List<DayOfWeek> daysOfWeek;
    private int minutesStart;
    private int minutesEnd;

    public TimeSlot() {
        super(0, false);
        this.daysOfWeek = new ArrayList<>();
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
