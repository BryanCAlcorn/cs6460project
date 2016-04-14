package omscs.edtech.ui.controls;

import javafx.scene.control.Button;

class MissingAssignmentToolbar implements EmailCustomToolbar {
    @Override
    public String getEmailName() {
        return "MissingAssignment";
    }

    @Override
    public Button[] getCustomButtons() {
        return new Button[0];
    }
}
