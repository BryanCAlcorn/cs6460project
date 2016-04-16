package omscs.edtech.ui.controls;

import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;

public interface EmailCustomToolbar {
    String getEmailName();
    Button[] getCustomButtons();
    void setEditor(HTMLEditor editor);
}
