package genetics.procedure;

import genetics.representation.Category;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;

import java.util.Random;

public class PopulationGenerator {

    private int targetHypothesisCount;

    public PopulationGenerator(int targetHypothesisCount) {
        this.targetHypothesisCount = targetHypothesisCount;
    }

    public Population newPopulationBasicModel(int targetHypothesisCount) {
        Random rng = new Random();
        int randomCourseSection = 0;
        int randomClassRoomNumber = 0;
        int randomTimeSlotIndex = 0;
        Population population = new Population();

        for (int i = 0; i < targetHypothesisCount; i++) {
            randomCourseSection = rng.nextInt(1, CourseSection.getNumberOfCourseSections() + 1);
            randomClassRoomNumber = rng.nextInt(1, ClassRoom.getNumberOfClassRooms() + 1);
            randomTimeSlotIndex = rng.nextInt(0, TimeSlot.getNumberOfTimeSlots());

            Category courseSection = CourseSection.getSection(randomCourseSection);
            Category classRoom = ClassRoom.getClassRoomByRoomNumber(randomClassRoomNumber);
            Category timeSlot = TimeSlot.getTimeSlotByIndex(randomTimeSlotIndex);

            Hypothesis hypothesis = new Hypothesis();
            hypothesis.addCategory(courseSection);
            hypothesis.addCategory(classRoom);
            hypothesis.addCategory(timeSlot);
            population.addHypothesis(hypothesis);
        }

        return population;
    }

    public void addToPopulationBasicModel(Population population) {
        int newHypothesisCount = targetHypothesisCount - population.getHypothesisList().size();
        Population newPop = newPopulationBasicModel(newHypothesisCount);
        for (Hypothesis hyp : newPop.getHypothesisList()) {
            population.addHypothesis(hyp);
        }
    }

}
