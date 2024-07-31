package onl.devin.geneticsai;

import onl.devin.geneticsai.genetics.representation.Hypothesis;
import onl.devin.geneticsai.implementation.category.CourseSection;

import java.util.Collections;
import java.util.List;

public class HypothesisSorter {

    public static void sort(List<Hypothesis> hypothesisList) {
        Collections.sort(hypothesisList, (o1, o2) -> {
            int sec1 = o1.getCategory(CourseSection.class).getSectionAbsolute();
            int sec2 = o2.getCategory(CourseSection.class).getSectionAbsolute();
            return Integer.compare(sec1, sec2);
        });
    }

}
