package implementation.category;

import genetics.representation.Category;
import genetics.util.BitStringGenerator;
import implementation.util.TeacherPreference;
import implementation.util.TeacherSatisfaction;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Category {

    private static List<Professor> professorList = new ArrayList<>();

    private int teacherID;
    private TeacherPreference teacherPreference;
    private TeacherSatisfaction teacherSatisfaction;

    public Professor() {
        professorList.add(this);
    }

    /**
     * call after initializing all objects
     */
    public static void initializeBitStringData() {
        BitStringGenerator bitGen = new BitStringGenerator(professorList.get(0));
        professorList.forEach(prof -> {
            prof.setBitString(bitGen.next());
        });
    }

    @Override
    public int getOutcomeCount() {
        return professorList.size();
    }

    @Override
    public List<Category> getOutcomeList() {
        return new ArrayList<>(professorList);
    }

    public static int getNumberOfProfessors() {
        return professorList.size();
    }

    public static Professor getProfessorByID(int id) {
        for (Professor prof : professorList) {
            if (prof.getTeacherID() == id) {
                return prof;
            }
        }
        Professor newProf = new Professor();
        newProf.setTeacherID(id);
        return newProf;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public TeacherPreference getTeacherPreference() {
        return teacherPreference;
    }

    public void setTeacherPreference(TeacherPreference teacherPreference) {
        this.teacherPreference = teacherPreference;
    }

    public TeacherSatisfaction getTeacherSatisfaction() {
        return teacherSatisfaction;
    }

    public void setTeacherSatisfaction(TeacherSatisfaction teacherSatisfaction) {
        this.teacherSatisfaction = teacherSatisfaction;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Professor) {
            Professor other = (Professor) o;
            return other.getTeacherID() == teacherID;
        }
        return false;
    }

}
