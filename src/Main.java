import genetics.procedure.Crossover;
import genetics.representation.BitString;
import genetics.representation.Category;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.CourseSection;
import implementation.category.Professor;
import implementation.category.TimeSlot;
import implementation.model.BasicModel;
import parsing.*;

import java.sql.Time;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        TeacherPreferenceParser teacherPreferenceParser = new TeacherPreferenceParser();
        TeacherSatisfactionParser teacherSatisfactionParser = new TeacherSatisfactionParser();
        TimeSlotParser timeSlotParser = new TimeSlotParser();
        ClassRoomParser classRoomParser = new ClassRoomParser();
        CourseSectionParser courseSectionParser = new CourseSectionParser();

        reader.parseFile("res/simulated_data/sim_teacher_satisfaction.csv", teacherSatisfactionParser);
        reader.parseFile("res/simulated_data/sim_teacher_preference.csv", teacherPreferenceParser);
        reader.parseFile("res/simulated_data/sim_classrooms_j.csv", classRoomParser);
        reader.parseFile("res/simulated_data/sim_time_slots_k.csv", timeSlotParser);
        reader.parseFile("res/simulated_data/sim_course_sections_i.csv", courseSectionParser);

        ClassRoom.initializeBitStringData();
        TimeSlot.initializeBitStringData();
        Professor.initializeBitStringData();
        CourseSection.initializeBitStringData();


        int randomClassRoomNumber = 0;
        int randomTimeSlotIndex = 0;
        Random rng = new Random();

        Population population = new Population();

        int digitCountCat1 = 0;
        int digitCountCat2 = 0;
        int digitCountCat3 = 0;
        boolean firstPass = true;

        for (Category section : CourseSection.getSection(1).getOutcomeList()) {
            randomClassRoomNumber = rng.nextInt(1, ClassRoom.getNumberOfClassRooms() + 1);
            randomTimeSlotIndex = rng.nextInt(0, TimeSlot.getNumberOfTimeSlots());

            Category classRoom = ClassRoom.getClassRoomByRoomNumber(randomClassRoomNumber);
            Category timeSlot = TimeSlot.getTimeSlotByIndex(randomTimeSlotIndex);

            if (firstPass) {
                digitCountCat1 = section.getBitString().getDigitCount();
                digitCountCat2 = classRoom.getBitString().getDigitCount();
                digitCountCat3 = timeSlot.getBitString().getDigitCount();
                firstPass = false;
            }

            Hypothesis hypothesis = new Hypothesis();
            hypothesis.addCategory(section);
            hypothesis.addCategory(classRoom);
            hypothesis.addCategory(timeSlot);
            population.addHypothesis(hypothesis);
        }

        int digitCountTotal = digitCountCat1 + digitCountCat2 + digitCountCat3;
        String mask = "";
        for (int i = 0; i < digitCountTotal / 2; i++) {
            mask += "1";
        }
        for (int i = digitCountTotal / 2 + 1; i <= digitCountTotal; i++) {
            mask += "0";
        }
        Crossover crossover = new Crossover(new BasicModel(), new BitString(mask), population);
        crossover.execute();
    }

}
