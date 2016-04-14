package omscs.edtech.ui.controllers;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import omscs.edtech.ui.controls.EmailCustomToolbar;
import omscs.edtech.ui.events.InjectModelEvent;
import omscs.edtech.ui.interfaces.SettingsDataAdapter;
import omscs.edtech.ui.models.EmailTemplateModel;

public class EmailEditorController {

    @FXML
    private VBox parentBox;

    @FXML
    private HTMLEditor htmlEditorBox;

    @FXML
    private ToolBar customWidgetToolBar;

    private SettingsDataAdapter settingsDataAdapter;
    private EmailTemplateModel currentEmailTemplate;

    @FXML
    protected void initialize(){

        settingsDataAdapter = new SettingsDataAdapter();

        parentBox.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double height = newValue.doubleValue();
                        htmlEditorBox.setPrefHeight(height - 80);
                    }
                }
        );

        parentBox.addEventFilter(InjectModelEvent.INJECT_MODEL,
                new EventHandler<InjectModelEvent>() {
                    @Override
                    public void handle(InjectModelEvent injectModelEvent) {
                        EmailCustomToolbar customToolbar = (EmailCustomToolbar)injectModelEvent.getModelToInject();
                        setCurrentEmailTemplate(settingsDataAdapter.getTemplate(customToolbar.getEmailName()));
                        customWidgetToolBar.getItems().addAll(customToolbar.getCustomButtons());
                    }
                });
    }

    @FXML
    protected void saveTemplate_Click(ActionEvent event) {
        currentEmailTemplate.setTemplateProperty(htmlEditorBox.getHtmlText());
        settingsDataAdapter.saveTemplate(currentEmailTemplate);
    }

    private void setCurrentEmailTemplate(EmailTemplateModel model){

        if(currentEmailTemplate != null){
            htmlEditorBox.setHtmlText("");
        }

        currentEmailTemplate = model;

        if(currentEmailTemplate != null){
            htmlEditorBox.setHtmlText(currentEmailTemplate.getTemplateProperty());
        }
    }
}
