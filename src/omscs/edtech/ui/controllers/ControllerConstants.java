package omscs.edtech.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

public class ControllerConstants {

    public static final String VIEW_PATH = "/omscs/edtech/ui/views/";
    public static final String APP_TITLE = "CS6460 Project";

    public static final String MAIN_SCREEN_VIEW = "Main Screen.fxml";
    public static final String ADD_CLASSES_VIEW = "Add Classes.fxml";
    public static final String CREATE_ASSIGNMENTS_VIEW = "Create Assignments.fxml";
    public static final String GRADE_ASSIGNMENTS_VIEW = "Grade Assignments.fxml";
    public static final String SETTINGS_VIEW = "Settings.fxml";
    public static final String EMAIL_EDITOR_VIEW = "EMail Editor.fxml";

    public static void selectFirstComboItem(ComboBox comboBox){
        if(comboBox != null && comboBox.getItems() != null && !comboBox.getItems().isEmpty()){
            comboBox.getSelectionModel().select(0);
            comboBox.fireEvent(new ActionEvent());
        }
    }
}
