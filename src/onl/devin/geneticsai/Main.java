package onl.devin.geneticsai;

import onl.devin.geneticsai.genetics.procedure.Genetics;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.implementation.model.*;
import onl.devin.geneticsai.parsing.*;
import onl.devin.geneticsai.parsing.config.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();

        ConfigParser configParser = new ConfigParser();
        try {
            reader.parseFile("res/config.txt", configParser);
        } catch (IOException e) {
            ModelLogBuilder.log("Error reading config. Does it exist in res/config.txt?");
            return;
        }

        TeacherPreferenceParser teacherPreferenceParser = new TeacherPreferenceParser();
        TeacherSatisfactionParser teacherSatisfactionParser = new TeacherSatisfactionParser();
        TimeSlotParser timeSlotParser = new TimeSlotParser();
        ClassRoomParser classRoomParser = new ClassRoomParser();
        CourseSectionParser courseSectionParser = new CourseSectionParser();

        Model model = ConfigKeyModel.MODEL.getModel();

        try {
            reader.parseFile(ConfigValueCSV.PATH_TO_CLASSROOMS_DATA.getPath(), classRoomParser);
            reader.parseFile(ConfigValueCSV.PATH_TO_TIME_SLOTS_DATA.getPath(), timeSlotParser);
            reader.parseFile(ConfigValueCSV.PATH_TO_COURSE_SECTIONS_DATA.getPath(), courseSectionParser);
            if (!(model instanceof BasicModel)) {
                if (!(model instanceof BasicTeacherModel) &&
                        !(model instanceof TeacherPreferenceModel) &&
                        !(model instanceof TeacherDifferenceModel)) {
                    reader.parseFile(ConfigValueCSV.PATH_TO_TEACHER_SATISFACTION_DATA.getPath(), teacherSatisfactionParser);
                }
                reader.parseFile(ConfigValueCSV.PATH_TO_TEACHER_PREFERENCE_DATA.getPath(), teacherPreferenceParser);
            }
        } catch (IOException e) {
            ModelLogBuilder.log("Error reading CSV files. Make sure they exist and you have permission to read them.");
            return;
        }

        ClassRoom.initializeBitStringData();
        TimeSlot.initializeBitStringData();
        CourseSection.initializeBitStringData();
        if (!(model instanceof BasicModel)) {
            Professor.initializeBitStringData();
        }

        Genetics genetics = new Genetics();
        Population population = genetics.run(model);
        model.printResults(population);
    }
    
}
