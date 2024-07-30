package onl.devin.geneticsai.implementation.util;

import java.util.ArrayList;
import java.util.List;

public class TeacherPreference {

    int minSections;
    int maxSections;
    private BoardType boardType;
    private TimeOfDay timeOfDay;
    private List<WeekDay> weekDayList;
    private TypePref typePref;

    public TeacherPreference() {
        boardType = BoardType.NONE;
        typePref = TypePref.NONE;
        timeOfDay = TimeOfDay.NONE;
        weekDayList = new ArrayList<>();
        weekDayList.add(WeekDay.NONE);
    }

    public TypePref getTypePref() {
        return typePref;
    }

    public void setTypePref(TypePref typePref) {
        this.typePref = typePref;
    }

    public List<WeekDay> getDaysOfWeek() {
        return new ArrayList<>(weekDayList);
    }

    public void addDayOfWeek(WeekDay weekDay) {
        if (weekDayList.contains(WeekDay.NONE)) {
            weekDayList.remove(WeekDay.NONE);
        }
        weekDayList.add(weekDay);
    }

    public int getMinSections() {
        return minSections;
    }

    public void setMinSections(int minSections) {
        this.minSections = minSections;
    }

    public int getMaxSections() {
        return maxSections;
    }

    public void setMaxSections(int maxSections) {
        this.maxSections = maxSections;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

}
