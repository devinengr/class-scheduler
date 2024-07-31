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

public class ModelFinalLogBuilder {

    private Population population;
    private FitnessEvaluator evaluator;
    private StringBuilder stringBuilder;

    public ModelFinalLogBuilder(Model model, Population population) {
        this.stringBuilder = new StringBuilder();
        this.evaluator = new FitnessEvaluator(model);
        this.population = population;
    }

    public ModelFinalLogBuilder appendFinalNumberOfTR() {
        stringBuilder.append(String.format("Number of TR: %d\n", evaluator.numberOfTR(population)));
        return this;
    }

    public ModelFinalLogBuilder appendFinalNumberOfMWF() {
        stringBuilder.append(String.format("Number of MWF: %d\n", evaluator.numberOfMWF(population)));
        return this;
    }

    public ModelFinalLogBuilder appendFinalNumberOfUniqueSections() {
        int numMissingSec = evaluator.numberOfMissingSections(population);
        int numUniqueSec = population.getHypothesisList().size() - numMissingSec;
        stringBuilder.append(String.format("Number of unique sections: %d\n", numUniqueSec));
        return this;
    }

    public ModelFinalLogBuilder appendFinalNumberOfMissingSections() {
        int numMissingSec = evaluator.numberOfMissingSections(population);
        stringBuilder.append(String.format("Number of missing sections: %d\n", numMissingSec));
        return this;
    }

    public ModelFinalLogBuilder appendFinalNumberOfProfessors() {
        int numMissingProf = evaluator.numberOfMissingProfessors(population);
        int numProf = population.getHypothesisList().size() - numMissingProf;
        stringBuilder.append(String.format("Number of professors: %d\n", numProf));
        return this;
    }

    public ModelFinalLogBuilder appendFinalNumberOfMissingProfessors() {
        int numMissingProf = evaluator.numberOfMissingProfessors(population);
        stringBuilder.append(String.format("Number of missing professors: %d\n", numMissingProf));
        return this;
    }

    public void log(String msg) {
        System.out.println(msg);
    }

    public void printBuiltLog() {
        log(stringBuilder.toString());
    }

}
