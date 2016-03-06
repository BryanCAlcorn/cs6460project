package omscs.edtech.ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import omscs.edtech.ui.controllers.ControllerConstants;

public class MainApplication extends Application {

    private Pane appContainer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + ControllerConstants.MAIN_SCREEN_VIEW));

        appContainer = (Pane) root.lookup("#appContainer");
        primaryStage.setTitle(ControllerConstants.APP_TITLE);
        primaryStage.setScene(new Scene(root));

        //Set width and height properties:
        primaryStage.widthProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double width = newValue.doubleValue();
                        appContainer.setPrefWidth(width);
                    }
                }
        );

        primaryStage.heightProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        Double height = newValue.doubleValue();
                        appContainer.setPrefHeight(height);
                    }
                }
        );

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
