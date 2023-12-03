package genetics.representation;

public class BitString {

    private String bitString;
    private Category category;

    public BitString(String bitString, Category category) {
        this.category = category;
    }

    public String getBitString() {
        return bitString;
    }

    public Category getCategory() {
        return category;
    }

}
