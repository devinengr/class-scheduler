package genetics.procedure;

import genetics.representation.BitString;
import genetics.representation.Category;
import genetics.representation.Hypothesis;
import genetics.representation.Population;
import implementation.category.ClassRoom;
import implementation.category.CourseSection;
import implementation.category.TimeSlot;

import java.util.List;
import java.util.Random;

public class PopulationGenerator {

    public Population newPopulationBasicModel(int amountNeeded) {
        Random rng = new Random();
        int randomCourseSection = 0;
        int randomClassRoomNumber = 0;
        int randomTimeSlotIndex = 0;
        Population population = new Population();

        for (int i = 0; i < amountNeeded; i++) {
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
            hypothesis.setFullBitString(new BitString(List.of(courseSection, classRoom, timeSlot)));
            population.addHypothesis(hypothesis);
        }

        return population;
    }

    public void copyHighFitnessParentsToPopulation(Population population, int amountNeeded) {
        int amountCopied = 0;
        for (Hypothesis hyp : population.getHypothesisList()) {
            if (hyp.getFitness() > Genetics.FITNESS_THRESHOLD) {
                amountCopied += 1;
                population.addHypothesis(hyp.clone());
                if (amountCopied == amountNeeded) {
                    return;
                }
            }
        }
    }

    public void addToPopulationBasicModel(Population population, int amountNeeded) {
        Population newPop = newPopulationBasicModel(amountNeeded);
        for (Hypothesis hyp : newPop.getHypothesisList()) {
            population.addHypothesis(hyp);
        }
    }

}
