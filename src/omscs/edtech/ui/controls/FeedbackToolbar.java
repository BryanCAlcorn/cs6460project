package omscs.edtech.ui.controls;

import javafx.scene.control.Button;

class FeedbackToolbar implements EmailCustomToolbar{
    @Override
    public String getEmailName() {
        return "Feedback";
    }

    @Override
    public Button[] getCustomButtons() {
        return new Button[0];
    }
}
