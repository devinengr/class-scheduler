package genetics.util;

import genetics.representation.BitString;
import genetics.representation.Category;

public class BitStringGenerator {

    private Category category;
    private BitString lastBitString;
    private boolean firstRun = true;

    public BitStringGenerator(Category category) {
        this.category = category;
    }

    public BitString next() {
        if (firstRun) {
            firstRun = false;
            lastBitString = new BitString(category);
            return lastBitString;
        }
        lastBitString = BitString.getNextBitString(lastBitString);
        return lastBitString;
    }

}
