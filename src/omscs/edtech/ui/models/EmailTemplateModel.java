package omscs.edtech.ui.models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmailTemplateModel {
    private Integer templateId;
    private StringProperty templateProperty;
    private StringProperty templateName;

    public EmailTemplateModel(String templateName) {
        this.templateName = new SimpleStringProperty(templateName);
        this.templateProperty = new SimpleStringProperty();
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getTemplateProperty() {
        return templateProperty.getValue();
    }

    public StringProperty templatePropertyProperty() {
        return templateProperty;
    }

    public void setTemplateProperty(String templateProperty) {
        this.templateProperty.setValue(templateProperty);
    }

    public String getTemplateName() {
        return templateName.getValue();
    }

    public StringProperty templateNameProperty() {
        return templateName;
    }
}
