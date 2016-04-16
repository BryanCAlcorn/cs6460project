package omscs.edtech.ui.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import omscs.edtech.ui.controls.EmailToolbarFactory;
import omscs.edtech.ui.events.InjectModelEvent;

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
            Event.fireEvent(missingAssignmentEMail, new InjectModelEvent<>(EmailToolbarFactory.getMissingAssignmentToolbar(), missingAssignmentEMail));
            Event.fireEvent(missingAssignmentEMail, new InjectModelEvent<>(tabPaneSettings, missingAssignmentEMail));

            feedbackEMail = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + ControllerConstants.EMAIL_EDITOR_VIEW));
            Event.fireEvent(feedbackEMail, new InjectModelEvent<>(EmailToolbarFactory.getFeedbackToolbar(), feedbackEMail));
            Event.fireEvent(feedbackEMail, new InjectModelEvent<>(tabPaneSettings, feedbackEMail));

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

            parentBox.widthProperty().addListener(
                    new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            Double width = newValue.doubleValue();
                            tabPaneSettings.setPrefWidth(width);
                        }
                    }
            );

        }catch (IOException ex){

        }
    }
}
