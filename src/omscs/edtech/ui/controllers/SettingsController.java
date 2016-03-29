package omscs.edtech.ui.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SettingsController {

    @FXML
    private VBox parentBox;
    @FXML
    private TabPane tabPaneSettings;
    @FXML
    private Tab tabMissingAssignments;
    @FXML
    private Tab tabFeedback;

    private Parent missingAssignmentEMail;
    private Parent feedbackEMail;

    @FXML
    protected void initialize(){

        try {
            missingAssignmentEMail = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + ControllerConstants.EMAIL_EDITOR_VIEW));
            feedbackEMail = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + ControllerConstants.EMAIL_EDITOR_VIEW));

            tabMissingAssignments.contentProperty().setValue(missingAssignmentEMail);
            tabFeedback.contentProperty().setValue(feedbackEMail);

            parentBox.heightProperty().addListener(
                    new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                            Double height = newValue.doubleValue();
                            tabPaneSettings.setPrefHeight(height);
                        }
                    }
            );

        }catch (IOException ex){

        }
    }
}
