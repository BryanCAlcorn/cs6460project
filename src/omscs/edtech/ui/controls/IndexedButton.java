package omscs.edtech.ui.controls;

import javafx.scene.control.Button;

public class IndexedButton extends Button {

    private int rowIndex;

    public IndexedButton(String text) {
        super(text);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
}
