package omscs.edtech.ui.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
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
    TabPane parentTabPane;

    @FXML
    protected void initialize(){

        settingsDataAdapter = new SettingsDataAdapter();

        parentBox.addEventFilter(InjectModelEvent.INJECT_MODEL,
                new EventHandler<InjectModelEvent>() {
                    @Override
                    public void handle(InjectModelEvent injectModelEvent) {

                        if(injectModelEvent.getModelToInject() instanceof EmailCustomToolbar) {
                            EmailCustomToolbar customToolbar = (EmailCustomToolbar) injectModelEvent.getModelToInject();
                            customToolbar.setEditor(htmlEditorBox);
                            setCurrentEmailTemplate(settingsDataAdapter.getTemplate(customToolbar.getEmailName()));
                            customWidgetToolBar.getItems().add(new Label("Add: "));
                            customWidgetToolBar.getItems().addAll(customToolbar.getCustomButtons());
                        }else if(injectModelEvent.getModelToInject() instanceof TabPane){
                            parentTabPane = (TabPane)injectModelEvent.getModelToInject();

                            parentTabPane.widthProperty().addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                    Double width = newValue.doubleValue();
                                    htmlEditorBox.setPrefWidth(width);
                                }
                            });

                            parentTabPane.heightProperty().addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                    Double height = newValue.doubleValue();
                                    htmlEditorBox.setPrefHeight(height - 180);
                                }
                            });

                        }
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
