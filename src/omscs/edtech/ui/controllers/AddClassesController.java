package omscs.edtech.ui.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import omscs.edtech.ui.controls.IntegerField;
import omscs.edtech.ui.models.ClassModel;

import javax.swing.*;

public class AddClassesController {
    @FXML
    private HBox parentBox;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;

    @FXML
    private TextField txtClassName;
    @FXML
    private IntegerField txtClassPeriod;
    @FXML
    private IntegerField txtClassYear;
    @FXML
    private CheckBox cbClassActive;

    private ClassModel currentClass;

    @FXML
    protected void initialize(){

        setPanelWidth(parentBox.getWidth());
        setPanelHeight(parentBox.getHeight());

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
    }

    @FXML
    protected void addNewClass_Click(ActionEvent event){
        currentClass = new ClassModel();
        currentClass.setClassName("Class 1");
        currentClass.setClassPeriod(6);
        currentClass.setClassYear(2016);

        txtClassName.textProperty().bindBidirectional(currentClass.classNameProperty());
        txtClassPeriod.integerProperty().bindBidirectional(currentClass.classPeriodProperty());
        txtClassYear.integerProperty().bindBidirectional(currentClass.classYearProperty());
        cbClassActive.selectedProperty().bindBidirectional(currentClass.activeProperty());
    }

    @FXML
    protected void saveClass_Click(ActionEvent event){
        //Temp for testing
        currentClass.setActive(true);
    }

    void setPanelWidth(double totalWidth){
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

    void setPanelHeight(double totalHeight){
        leftBox.setPrefHeight(totalHeight);
        rightBox.setPrefHeight(totalHeight);
    }
}
