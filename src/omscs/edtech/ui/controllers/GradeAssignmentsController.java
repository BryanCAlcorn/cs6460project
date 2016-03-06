package omscs.edtech.ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GradeAssignmentsController {
    @FXML
    private HBox parentBox;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;

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
    }
}
