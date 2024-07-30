package onl.devin.geneticsai.parsing.config;

public enum ConfigValueCSV {

    PATH_TO_CLASSROOMS_DATA("res/simulated_data/sim_classrooms_j.csv"),
    PATH_TO_COURSE_SECTIONS_DATA("res/simulated_data/sim_course_sections_i.csv"),
    PATH_TO_TEACHER_PREFERENCE_DATA("res/simulated_data/sim_teacher_preference.csv"),
    PATH_TO_TEACHER_SATISFACTION_DATA("res/simulated_data/sim_teacher_satisfaction.csv"),
    PATH_TO_TIME_SLOTS_DATA("res/simulated_data/sim_time_slots_k.csv"),
    ;

    private String path;

    ConfigValueCSV(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
