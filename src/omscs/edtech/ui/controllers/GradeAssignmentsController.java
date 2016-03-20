package omscs.edtech.ui.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.ui.events.InjectModelEvent;
import omscs.edtech.ui.models.*;

public class GradeAssignmentsController {
    @FXML
    private HBox parentBox;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;

    @FXML
    private ComboBox<AssignmentModel> comboAssignments;

    @FXML
    private TableView<StudentAssignmentModel> tblStudentGrades;
    @FXML
    private TableColumn<StudentAssignmentModel, StringProperty> colStudentName;
    @FXML
    private TableColumn<StudentAssignmentModel, DoubleProperty> colAssignmentGrade;

    @FXML
    private Label lblAssignmentDescription;
    @FXML
    private Label lblAssignmentText;
    @FXML
    private ImageView imgAssignmentImage;
    @FXML
    private TextArea txtFeedback;

    private GradeAssignmentsModel gradeAssignmentsModel;
    private AssignmentModel currentAssignment;
    private StudentAssignmentModel currentStudent;

    @FXML
    protected void initialize(){
        //Set width and height properties:
        parentBox.widthProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double halfWidth = newValue.doubleValue() / 2.0;
                        leftBox.setPrefWidth(halfWidth);
                        rightBox.setPrefWidth(halfWidth);
                    }
                }
        );

        parentBox.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double height = newValue.doubleValue();
                        leftBox.setPrefHeight(height);
                        rightBox.setPrefHeight(height);
                    }
                }
        );

        parentBox.addEventFilter(InjectModelEvent.INJECT_MODEL,
                new EventHandler<InjectModelEvent>() {
                    @Override
                    public void handle(InjectModelEvent injectClassModelEvent) {
                        gradeAssignmentsModel = (GradeAssignmentsModel)injectClassModelEvent.getModelToInject();
                        //Do anything else needed to load the class model!
                        //Bind Students to grid? Wait until assignment chosen?
                        //Bind Assignments to drop down.
                        comboAssignments.itemsProperty().bindBidirectional(gradeAssignmentsModel.getAssignmentModelsProperty());
                        comboAssignments.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                setCurrentAssignment(comboAssignments.getSelectionModel().getSelectedItem());
                            }
                        });
                    }
                });
    }

    private void setCurrentAssignment(AssignmentModel assignment){
        if(currentAssignment != null){
            //Unhook events
            lblAssignmentDescription.textProperty().unbind();
        }

        currentAssignment = assignment;

        lblAssignmentDescription.textProperty().bind(currentAssignment.descriptionProperty());
        tblStudentGrades.setItems(gradeAssignmentsModel.getStudentAssignmentList(currentAssignment));
    }

    @FXML
    protected void importAssignments_Click(ActionEvent event){

    }

    @FXML
    protected void btnSaveGrades_Click(ActionEvent event){

    }

    @FXML
    protected void btnSendAllFeedback_Click(ActionEvent event){

    }

    @FXML
    protected void sendFeedback_Click(ActionEvent event){

    }
}
