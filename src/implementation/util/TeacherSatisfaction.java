package implementation.util;

import implementation.category.CourseSection;

import java.util.HashMap;
import java.util.Map;

public class TeacherSatisfaction {

    private TeacherPreference teacherPreference;
    private Map<CourseSection, Integer> courseSectionPref;

    public TeacherSatisfaction() {
        this.courseSectionPref = new HashMap<>();
    }

    public TeacherPreference getTeacherPreference() {
        return teacherPreference;
    }

    public void setTeacherPreference(TeacherPreference teacherPreference) {
        this.teacherPreference = teacherPreference;
    }

    /**
     * @param preferenceLevel 0 - most preferred
     *                        5 - least preferred
     * @param courseSection
     */
    public void setCourseSectionPref(int preferenceLevel, CourseSection courseSection) {
        courseSectionPref.put(courseSection, preferenceLevel);
    }

    public int getPreferenceLevelForCourseSection(CourseSection courseSection) {
        return courseSectionPref.get(courseSection);
    }

}
