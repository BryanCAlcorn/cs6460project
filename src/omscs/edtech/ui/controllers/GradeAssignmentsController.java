package omscs.edtech.ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;
import omscs.edtech.ui.events.InjectModelEvent;
import omscs.edtech.ui.models.*;

import java.io.File;
import java.util.List;

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
    private TableColumn<StudentAssignmentModel, String> colStudentName;
    @FXML
    private TableColumn<StudentAssignmentModel, Number> colAssignmentGrade;

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

        tblStudentGrades.setEditable(true);
        colStudentName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentAssignmentModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentAssignmentModel, String> studentModel) {
                return studentModel.getValue().getStudentNameProperty();
            }
        });

        colAssignmentGrade.setEditable(true);
        colAssignmentGrade.setCellFactory(new Callback<TableColumn<StudentAssignmentModel, Number>, TableCell<StudentAssignmentModel, Number>>() {
            @Override
            public TableCell<StudentAssignmentModel, Number> call(final TableColumn<StudentAssignmentModel, Number> studentAssignmentModelDoubleTableColumn) {
                return new TextFieldTableCell<StudentAssignmentModel, Number>(new StringConverter<Number>() {
                    @Override
                    public String toString(Number number) {
                        return number.toString();
                    }

                    @Override
                    public Number fromString(final String s) {
                        if(s.matches("([0-9])*(\\.)*([0-9])*")) {
                            return new Number() {
                                @Override
                                public int intValue() {
                                    return Integer.getInteger(s);
                                }

                                @Override
                                public long longValue() {
                                    return Long.getLong(s);
                                }

                                @Override
                                public float floatValue() {
                                    return Float.valueOf(s);
                                }

                                @Override
                                public double doubleValue() {
                                    return Double.valueOf(s);
                                }
                            };
                        }else {
                            return 0.0;
                        }
                    }
                });
            }
        });

        colAssignmentGrade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentAssignmentModel, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<StudentAssignmentModel, Number> studentAssignmentModel) {
                return studentAssignmentModel.getValue().studentGradeProperty();
            }
        });

        colAssignmentGrade.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<StudentAssignmentModel, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<StudentAssignmentModel, Number> studentAssignmentModel) {
                studentAssignmentModel.getTableView().getItems().get(
                        studentAssignmentModel.getTablePosition().getRow())
                        .setStudentGrade(studentAssignmentModel.getNewValue());
            }
        });

        parentBox.addEventFilter(InjectModelEvent.INJECT_MODEL,
                new EventHandler<InjectModelEvent>() {
                    @Override
                    public void handle(InjectModelEvent injectClassModelEvent) {
                        gradeAssignmentsModel = (GradeAssignmentsModel)injectClassModelEvent.getModelToInject();

                        comboAssignments.itemsProperty().bindBidirectional(gradeAssignmentsModel.getAssignmentModelsProperty());
                        comboAssignments.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                setCurrentAssignment(comboAssignments.getSelectionModel().getSelectedItem());
                            }
                        });
                        ControllerConstants.selectFirstComboItem(comboAssignments);
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
        ObservableList<StudentAssignmentModel> studentAssignmentModels = gradeAssignmentsModel.getStudentAssignmentList(currentAssignment);
        tblStudentGrades.setItems(studentAssignmentModels);
    }

    @FXML
    protected void importAssignments_Click(ActionEvent event){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Assignment(s)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TIFF", "*.tiff"),
                new FileChooser.ExtensionFilter("TIFF", "*.tif")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(parentBox.getScene().getWindow());
        if(files != null){
            for(File file : files) {
                //Send them to the OCR interface
                //If it accepts a list, don't need to loop.
            }
        }
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
