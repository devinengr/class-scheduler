package onl.devin.geneticsai.implementation.category;

import onl.devin.geneticsai.genetics.representation.Category;
import onl.devin.geneticsai.genetics.util.BitStringGenerator;
import onl.devin.geneticsai.implementation.util.TypePref;

import java.util.ArrayList;
import java.util.List;

public class CourseSection extends Category implements Comparable<CourseSection> {

    private static List<CourseSection> sectionList = new ArrayList<>();

    private int sectionAbsolute;
    private int courseNumber;
    private int sectionRelative;
    private int numberOfCredits;
    private TypePref typePref;

    public CourseSection() {
        sectionList.add(this);
    }

    /**
     * call after initializing all objects
     */
    public static void initializeBitStringData() {
        BitStringGenerator bitGen = new BitStringGenerator(sectionList.get(0));
        sectionList.forEach(section -> {
            section.setBitString(bitGen.next());
        });
    }

    @Override
    public int getOutcomeCount() {
        return sectionList.size();
    }

    @Override
    public List<Category> getOutcomeList() {
        return new ArrayList<>(sectionList);
    }

    public static CourseSection getSection(int sectionAbsolute) {
        for (CourseSection section : sectionList) {
            if (section.getSectionAbsolute() == sectionAbsolute) {
                return section;
            }
        }
        CourseSection newCourse = new CourseSection();
        newCourse.setSectionAbsolute(sectionAbsolute);
        return newCourse;
    }

    public static int getNumberOfCourseSections() {
        return sectionList.size();
    }

    public int getSectionAbsolute() {
        return sectionAbsolute;
    }

    public void setSectionAbsolute(int sectionAbsolute) {
        this.sectionAbsolute = sectionAbsolute;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public int getSectionRelative() {
        return sectionRelative;
    }

    public void setSectionRelative(int sectionRelative) {
        this.sectionRelative = sectionRelative;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public TypePref getTypePref() {
        return typePref;
    }

    public void setTypePref(TypePref typePref) {
        this.typePref = typePref;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof CourseSection) {
            CourseSection other = (CourseSection) o;
            return other.getSectionAbsolute() == sectionAbsolute;
        }
        return false;
    }

    @Override
    public int compareTo(CourseSection o) {
        return Integer.compare(sectionAbsolute, o.sectionAbsolute);
    }
}
