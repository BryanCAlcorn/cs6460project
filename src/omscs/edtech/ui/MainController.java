package omscs.edtech.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


public class MainController {

    @FXML
    private Text targetTextBox;

    @FXML
    protected void handleButtonAction(ActionEvent event){
        targetTextBox.setText("Button was pressed");
    }
}
