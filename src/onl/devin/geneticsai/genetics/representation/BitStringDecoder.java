package onl.devin.geneticsai.genetics.representation;

public class BitStringDecoder {

    public Hypothesis decodeFullBitString(Hypothesis reference, BitString fullBitString) {
        String bitString = fullBitString.getBitString();
        int stringStartInc = 0;
        Hypothesis hypothesis = new Hypothesis();
        hypothesis.setFullBitString(fullBitString);
        for (Category category : reference.getCategories()) {
            int numberOfDigits = category.getBitString().getBitString().length();
            int stringEndExc = stringStartInc + numberOfDigits;
            String bit = bitString.substring(stringStartInc, stringEndExc);
            Category match = category.getCategoryWithBitString(new BitString(bit));
            hypothesis.addCategory(match);
        }
        return hypothesis;
    }

}
