package representation;

public abstract class Category {

    private int outcomeCount;
    private boolean binary;

    public Category(int outcomeCount, boolean binary) {
        this.outcomeCount = outcomeCount;
        this.binary = binary;
    }

}
