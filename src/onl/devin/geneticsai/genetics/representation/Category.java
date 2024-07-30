package onl.devin.geneticsai.genetics.representation;

import java.util.List;

public abstract class Category {

    private BitString bitString;

    public BitString getBitString() {
        return bitString;
    }

    public void setBitString(BitString bitString) {
        this.bitString = bitString;
    }

    public Category getCategoryWithBitString(BitString bitString) {
        for (Category category : getOutcomeList()) {
            if (category.getBitString().getBitString().equalsIgnoreCase(bitString.getBitString())) {
                return category;
            }
        }
        return null;
    }

    public abstract int getOutcomeCount();

    public abstract List<Category> getOutcomeList();

}
