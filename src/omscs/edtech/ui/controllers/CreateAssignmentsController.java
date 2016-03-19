package omscs.edtech.ui.controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import omscs.edtech.ui.controls.IntegerField;
import omscs.edtech.ui.interfaces.AssignmentDataAdapter;
import omscs.edtech.ui.models.AssignmentModel;
import omscs.edtech.ui.models.ClassAssignmentModel;

public class CreateAssignmentsController {

    @FXML
    private TextField txtAssignmentName;
    @FXML
    private IntegerField txtMaxPoints;
    @FXML
    private TextArea txtDescription;
    @FXML
    private ListView<ClassAssignmentModel> listClasses;
    @FXML
    private ComboBox<AssignmentModel> comboAssignments;

    AssignmentDataAdapter assignmentDataAdapter;
    AssignmentModel currentAssignment;

    @FXML
    protected void initialize(){

        listClasses.setCellFactory(CheckBoxListCell.forListView(new Callback<ClassAssignmentModel, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(ClassAssignmentModel classModel) {
                return classModel.isAssignedProperty();
            }
        }));

        assignmentDataAdapter = new AssignmentDataAdapter();
        for(ClassAssignmentModel classAssignmentModel : assignmentDataAdapter.getClassAssignmentModels()){
            listClasses.getItems().add(classAssignmentModel);
        }

        comboAssignments.itemsProperty().bindBidirectional(assignmentDataAdapter.getAssignmentsProperty());
        comboAssignments.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setCurrentAssignment(comboAssignments.getSelectionModel().getSelectedItem());
            }
        });

        //Have a currentAssignment set from the beginning.
        newAssignment_Click(null);
    }

    @FXML
    protected void newAssignment_Click(ActionEvent event) {
        AssignmentModel assignmentModel = new AssignmentModel();
        setCurrentAssignment(assignmentModel);
    }

    @FXML
    protected void saveAssignment_Click(ActionEvent event) {

        if(currentAssignment != null) {
            for (ClassAssignmentModel listClass : listClasses.getItems()) {
                if (listClass.getIsAssigned() && !currentAssignment.hasAssignedClass(listClass)) {
                    currentAssignment.assignToClass(listClass);
                }
            }

            if(!assignmentDataAdapter.containsAssignment(currentAssignment)){
                assignmentDataAdapter.addAssignment(currentAssignment);
            }
        }

    }

    private void setCurrentAssignment(AssignmentModel assignment){
        if(currentAssignment != null){
            txtAssignmentName.textProperty().unbindBidirectional(currentAssignment.nameProperty());
            txtMaxPoints.integerProperty().unbindBidirectional(currentAssignment.maxPointsProperty());
            txtDescription.textProperty().unbindBidirectional(currentAssignment.descriptionProperty());
        }

        currentAssignment = assignment;

        txtAssignmentName.textProperty().bindBidirectional(currentAssignment.nameProperty());
        txtMaxPoints.integerProperty().bindBidirectional(currentAssignment.maxPointsProperty());
        txtDescription.textProperty().bindBidirectional(currentAssignment.descriptionProperty());

        for(ClassAssignmentModel listClass : listClasses.getItems()){
            listClass.setIsAssigned(false);
            for (ClassAssignmentModel assignedClass : currentAssignment.getAssignedClasses()) {
                if (listClass.getClassName().equals(assignedClass.getClassName())) {
                    listClass.setIsAssigned(true);
                    break;
                }
            }
        }
    }
}
