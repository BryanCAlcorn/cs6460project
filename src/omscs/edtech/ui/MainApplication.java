package omscs.edtech.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import omscs.edtech.ui.controllers.ControllerConstants;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(ControllerConstants.VIEW_PATH + ControllerConstants.MAIN_SCREEN_VIEW));
        primaryStage.setTitle(ControllerConstants.APP_TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
