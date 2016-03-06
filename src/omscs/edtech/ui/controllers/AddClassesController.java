package omscs.edtech.ui.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddClassesController {
    @FXML
    private HBox parentBox;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;

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
