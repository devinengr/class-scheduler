package onl.devin.geneticsai.genetics.procedure;

import onl.devin.geneticsai.genetics.representation.BitString;
import onl.devin.geneticsai.genetics.representation.Category;
import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.genetics.representation.Population;
import onl.devin.geneticsai.implementation.category.ClassRoom;
import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.category.TimeSlot;
import onl.devin.geneticsai.parsing.config.ConfigValue;

import java.util.List;
import java.util.Random;

public class PopulationGenerator {

    public Population newPopulationBasicModel(int amountNeeded, boolean includeTeacherModel) {
        Random rng = new Random();
        int randomCourseSection = 0;
        int randomClassRoomNumber = 0;
        int randomTimeSlotIndex = 0;
        int randomProfessorID = 0;
        Population population = new Population();

        for (int i = 0; i < amountNeeded; i++) {
            randomCourseSection = rng.nextInt(1, CourseSection.getNumberOfCourseSections() + 1);
            randomClassRoomNumber = rng.nextInt(1, ClassRoom.getNumberOfClassRooms() + 1);
            randomTimeSlotIndex = rng.nextInt(0, TimeSlot.getNumberOfTimeSlots());
            if (includeTeacherModel) {
                randomProfessorID = rng.nextInt(1, Professor.getNumberOfProfessors() + 1);
            }

            Category courseSection = CourseSection.getSection(randomCourseSection);
            Category classRoom = ClassRoom.getClassRoomByRoomNumber(randomClassRoomNumber);
            Category timeSlot = TimeSlot.getTimeSlotByIndex(randomTimeSlotIndex);
            Category professor = null;
            if (includeTeacherModel) {
                professor = Professor.getProfessorByID(randomProfessorID);
            }

            Hypothesis hypothesis = new Hypothesis();
            hypothesis.addCategory(courseSection);
            hypothesis.addCategory(classRoom);
            hypothesis.addCategory(timeSlot);
            if (includeTeacherModel) {
                hypothesis.addCategory(professor);
            }
            if (includeTeacherModel) {
                hypothesis.setFullBitString(new BitString(List.of(courseSection, classRoom, timeSlot, professor)));
            } else {
                hypothesis.setFullBitString(new BitString(List.of(courseSection, classRoom, timeSlot)));
            }
            population.addHypothesis(hypothesis);
        }

        return population;
    }

    public void copyHighFitnessParentsToPopulation(Population population, int amountNeeded) {
        int amountCopied = 0;
        for (Hypothesis hyp : population.getHypothesisList()) {
            if (hyp.getFitness() > ConfigValue.FITNESS_THRESHOLD.getValue()) {
                amountCopied += 1;
                population.addHypothesis(hyp.clone());
                if (amountCopied == amountNeeded) {
                    return;
                }
            }
        }
    }

    public void addToPopulationBasicModel(Population population, int amountNeeded, boolean includeTeacherModel) {
        Population newPop = newPopulationBasicModel(amountNeeded, includeTeacherModel);
        for (Hypothesis hyp : newPop.getHypothesisList()) {
            population.addHypothesis(hyp);
        }
    }

}
