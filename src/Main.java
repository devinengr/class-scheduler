import implementation.ClassRoom;
import implementation.CourseSection;
import implementation.Professor;
import implementation.TimeSlot;
import parsing.*;

public class Main {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        TeacherPreferenceParser teacherPreferenceParser = new TeacherPreferenceParser();
        TeacherSatisfactionParser teacherSatisfactionParser = new TeacherSatisfactionParser();
        TimeSlotParser timeSlotParser = new TimeSlotParser();
        ClassRoomParser classRoomParser = new ClassRoomParser();

        reader.parseFile("res/simulated_data/sim_teacher_satisfaction.csv", teacherSatisfactionParser);
        reader.parseFile("res/simulated_data/sim_teacher_preference.csv", teacherPreferenceParser);
        reader.parseFile("res/simulated_data/sim_classrooms_j.csv", classRoomParser);
        reader.parseFile("res/simulated_data/sim_time_slots_k.csv", timeSlotParser);

        ClassRoom.initializeBitStringData();
        TimeSlot.initializeBitStringData();
        Professor.initializeBitStringData();
        CourseSection.initializeBitStringData();

        for (int i = 1; i <= ClassRoom.getNumberOfClassRooms(); i++) {
            ClassRoom room = ClassRoom.getClassRoomByRoomNumber(i);
            System.out.println(room.getBitString().getBitString());
        }

        for (int i = 0; i < TimeSlot.getNumberOfTimeSlots(); i++) {
            String bit = TimeSlot.getTimeSlotByIndex(i).getBitString().getBitString();
            System.out.println(bit);
        }

        for (int i = 1; i <= Professor.getNumberOfProfessors(); i++) {
            String bit = Professor.getProfessorByID(i).getBitString().getBitString();
            System.out.println(bit);
        }

        for (int i = 1; i <= CourseSection.getNumberOfCourseSections(); i++) {
            String bit = CourseSection.getSection(i).getBitString().getBitString();
            System.out.println(bit);
        }
    }

}
