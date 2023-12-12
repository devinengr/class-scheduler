package genetics.representation;

import java.util.List;

public class BitString {

    private String bitString;
    private int outcomeCount;
    private int digitCount;

    public BitString(String bitString) {
        this.bitString = bitString;
        this.digitCount = bitString.length();
        this.outcomeCount = -1;
    }

    public BitString(List<Category> categoryList) {
        this.bitString = "";
        for (Category category : categoryList) {
            bitString += category.getBitString().getBitString();
        }
        this.digitCount = bitString.length();
        this.outcomeCount = -1;
    }

    public BitString(Category category) {
        this.outcomeCount = category.getOutcomeCount();
        this.digitCount = getNumberOfDigitsInBitString(outcomeCount);
        this.bitString = String.format("%0" + digitCount + "d", 0);
    }

    private BitString(String bitString, int outcomeCount, int digitCount) {
        this.bitString = bitString;
        this.outcomeCount = outcomeCount;
        this.digitCount = digitCount;
    }

    public int getOutcomeCount() {
        return outcomeCount;
    }

    public int getDigitCount() {
        return digitCount;
    }

    public static BitString getNextBitString(BitString lastBitString) {
        String binary = lastBitString.getBitString();
        String result = Integer.toBinaryString(Integer.parseInt(binary, 2) + 1);
        String formatter = "%" + lastBitString.getDigitCount() + "s";
        String res = String.format(formatter, result).replace(' ', '0');
        return new BitString(res,
                lastBitString.getOutcomeCount(),
                lastBitString.getDigitCount());
    }

    public static int getNumberOfDigitsInBitString(int number) {
        return (int) Math.floor(Math.log(number) / Math.log(2)) + 1;
    }

    public String getBitString() {
        return bitString;
    }

}
