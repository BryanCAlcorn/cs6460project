package omscs.edtech.ui.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

public class ButtonCell<T> extends TableCell<T, Boolean> {

    private Button cellButton;

    public ButtonCell(String buttonText, EventHandler<ActionEvent> buttonHandler) {
        cellButton = new Button(buttonText);
        cellButton.setOnAction(buttonHandler);
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty && t) {
            setGraphic(cellButton);
        }else {
            setGraphic(null);
        }
    }
}
