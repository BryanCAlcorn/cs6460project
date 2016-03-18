package omscs.edtech.ui.controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import omscs.edtech.ui.controls.IntegerField;
import omscs.edtech.ui.interfaces.AssignmentDataAdapter;
import omscs.edtech.ui.models.AssignmentModel;
import omscs.edtech.ui.models.ClassAssignmentModel;
import omscs.edtech.ui.models.ClassModel;

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
    private ComboBox<AssignmentModel> cbAssignments;

    AssignmentModel currentAssignment;

    @FXML
    protected void initialize(){

        listClasses.setCellFactory(CheckBoxListCell.forListView(new Callback<ClassAssignmentModel, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(ClassAssignmentModel classModel) {
                return classModel.isAssignedProperty();
            }
        }));

        AssignmentDataAdapter assignmentData = new AssignmentDataAdapter();
        for(ClassAssignmentModel classAssignmentModel : assignmentData.getClassAssignmentModels()){
            listClasses.getItems().add(classAssignmentModel);
        }

        AssignmentModel assignmentModel = new AssignmentModel();
        assignmentModel.setName("Assignment 1");
        assignmentModel.setDescription("Description!!");
        assignmentModel.setMaxPoints(20);
        assignmentModel.getAssignedClasses().add(new ClassAssignmentModel("C2", true));

        setCurrentAssignment(assignmentModel);
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
            for(ClassAssignmentModel assignedClass : currentAssignment.getAssignedClasses()){
                if(listClass.getClassName().equals(assignedClass.getClassName())){
                    listClass.setIsAssigned(true);
                }else{
                    listClass.setIsAssigned(false);
                }
            }
        }
    }
}
