package constraints;

public class ViolationCount {

    private int violationCountAcceptable;
    private int violationCountUnacceptable;

    public ViolationCount() {
        this.violationCountAcceptable = 0;
        this.violationCountUnacceptable = 0;
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

}
