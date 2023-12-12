import genetics.procedure.Crossover;
import genetics.procedure.FitnessEvaluator;
import genetics.procedure.Genetics;
import genetics.representation.BitString;
import genetics.representation.Category;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.CourseSection;
import implementation.category.Professor;
import implementation.category.TimeSlot;
import implementation.model.BasicModel;
import implementation.model.Model;
import parsing.*;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
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

        Genetics genetics = new Genetics();
        Model model = new BasicModel();
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

        System.out.println("DONE");
        for (Hypothesis hyp : population.getHypothesisList()) {
            Integer secID = hyp.getCategory(CourseSection.class).getSectionAbsolute();
            Integer roomNum = hyp.getCategory(ClassRoom.class).getRoomNumber();
            TimeSlot slot = hyp.getCategory(TimeSlot.class);
            System.out.print("Sec: " + secID);
            System.out.print("\t| Room: " + roomNum);
            System.out.print("\t| T_Start: " + slot.getMinutesStart());
            System.out.print("\t| T_End: " + slot.getMinutesEnd());
            System.out.print("\t\t| T_TOD: " + slot.getTimesOfDay());
            System.out.print("\t\t\t\t| T_Days: " + slot.getDaysOfWeek());
            System.out.println();
        }

        System.out.println();
        System.out.println("Number of unique sections: " +
                        (population.getHypothesisList().size()
                                - evaluator.numberOfMissingSections(population)));
        System.out.println("Number of missing sections: " + evaluator.numberOfMissingSections(population));
        System.out.println("Number of TR: " + evaluator.numberOfTR(population));
        System.out.println("Number of MWF: " + evaluator.numberOfMWF(population));
    }

}
