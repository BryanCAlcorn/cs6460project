package omscs.edtech.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.apache.derby.iapi.services.classfile.CONSTANT_Index_info;

import java.io.IOException;


public class MainController {

    @FXML
    private AnchorPane mainViewPane;

    private Parent currentView;

    @FXML
    protected void initialize(){
        loadView(ControllerConstants.GRADE_ASSIGNMENTS_VIEW);
    }

    @FXML
    protected void showGradeAssignmentsView(ActionEvent event){
        loadView(ControllerConstants.GRADE_ASSIGNMENTS_VIEW);
    }

    @FXML
    protected void showCreateAssignmentsView(ActionEvent event){
        loadView(ControllerConstants.CREATE_ASSIGNMENTS_VIEW);
    }

    @FXML
    protected void showAddClassesView(ActionEvent event){
        loadView(ControllerConstants.ADD_CLASSES_VIEW);
    }

    @FXML
    protected void showSettingsView(ActionEvent event){
        loadView(ControllerConstants.SETTINGS_VIEW);
    }

    private void loadView(String viewName){
        try {
            if(currentView != null) {
                mainViewPane.getChildren().remove(currentView);
            }
            currentView = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + viewName));
            mainViewPane.getChildren().add(0, currentView);
        }catch (IOException ex){
        }
    }
}
