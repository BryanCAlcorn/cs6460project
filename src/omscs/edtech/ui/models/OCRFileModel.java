package omscs.edtech.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.*;

public class OCRFileModel {

    private Integer studentId;
    private StringProperty fileText;
    private Image fileImage;

    public OCRFileModel(Integer studentId, String fileText, Image fileImage) {
        this.studentId = studentId;
        this.fileText = new SimpleStringProperty(fileText);
        this.fileImage = fileImage;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getFileText() {
        return fileText.getValue();
    }

    public StringProperty fileTextProperty() {
        return fileText;
    }
}
