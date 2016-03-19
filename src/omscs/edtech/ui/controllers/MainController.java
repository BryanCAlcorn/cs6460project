package omscs.edtech.ui.controllers;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import omscs.edtech.ui.events.InjectClassModelEvent;
import omscs.edtech.ui.interfaces.ClassDataAdapter;
import omscs.edtech.ui.models.ClassModel;

import java.io.IOException;


public class MainController {

    @FXML
    private Pane mainViewPane;
    private Pane currentView;

    @FXML
    protected void initialize(){
        showGradeAssignmentsView(null);
    }

    @FXML
    protected void showGradeAssignmentsView(ActionEvent event){
        ClassDataAdapter classDataAdapter = new ClassDataAdapter();
        Pane pane = new Pane();
        TabPane classesTab = new TabPane();
        //Switch with a foreach loop to add each class:
        for (ClassModel classModel : classDataAdapter.getAllClasses()) {
            Tab classTab = new Tab(classModel.toString());
            classTab.setClosable(false);

            //Load the grade assignments view for first tab:
            final Pane workspace = getWorkspace(ControllerConstants.GRADE_ASSIGNMENTS_VIEW);
            Event.fireEvent(workspace, new InjectClassModelEvent(classModel, workspace));

            workspace.setPrefWidth(mainViewPane.getPrefWidth());
            workspace.setPrefHeight(mainViewPane.getPrefHeight());

            //Set width and height properties:
            pane.widthProperty().addListener(
                    new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                            Double width = newValue.doubleValue();
                            workspace.setPrefWidth(width);
                        }
                    }
            );

            pane.heightProperty().addListener(
                    new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                            Double height = newValue.doubleValue();
                            workspace.setPrefHeight(height);
                        }
                    }
            );

            classTab.contentProperty().setValue(workspace);
            classesTab.getTabs().add(classTab);
        }

        //Add event hook for tab switching?
        pane.getChildren().add(classesTab);
        loadView(pane);
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

    private void loadView(Pane view){
        if(currentView != null) {
            mainViewPane.getChildren().remove(currentView);
            currentView = null;
        }

        currentView = view;
        currentView.setPrefWidth(mainViewPane.getWidth());
        currentView.setPrefHeight(mainViewPane.getHeight());

        //Set width and height properties:
        mainViewPane.widthProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double width = newValue.doubleValue();
                        currentView.setPrefWidth(width);
                    }
                }
        );

        mainViewPane.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double height = newValue.doubleValue();
                        currentView.setPrefHeight(height);
                    }
                }
        );

        mainViewPane.getChildren().add(0, currentView);
    }

    private Pane getWorkspace(String workspaceName){
        Pane workspace;
        try {
            workspace = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + workspaceName));
        }catch (IOException ex){
            workspace = new Pane();
            workspace.getChildren().add(new Label("Error loading workspace: " + workspaceName + "\n" + ex.getMessage()));
        }
        return workspace;
    }
}
