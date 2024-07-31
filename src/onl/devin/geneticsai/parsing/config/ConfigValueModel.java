package onl.devin.geneticsai.parsing.config;

import onl.devin.geneticsai.implementation.model.*;

public enum ConfigValueModel {

    BASIC(new BasicModel()),
    BASIC_TEACHER(new BasicTeacherModel()),
    TEACHER_PREFERENCE(new TeacherPreferenceModel()),
    TEACHER_DIFFERENCE(new TeacherDifferenceModel()),
    TEACHER_SATISFACTION(new TeacherSatisfactionModel()),
    TEACHER_TRICRITERIA(new TeacherTricriteriaModel()),
    ;

    private Model model;

    ConfigValueModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
