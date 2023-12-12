package genetics.procedure;

import genetics.representation.BitString;
import genetics.representation.Category;
import genetics.representation.Population;

import java.util.List;

public class CrossoverMaskGenerator {

    public static BitString byEachCategory(Population population) {
        List<Category> categoryList = population.getHypothesisList().get(0).getCategories();

        char current = '0';
        String bitString = "";
        for (int i = 0; i < categoryList.size(); i++) {
            int digitCount = categoryList.get(i).getBitString().getDigitCount();

            for (int j = 0; j < digitCount; j++) {
                bitString += current;
            }

            if (current == '0') {
                current = '1';
            } else {
                current = '0';
            }
        }

        return new BitString(bitString);
    }

    public static BitString singlePointCrossover(Population population) {
        List<Category> categoryList = population.getHypothesisList().get(0).getCategories();

        int digitCount = 0;
        for (int i = 0; i < categoryList.size(); i++) {
            digitCount += categoryList.get(i).getBitString().getDigitCount();
        }
        String mask = "";
        for (int i = 0; i < digitCount / 2; i++) {
            mask += "1";
        }
        for (int i = digitCount / 2 + 1; i <= digitCount; i++) {
            mask += "0";
        }
        return new BitString(mask);
    }

}
