package onl.devin.geneticsai.parsing;

import onl.devin.geneticsai.implementation.category.CourseSection;
import onl.devin.geneticsai.implementation.category.Professor;
import onl.devin.geneticsai.implementation.util.TeacherPreference;
import onl.devin.geneticsai.implementation.util.TeacherSatisfaction;

public class TeacherSatisfactionParser implements FileParser {

    @Override
    public void parseLine(String line, int lineNumber) {
        // ignore the headers
        if (lineNumber <= 2) {
            return;
        }
        TeacherSatisfaction satisfaction = new TeacherSatisfaction();
        TeacherPreference preference = new TeacherPreference();

        String[] split = line.split(",");
        int teacherID = Integer.parseInt(split[0]);
        int minSections = Integer.parseInt(split[1]);
        int maxSections = Integer.parseInt(split[2]);
        int boardPref = Integer.parseInt(split[3]);
        int timePref = Integer.parseInt(split[4]);
        int daysOfWeek = Integer.parseInt(split[5]);

        TeacherPreferenceParser.setPreferences(preference, boardPref, timePref, daysOfWeek, minSections, maxSections);
        satisfaction.setTeacherPreference(preference);

        Professor prof = Professor.getProfessorByID(teacherID);
        prof.setTeacherSatisfaction(satisfaction);
        prof.setTeacherPreference(preference);

        int splitNext = 6;
        int sectionAbsolute = 1;
        while (splitNext < split.length) {
            satisfaction.setCourseSectionPref(Integer.parseInt(split[splitNext]),
                                              CourseSection.getSection(sectionAbsolute));
            splitNext++;
            sectionAbsolute++;
        }
    }

}
