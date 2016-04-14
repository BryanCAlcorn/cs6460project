package omscs.edtech.ui.interfaces;

import omscs.edtech.db.interfaces.EmailTemplateDataConnector;
import omscs.edtech.db.model.EmailTemplate;
import omscs.edtech.ui.models.EmailTemplateModel;

public class SettingsDataAdapter {

    private EmailTemplateDataConnector emailTemplateDataConnector;

    public SettingsDataAdapter(){
        emailTemplateDataConnector = new EmailTemplateDataConnector();
    }

    public EmailTemplateModel getTemplate(String templateName){
        return toModel(emailTemplateDataConnector.getTemplateByName(templateName));
    }

    public void saveTemplate(EmailTemplateModel model){
        emailTemplateDataConnector.saveTemplate(fromModel(model));
    }

    private EmailTemplateModel toModel(EmailTemplate template){
        EmailTemplateModel model = new EmailTemplateModel(template.getName());
        model.setTemplateId(template.getId());
        model.setTemplateProperty(template.getTemplate());

        return model;
    }

    private EmailTemplate fromModel(EmailTemplateModel model){
        EmailTemplate template = new EmailTemplate(model.getTemplateId());
        template.setName(model.getTemplateName());
        template.setTemplate(model.getTemplateProperty());

        return template;
    }
}
