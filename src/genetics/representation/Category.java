package genetics.representation;

public abstract class Category {

    private BitString bitString;

    public BitString getBitString() {
        return bitString;
    }

    public void setBitString(BitString bitString) {
        this.bitString = bitString;
    }

    public abstract int getOutcomeCount();

}
