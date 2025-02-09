package onl.devin.geneticsai.constraints;

public class ViolationCount {

    private int violationCountAcceptable;
    private int violationCountUnacceptable;
    private int violationLevelTeacherSatisfaction;

    public ViolationCount() {
        this.violationCountAcceptable = 0;
        this.violationCountUnacceptable = 0;
        this.violationLevelTeacherSatisfaction = 0;
    }

    public void reset() {
        this.violationCountAcceptable = 0;
        this.violationCountUnacceptable = 0;
        this.violationLevelTeacherSatisfaction = 0;
    }

    public void addViolationAcceptable() {
        violationCountAcceptable++;
    }

    public void addViolationUnacceptable() {
        violationCountUnacceptable++;
    }

    public int getViolationsAcceptable() {
        return violationCountAcceptable;
    }

    public int getViolationsUnacceptable() {
        return violationCountUnacceptable;
    }

    public int getViolationLevelTeacherSatisfaction() {
        return violationLevelTeacherSatisfaction;
    }

    public void addViolationLevelTeacherSatisfaction() {
        violationLevelTeacherSatisfaction++;
    }

}
