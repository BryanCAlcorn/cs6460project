package omscs.edtech.ui.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import omscs.edtech.ui.controls.IndexedButtonCell;
import omscs.edtech.ui.controls.IndexedButton;
import omscs.edtech.ui.controls.PickStudentDialog;
import omscs.edtech.ui.controls.SetGradeEventHandler;
import omscs.edtech.ui.events.InjectModelEvent;
import omscs.edtech.ui.interfaces.GradesDataAdapter;
import omscs.edtech.ui.models.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

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
    private TableColumn<StudentAssignmentModel, Boolean> colMissingAssignments;

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
                        imgAssignmentImage.setFitWidth(halfWidth);
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
                        tblStudentGrades.setPrefHeight(height - 175);
                        paneAssignment.setPrefHeight(height - 275 - assignmentDescrBox.getPrefHeight());
                        imgAssignmentImage.setFitHeight(paneAssignment.getPrefHeight());
                    }
                }
        );

        tblStudentGrades.setEditable(true);
        colStudentName.setEditable(false);
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
                return new TextFieldTableCell<>(new StringConverter<Number>() {
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
                                    return (int)doubleValue();
                                }

                                @Override
                                public long longValue() {
                                    return Long.valueOf(s);
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

        colAssignmentGrade.setOnEditCommit(new SetGradeEventHandler(currentAssignment));

        colMissingAssignments.setEditable(false);
        colMissingAssignments.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentAssignmentModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<StudentAssignmentModel, Boolean> param) {
                return param.getValue().assignmentMissingProperty();
            }
        });
        colMissingAssignments.setCellFactory(new Callback<TableColumn<StudentAssignmentModel, Boolean>, TableCell<StudentAssignmentModel, Boolean>>() {
            @Override
            public TableCell<StudentAssignmentModel, Boolean> call(final TableColumn<StudentAssignmentModel, Boolean> param) {
                return new IndexedButtonCell<>("Assignment Missing", new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        IndexedButton button = (IndexedButton)event.getSource();
                        StudentAssignmentModel studentAssignmentModel = tblStudentGrades.getItems().get(button.getRowIndex());
                        gradesDataAdapter.sendMissingAssignmentEmail(studentAssignmentModel);
                    }
                });
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
            tblStudentGrades.setItems(FXCollections.<StudentAssignmentModel>emptyObservableList());
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
            lblAssignmentText.textProperty().unbind();
            imgAssignmentImage.imageProperty().setValue(null);
        }

        currentOCRFile = ocrFile;

        if(currentOCRFile != null){
            lblAssignmentText.textProperty().bind(currentOCRFile.fileTextProperty());
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
        PickStudentDialog pickStudentDialog = new PickStudentDialog(parentBox, gradeAssignmentsModel, currentAssignment);
        if(files != null){
            for(File file : files) {
                final OCRFileModel ocrFileModel =
                        gradesDataAdapter.importAssignmentImage(
                                gradeAssignmentsModel.getClassId(), currentAssignment.getId(), file);
                if(ocrFileModel.getStudentId() == null){
                    //Student was unknown, ask to pick:
                    StudentAssignmentModel studentAssignmentModel = pickStudentDialog.showDialog(ocrFileModel);
                    if(studentAssignmentModel != null){
                        ocrFileModel.setStudentId(studentAssignmentModel.getStudentId());
                        gradesDataAdapter.saveOCRFile(ocrFileModel);
                    }
                }

                List<StudentAssignmentModel> student = tblStudentGrades.getItems().filtered(new Predicate<StudentAssignmentModel>() {
                    @Override
                    public boolean test(StudentAssignmentModel model) {
                        return model.getStudentId() == ocrFileModel.getStudentId();
                    }
                });
                if(student != null && !student.isEmpty()){
                    student.get(0).setAssignmentMissing(false);
                }

                if (currentStudent != null && ocrFileModel.getStudentId() == currentStudent.getStudentId()) {
                    //Imported for the currently selected student, bind to UI:
                    setCurrentOCRFile(ocrFileModel);
                }
            }
        }
    }

    @FXML
    protected void btnExportGrades_Click(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Grades");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );

        fileChooser.setInitialFileName(
                gradeAssignmentsModel.toString() + " - " + currentAssignment.getName() + ".csv");
        File savedFile = fileChooser.showSaveDialog(parentBox.getScene().getWindow());
        if(savedFile != null) {
            try {
                FileWriter writer = new FileWriter(savedFile);

                List<StudentAssignmentModel> studentAssignmentModels =
                        gradeAssignmentsModel.getStudentAssignmentList(currentAssignment);
                for (StudentAssignmentModel model : studentAssignmentModels) {
                    writer.write(model.toCsvExport());
                    writer.write("\n");
                }

                writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    protected void btnSaveGrades_Click(ActionEvent event){
         gradesDataAdapter.saveGrades(tblStudentGrades.getItems());
    }

    @FXML
    protected void btnSendAllFeedback_Click(ActionEvent event) throws Exception{
        for (StudentAssignmentModel assignmentModel : tblStudentGrades.getItems()) {
            if(!assignmentModel.getAssignmentFeedback().isEmpty()){
                gradesDataAdapter.sendFeedbackEmail(assignmentModel);
            }
        }
    }

    @FXML
    protected void sendFeedback_Click(ActionEvent event){
        if(!currentStudent.getAssignmentFeedback().isEmpty()) {
            gradesDataAdapter.sendFeedbackEmail(currentStudent);
        }
    }
}
