package omscs.edtech.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
        showGradeAssignmentsView(null);
    }

    @FXML
    protected void showGradeAssignmentsView(ActionEvent event){
        TabPane classesTab = new TabPane();
        //Switch with a foreach loop to add each class:
        Tab class1Tab = new Tab("Class 1");
        class1Tab.setClosable(false);
        classesTab.getTabs().add(class1Tab);

        //Load the grade assignments view for first tab:
        Parent workspace = getWorkspace(ControllerConstants.GRADE_ASSIGNMENTS_VIEW);
        class1Tab.contentProperty().setValue(workspace);

        //Add event hook for tab switching?

        loadView(classesTab);
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

    private void loadView(String workspaceName){
        loadView(getWorkspace(workspaceName));
    }

    private void loadView(Parent view){
        if(currentView != null) {
            mainViewPane.getChildren().remove(currentView);
        }

        currentView = view;

        mainViewPane.getChildren().add(0, currentView);
    }

    private Parent getWorkspace(String workspaceName){
        Parent workspace;
        try {
            workspace = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + workspaceName));
        }catch (IOException ex){
            workspace = new Label("Error loading workspace: " + workspaceName + "\n" + ex.getMessage());
        }
        return workspace;
    }
}
