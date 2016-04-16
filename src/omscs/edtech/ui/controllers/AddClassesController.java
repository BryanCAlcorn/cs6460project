package omscs.edtech.ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import omscs.edtech.ui.controls.IntegerField;
import omscs.edtech.ui.interfaces.ClassDataAdapter;
import omscs.edtech.ui.models.ClassModel;
import omscs.edtech.ui.models.StudentModel;

public class AddClassesController {
    //Top Level Elements:
    @FXML
    private HBox parentBox;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;
    @FXML
    private VBox emptyBox;

    @FXML
    private ComboBox<ClassModel> comboClassesList;

    //Input fields
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtStudentEmail;
    @FXML
    private TextField txtClassName;
    @FXML
    private IntegerField txtClassPeriod;
    @FXML
    private IntegerField txtClassYear;
    @FXML
    private CheckBox cbClassActive;

    //Grid fields
    @FXML
    private TableView<StudentModel> tblStudents;
    @FXML
    private TableColumn<StudentModel, String> colStudentName;
    @FXML
    private TableColumn<StudentModel, String> colEmail;

    private ClassDataAdapter classDataAdapter;
    private ClassModel currentClass;

    @FXML
    protected void initialize(){

        classDataAdapter = new ClassDataAdapter();
        setPanelWidth(parentBox.getWidth());
        setPanelHeight(parentBox.getHeight());

        tblStudents.setEditable(true);

        //Bind table columns to StudentModel properties
        colStudentName.setCellFactory(new Callback<TableColumn<StudentModel, String>, TableCell<StudentModel, String>>() {
            @Override
            public TableCell<StudentModel, String> call(TableColumn<StudentModel, String> studentModelStringTableColumn) {
                return new TextFieldTableCell<>(new StringConverter<String>() {
                    @Override
                    public String toString(String s) {
                        return s;
                    }

                    @Override
                    public String fromString(String s) {
                        return s;
                    }
                });
            }
        });
        colStudentName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentModel, String> studentModel) {
                return studentModel.getValue().getStudentNameProperty();
            }
        });
        colStudentName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<StudentModel, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<StudentModel, String> studentModelStringCellEditEvent) {
                studentModelStringCellEditEvent.getTableView().getItems().get(
                        studentModelStringCellEditEvent.getTablePosition().getRow())
                        .setStudentName(studentModelStringCellEditEvent.getNewValue());
            }
        });

        colEmail.setCellFactory(new Callback<TableColumn<StudentModel, String>, TableCell<StudentModel, String>>() {
            @Override
            public TableCell<StudentModel, String> call(TableColumn<StudentModel, String> studentModelStringTableColumn) {
                return new TextFieldTableCell<>(new StringConverter<String>() {
                    @Override
                    public String toString(String s) {
                        return s;
                    }

                    @Override
                    public String fromString(String s) {
                        return s;
                    }
                });
            }
        });
        colEmail.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentModel, String> studentModel) {
                return studentModel.getValue().studentEmailProperty();
            }
        });
        colEmail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<StudentModel, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<StudentModel, String> studentModelStringCellEditEvent) {
                studentModelStringCellEditEvent.getTableView().getItems().get(
                        studentModelStringCellEditEvent.getTablePosition().getRow())
                        .setStudentEmail(studentModelStringCellEditEvent.getNewValue());
            }
        });

        //Set width and height properties:
        parentBox.widthProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        setPanelWidth(newValue.doubleValue());
                    }
                }
        );

        parentBox.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        setPanelHeight(newValue.doubleValue());
                    }
                }
        );

        comboClassesList.itemsProperty().bindBidirectional(classDataAdapter.getClassesProperty());
        comboClassesList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setCurrentClass(comboClassesList.getSelectionModel().getSelectedItem());
            }
        });
        ControllerConstants.selectFirstComboItem(comboClassesList);
    }

    @FXML
    protected void addNewClass_Click(ActionEvent event){
        ClassModel newClass = new ClassModel();
        setCurrentClass(newClass);
    }

    private void setCurrentClass(ClassModel classModel){
        if(currentClass != null){
            txtClassName.textProperty().unbindBidirectional(currentClass.classNameProperty());
            txtClassPeriod.integerProperty().unbindBidirectional(currentClass.classPeriodProperty());
            txtClassYear.integerProperty().unbindBidirectional(currentClass.classYearProperty());
            cbClassActive.selectedProperty().unbindBidirectional(currentClass.activeProperty());
            tblStudents.setItems(FXCollections.<StudentModel>emptyObservableList());
        }

        currentClass = classModel;

        if(currentClass != null) {
            txtClassName.textProperty().bindBidirectional(currentClass.classNameProperty());
            txtClassPeriod.integerProperty().bindBidirectional(currentClass.classPeriodProperty());
            txtClassYear.integerProperty().bindBidirectional(currentClass.classYearProperty());
            cbClassActive.selectedProperty().bindBidirectional(currentClass.activeProperty());
            tblStudents.setItems(currentClass.studentsProperty());
        }
    }

    @FXML
    protected void saveClass_Click(ActionEvent event){
        if(!classDataAdapter.containsClass(currentClass)) {
            classDataAdapter.addClass(currentClass);
            comboClassesList.getSelectionModel().select(currentClass);
        }
        classDataAdapter.saveClass(currentClass);
    }

    @FXML
    protected void addStudent_Click(ActionEvent event){
        if(currentClass != null){
            if(txtStudentName.getText().length() > 0){
                StudentModel student = new StudentModel(txtStudentName.getText(), txtStudentEmail.getText());
                currentClass.studentsProperty().add(student);
            }
            clear_Click(event);
        }
    }

    @FXML
    protected void clear_Click(ActionEvent event){
        txtStudentName.clear();
        txtStudentEmail.clear();
    }

    private void setPanelWidth(double totalWidth){
        double halfWidth = totalWidth / 2.0;
        double leftWidth = halfWidth;
        double rightWidth = halfWidth;
        if(halfWidth > 450){
            double difference = halfWidth - 450;
            leftWidth = 450;
            rightWidth += difference;
        }
        leftBox.setPrefWidth(leftWidth);
        rightBox.setPrefWidth(rightWidth);
    }

    private void setPanelHeight(double totalHeight){
        leftBox.setPrefHeight(totalHeight);
        rightBox.setPrefHeight(totalHeight);
        tblStudents.setPrefHeight(totalHeight - 182);
        emptyBox.setPrefHeight(totalHeight - 462);
    }
}
