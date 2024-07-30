package onl.devin.geneticsai;

import onl.devin.geneticsai.genetics.procedure.FitnessEvaluator;
import onl.devin.geneticsai.genetics.procedure.Genetics;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.implementation.model.Model;
import onl.devin.geneticsai.implementation.model.TeacherTricriteriaModel;
import onl.devin.geneticsai.implementation.util.BoardType;
import onl.devin.geneticsai.implementation.util.TimeOfDay;
import onl.devin.geneticsai.implementation.util.TypePref;
import onl.devin.geneticsai.implementation.util.WeekDay;
import onl.devin.geneticsai.parsing.*;
import onl.devin.geneticsai.parsing.config.ConfigParser;
import onl.devin.geneticsai.parsing.config.ConfigValue;
import onl.devin.geneticsai.parsing.config.ConfigValueCSV;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();

        ConfigParser configParser = new ConfigParser();
        reader.parseFile("res/config.txt", configParser);

        TeacherPreferenceParser teacherPreferenceParser = new TeacherPreferenceParser();
        TeacherSatisfactionParser teacherSatisfactionParser = new TeacherSatisfactionParser();
        TimeSlotParser timeSlotParser = new TimeSlotParser();
        ClassRoomParser classRoomParser = new ClassRoomParser();
        CourseSectionParser courseSectionParser = new CourseSectionParser();

        reader.parseFile(ConfigValueCSV.PATH_TO_TEACHER_SATISFACTION_DATA.getPath(), teacherSatisfactionParser);
        reader.parseFile(ConfigValueCSV.PATH_TO_TEACHER_PREFERENCE_DATA.getPath(), teacherPreferenceParser);
        reader.parseFile(ConfigValueCSV.PATH_TO_CLASSROOMS_DATA.getPath(), classRoomParser);
        reader.parseFile(ConfigValueCSV.PATH_TO_TIME_SLOTS_DATA.getPath(), timeSlotParser);
        reader.parseFile(ConfigValueCSV.PATH_TO_COURSE_SECTIONS_DATA.getPath(), courseSectionParser);

        ClassRoom.initializeBitStringData();
        TimeSlot.initializeBitStringData();
        Professor.initializeBitStringData();
        CourseSection.initializeBitStringData();

        Genetics genetics = new Genetics();
        Model model = new TeacherTricriteriaModel(
                ConfigValue.DAY_DIFFERENCE_THRESHOLD.getValue(),
                ConfigValue.TEACHER_LOAD_THRESHOLD.getValue(),
                ConfigValue.TEACHER_SATISFACTION_WEIGHT.getValue());
        Population population = genetics.run(model);
        printResults(model, population);
    }
    
    private static void printResults(Model model, Population population) {
        FitnessEvaluator evaluator = new FitnessEvaluator(model);
        List<Hypothesis> hypList = population.getHypothesisList();
        Collections.sort(hypList, (o1, o2) -> {
            int sec1 = o1.getCategory(CourseSection.class).getSectionAbsolute();
            int sec2 = o2.getCategory(CourseSection.class).getSectionAbsolute();
            return Integer.compare(sec1, sec2);
        });
        population = new Population(hypList);
        System.out.println("\nDONE\n");
        Map<Professor, Integer> numberOfSectionsTeaching = evaluator.numberOfSectionsTeaching(population);
        for (Hypothesis hyp : population.getHypothesisList()) {
            CourseSection sec = hyp.getCategory(CourseSection.class);
            ClassRoom room = hyp.getCategory(ClassRoom.class);
            TimeSlot time = hyp.getCategory(TimeSlot.class);
            Professor prof = hyp.getCategory(Professor.class);
            BoardType boardPref = prof.getTeacherPreference().getBoardType();
            BoardType boardActual = room.getBoardType();
            TypePref typePref = prof.getTeacherPreference().getTypePref();
            TypePref typeActual = sec.getTypePref();
            int sat = prof.getTeacherSatisfaction().getPreferenceLevelForCourseSection(sec);
            int minSec = prof.getTeacherPreference().getMinSections();
            int maxSec = prof.getTeacherPreference().getMaxSections();
            int numTeaching = numberOfSectionsTeaching.get(prof);
            List<WeekDay> dowPref = prof.getTeacherPreference().getDaysOfWeek();
            TimeOfDay todPref = prof.getTeacherPreference().getTimeOfDay();
            System.out.println("Setion: " + sec.getSectionAbsolute());
            System.out.println("Professor: " + prof.getTeacherID());
            System.out.println("Room: " + room.getRoomNumber());
            System.out.println("Start Time: " + time.getMinutesStart());
            System.out.println("End Time: " + time.getMinutesEnd());
            System.out.println("Satisfaction [0=best]: " + sat);
            System.out.format("TODPref, TODAct: %s, %s\n", todPref, time.getTimesOfDay());
            System.out.format("DOWPref, DOWAct: %s, %s\n", dowPref, time.getDaysOfWeek());
            System.out.format("BPref,BAct: %s, %s\n", boardPref, boardActual);
            System.out.format("TPref,TAct: %s, %s\n", typePref, typeActual);
            System.out.format("Class Count. Min,Actual,Max: %d, %d, %d\n", minSec, numTeaching, maxSec);
            System.out.println();
        }
        int numMissingSec = evaluator.numberOfMissingSections(population);
        int numUniqueSec = population.getHypothesisList().size() - numMissingSec;
        int numMissingProf = evaluator.numberOfMissingProfessors(population);
        int numProf = population.getHypothesisList().size() - numMissingProf;
        System.out.println("Number of TR: " + evaluator.numberOfTR(population));
        System.out.println("Number of MWF: " + evaluator.numberOfMWF(population));
        System.out.println("Number of unique sections: " + numUniqueSec);
        System.out.println("Number of missing sections: " + numMissingSec);
        System.out.println("Number of professors: " + numProf);
        System.out.println("Number of missing professors: " + numMissingProf);
    }

}
