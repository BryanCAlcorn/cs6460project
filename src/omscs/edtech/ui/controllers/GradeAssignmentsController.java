package omscs.edtech.ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import omscs.edtech.TessAPI.TesseractAPI;
import omscs.edtech.ui.events.InjectModelEvent;
import omscs.edtech.ui.interfaces.GradesDataAdapter;
import omscs.edtech.ui.models.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GradeAssignmentsController {
    @FXML
    private HBox parentBox;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;
    @FXML
    private TabPane paneAssignment;
    @FXML
    private VBox assignmentDescrBox;

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
    private OCRFileModel currentOCRFile;
    private GradesDataAdapter gradesDataAdapter;

    @FXML
    protected void initialize(){
        gradesDataAdapter = new GradesDataAdapter();
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
                        tblStudentGrades.setPrefHeight(height - 125);
                        paneAssignment.setPrefHeight(height - 227 - assignmentDescrBox.getPrefHeight());
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

        tblStudentGrades.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StudentAssignmentModel>() {
            @Override
            public void changed(ObservableValue<? extends StudentAssignmentModel> observableValue, StudentAssignmentModel studentAssignmentModel, StudentAssignmentModel t1) {
                setCurrentStudent(t1);
            }
        });

        comboAssignments.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setCurrentAssignment(comboAssignments.getSelectionModel().getSelectedItem());
            }
        });

        parentBox.addEventFilter(InjectModelEvent.INJECT_MODEL,
                new EventHandler<InjectModelEvent>() {
                    @Override
                    public void handle(InjectModelEvent injectClassModelEvent) {
                        gradeAssignmentsModel = (GradeAssignmentsModel)injectClassModelEvent.getModelToInject();
                        comboAssignments.itemsProperty().bindBidirectional(gradeAssignmentsModel.getAssignmentModelsProperty());
                        ControllerConstants.selectFirstComboItem(comboAssignments);
                    }
                });
    }

    private void setCurrentAssignment(AssignmentModel assignment){
        int itemToSelect = 0;
        if(currentAssignment != null){
            //Unhook events
            lblAssignmentDescription.textProperty().setValue("-----");
            itemToSelect = tblStudentGrades.getSelectionModel().getSelectedIndex();
        }

        currentAssignment = assignment;

        if(currentAssignment != null) {
            lblAssignmentDescription.textProperty().setValue(currentAssignment.toLongString());
            ObservableList<StudentAssignmentModel> studentAssignmentModels = gradeAssignmentsModel.getStudentAssignmentList(currentAssignment);
            tblStudentGrades.setItems(studentAssignmentModels);
            if(!studentAssignmentModels.isEmpty()) {
                tblStudentGrades.getSelectionModel().select(itemToSelect);
            }
        }
    }

    private void setCurrentStudent(StudentAssignmentModel student){
        if(currentStudent != null){
            txtFeedback.textProperty().unbindBidirectional(currentStudent.assignmentFeedbackProperty());
            lblAssignmentText.textProperty().unbind();
        }

        currentStudent = student;

        if(currentStudent != null) {
            txtFeedback.textProperty().bindBidirectional(currentStudent.assignmentFeedbackProperty());
            lblAssignmentText.textProperty().bind(currentStudent.assignmentTextProperty());
            setCurrentOCRFile(gradesDataAdapter.getAssignmentImage(currentStudent));
        }
    }

    private void setCurrentOCRFile(OCRFileModel ocrFile){

        if(currentOCRFile != null){
            lblAssignmentText.textProperty().setValue("-----");
            imgAssignmentImage.imageProperty().setValue(null);
        }

        currentOCRFile = ocrFile;

        if(currentOCRFile != null){
            lblAssignmentText.textProperty().setValue(currentOCRFile.getFileText());
            imgAssignmentImage.imageProperty().setValue(currentOCRFile.getImageProperty());
        }
    }

    @FXML
    protected void importAssignments_Click(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Assignment(s)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TIFF", "*.tiff", "*.tif")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(parentBox.getScene().getWindow());
        if(files != null){
            for(File file : files) {
                OCRFileModel ocrFileModel = gradesDataAdapter.importAssignmentImage(gradeAssignmentsModel.getClassId(), file);
                if (currentStudent != null && ocrFileModel.getStudentId() == currentStudent.getStudentId()) {
                    //Imported for the currently selected student, bind to UI:
                    setCurrentOCRFile(ocrFileModel);
                }
                if(ocrFileModel.getStudentId() == null){
                    //Student was unknown, ask to find:
                    StudentAssignmentModel studentAssignmentModel = showPickStudentDialog(ocrFileModel);
                    if(studentAssignmentModel != null){
                        ocrFileModel.setStudentId(studentAssignmentModel.getStudentId());
                        gradesDataAdapter.saveOCRFile(ocrFileModel);
                    }
                }
            }
        }
    }

    private StudentAssignmentModel showPickStudentDialog(OCRFileModel ocrFileModel){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parentBox.getScene().getWindow());
        dialog.setTitle("Match Student and Assignment");
        VBox dialogVbox = new VBox(20);

        dialogVbox.getChildren().add(new Text("We could not match a student with this assignment\n Please select the student:"));

        final ComboBox<StudentAssignmentModel> studentModelComboBox =
                new ComboBox<>(gradeAssignmentsModel.getStudentAssignmentList(currentAssignment));
        dialogVbox.getChildren().add(studentModelComboBox);

        if(ocrFileModel != null) {
            ImageView assignmentImage = new ImageView(ocrFileModel.getImageProperty());
            assignmentImage.setFitHeight(600);
            assignmentImage.setFitWidth(800);
            dialogVbox.getChildren().add(assignmentImage);
        }

        HBox buttonBox = new HBox();
        dialogVbox.getChildren().add(buttonBox);

        Button selectButton = new Button("Select");
        buttonBox.getChildren().add(selectButton);
        SelectEventHandler eventHandler = new SelectEventHandler(dialog, studentModelComboBox);
        selectButton.setOnAction(eventHandler);

        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().add(cancelButton);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.close();
            }
        });

        Scene dialogScene = new Scene(dialogVbox, 1000,800);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        return eventHandler.getStudentAssignmentModel();
    }

    private class SelectEventHandler implements EventHandler<ActionEvent>{
        StudentAssignmentModel studentAssignmentModel;
        ComboBox<StudentAssignmentModel> studentAssignmentModelComboBox;
        Stage dialog;

        public SelectEventHandler(Stage stage, ComboBox<StudentAssignmentModel> comboBox){
            dialog = stage;
            studentAssignmentModelComboBox = comboBox;
            studentAssignmentModel = null;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            studentAssignmentModel = studentAssignmentModelComboBox.getSelectionModel().getSelectedItem();
            dialog.close();
        }

        public StudentAssignmentModel getStudentAssignmentModel() {
            return studentAssignmentModel;
        }
    }

    @FXML
    protected void btnSaveGrades_Click(ActionEvent event){
         gradesDataAdapter.saveGrades(tblStudentGrades.getItems());
    }

    @FXML
    protected void btnSendAllFeedback_Click(ActionEvent event) throws Exception{

    }

    @FXML
    protected void sendFeedback_Click(ActionEvent event){

    }
}
