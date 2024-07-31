package onl.devin.geneticsai.parsing.config;

import onl.devin.geneticsai.implementation.model.BasicModel;
import onl.devin.geneticsai.implementation.model.Model;

public enum ConfigKeyModel {

    MODEL(new BasicModel()),
    ;

    private Model model;

    ConfigKeyModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
