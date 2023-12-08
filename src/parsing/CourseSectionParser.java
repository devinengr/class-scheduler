package parsing;

import implementation.category.CourseSection;
import implementation.util.TypePref;

public class CourseSectionParser implements FileParser {

    @Override
    public void parseLine(String line, int lineNumber) {
        if (lineNumber == 1) {
            return;
        }

        String[] split = line.split(",");
        int sectionAbsolute = Integer.parseInt(split[0]);
        int courseNumber = Integer.parseInt(split[1]);
        int sectionRelative = Integer.parseInt(split[2]);
        int numberOfCredits = Integer.parseInt(split[3]);
        int appliedOrPure = Integer.parseInt(split[4]);
        TypePref typePref;
        if (appliedOrPure == 1) {
            typePref = TypePref.APPLIED;
        } else {
            typePref = TypePref.PURE;
        }

        CourseSection section = CourseSection.getSection(sectionAbsolute);
        section.setCourseNumber(courseNumber);
        section.setSectionRelative(sectionRelative);
        section.setNumberOfCredits(numberOfCredits);
        section.setTypePref(typePref);
    }

}
