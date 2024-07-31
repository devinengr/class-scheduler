package onl.devin.geneticsai;

import onl.devin.geneticsai.genetics.procedure.FitnessEvaluator;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.implementation.model.BasicModel;
import onl.devin.geneticsai.implementation.model.Model;
import onl.devin.geneticsai.implementation.util.BoardType;
import onl.devin.geneticsai.implementation.util.TimeOfDay;
import onl.devin.geneticsai.implementation.util.TypePref;
import onl.devin.geneticsai.implementation.util.WeekDay;

import java.util.List;

public class ModelLogBuilder {

    private Population population;
    private Hypothesis hypothesis;
    private  FitnessEvaluator evaluator;
    private StringBuilder stringBuilder;

    private CourseSection courseSection;
    private ClassRoom classRoom;
    private TimeSlot timeSlot;
    private Professor professor;

    public ModelLogBuilder(Hypothesis hypothesis, Model model, Population population) {
        this.stringBuilder = new StringBuilder();
        this.evaluator = new FitnessEvaluator(model);
        this.population = population;
        this.hypothesis = hypothesis;
        initClassroom();
        initSection();
        initTimeSlot();
        if (!(model instanceof BasicModel)) {
            initProfessor();
        }
    }

    private void initClassroom() {
        classRoom = hypothesis.getCategory(ClassRoom.class);
    }

    private void initSection() {
        courseSection = hypothesis.getCategory(CourseSection.class);
    }

    private void initTimeSlot() {
        timeSlot = hypothesis.getCategory(TimeSlot.class);
    }

    private void initProfessor() {
        professor = hypothesis.getCategory(Professor.class);
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

    public ModelLogBuilder appendSection() {
        stringBuilder.append(String.format("Section: %d\n", courseSection.getSectionAbsolute()));
        return this;
    }

    public ModelLogBuilder appendTeacherID() {
        stringBuilder.append(String.format("Professor: %d\n", professor.getTeacherID()));
        return this;
    }

    public ModelLogBuilder appendRoomNumber() {
        stringBuilder.append(String.format("Room: %d\n", classRoom.getRoomNumber()));
        return this;
    }

    public ModelLogBuilder appendStartTime() {
        stringBuilder.append(String.format("Start Time: %d\n", timeSlot.getMinutesStart()));
        return this;
    }

    public ModelLogBuilder appendEndTime() {
        stringBuilder.append(String.format("End Time: %d\n", timeSlot.getMinutesEnd()));
        return this;
    }

    public ModelLogBuilder appendTeacherSatisfaction() {
        int satisfaction = professor
                .getTeacherSatisfaction()
                .getPreferenceLevelForCourseSection(courseSection);
        stringBuilder.append(String.format(String.format("Satisfaction [0=best]: %d\n", satisfaction)));
        return this;
    }

    public ModelLogBuilder appendTimeOfDay() {
        TimeOfDay todPref = professor.getTeacherPreference().getTimeOfDay();
        stringBuilder.append(String.format("TODPref, TODAct: %s, %s\n", todPref, timeSlot.getTimesOfDay()));
        return this;
    }

    public ModelLogBuilder appendDayOfWeek() {
        List<WeekDay> dowPref = professor.getTeacherPreference().getDaysOfWeek();
        stringBuilder.append(String.format("DOWPref, DOWAct: %s, %s\n", dowPref, timeSlot.getDaysOfWeek()));
        return this;
    }

    public ModelLogBuilder appendBoardType() {
        BoardType boardPref = professor.getTeacherPreference().getBoardType();
        BoardType boardActual = classRoom.getBoardType();
        stringBuilder.append(String.format("BPref,BAct: %s, %s\n", boardPref, boardActual));
        return this;
    }

    public ModelLogBuilder appendRoomType() {
        TypePref typePref = professor.getTeacherPreference().getTypePref();
        TypePref typeActual = courseSection.getTypePref();
        stringBuilder.append(String.format("TPref,TAct: %s, %s\n", typePref, typeActual));
        return this;
    }

    public ModelLogBuilder appendClassCount() {
        int numSectionsProfIsTeaching = evaluator.numberOfSectionsTeaching(population).get(professor);
        int minSec = professor.getTeacherPreference().getMinSections();
        int maxSec = professor.getTeacherPreference().getMaxSections();
        stringBuilder.append(String.format("Class Count. Min,Actual,Max: %d, %d, %d\n",
                minSec, numSectionsProfIsTeaching, maxSec));
        return this;
    }

    public void printBuiltLog() {
        log(stringBuilder.toString());
    }

}
