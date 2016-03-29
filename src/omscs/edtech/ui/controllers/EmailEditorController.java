package omscs.edtech.ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class EmailEditorController {

    @FXML
    private VBox parentBox;

    @FXML
    private HTMLEditor htmlEditorBox;

    @FXML
    protected void initialize(){
        parentBox.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double height = newValue.doubleValue();
                        htmlEditorBox.setPrefHeight(height - 80);
                    }
                }
        );
    }
}
