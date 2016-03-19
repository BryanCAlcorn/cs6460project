package omscs.edtech.ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import omscs.edtech.ui.events.InjectClassModelEvent;
import omscs.edtech.ui.models.ClassModel;

public class GradeAssignmentsController {
    @FXML
    private HBox parentBox;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;

    private ClassModel classModel;

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

        parentBox.addEventFilter(InjectClassModelEvent.INJECT_CLASS_MODEL,
                new EventHandler<InjectClassModelEvent>() {
                    @Override
                    public void handle(InjectClassModelEvent injectClassModelEvent) {
                        classModel = injectClassModelEvent.getClassModel();
                        //Do anything else needed to load the class model!
                    }
                });

    }

    @FXML
    protected void importAssignments_Click(ActionEvent event){

    }
}
