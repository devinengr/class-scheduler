package onl.devin.geneticsai;

import onl.devin.geneticsai.genetics.procedure.Genetics;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.implementation.model.Model;
import onl.devin.geneticsai.parsing.*;
import onl.devin.geneticsai.parsing.config.*;

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

        reader.parseFile(ConfigValueCSV.PATH_TO_CLASSROOMS_DATA.getPath(), classRoomParser);
        reader.parseFile(ConfigValueCSV.PATH_TO_TIME_SLOTS_DATA.getPath(), timeSlotParser);
        reader.parseFile(ConfigValueCSV.PATH_TO_COURSE_SECTIONS_DATA.getPath(), courseSectionParser);

        reader.parseFile(ConfigValueCSV.PATH_TO_TEACHER_SATISFACTION_DATA.getPath(), teacherSatisfactionParser);
        reader.parseFile(ConfigValueCSV.PATH_TO_TEACHER_PREFERENCE_DATA.getPath(), teacherPreferenceParser);

        ClassRoom.initializeBitStringData();
        TimeSlot.initializeBitStringData();
        Professor.initializeBitStringData();
        CourseSection.initializeBitStringData();

        Genetics genetics = new Genetics();
        Model model = ConfigKeyModel.MODEL.getModel();
        Population population = genetics.run(model);
        model.printResults(population);
    }
    
}
