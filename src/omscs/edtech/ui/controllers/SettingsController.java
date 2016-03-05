package omscs.edtech.ui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;

public class SettingsController {

    @FXML
    private Tab tabMissingAssignments;

    @FXML
    private Tab tabFeedback;

    @FXML
    protected void initialize(){
        try {
            Parent missingAssignmentEMail = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + ControllerConstants.EMAIL_EDITOR_VIEW));
            Parent feedbackEMail = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + ControllerConstants.EMAIL_EDITOR_VIEW));

            tabMissingAssignments.contentProperty().setValue(missingAssignmentEMail);
            tabFeedback.contentProperty().setValue(feedbackEMail);

        }catch (IOException ex){

        }
    }
}
