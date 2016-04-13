package omscs.edtech.ui.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class OCRFileModel {

    private Integer studentId;
    private StringProperty fileText;
    private ObjectProperty<Image> imageProperty;

    public OCRFileModel(Integer studentId, String fileText, byte[] imageBytes) {
        this.studentId = studentId;
        this.fileText = new SimpleStringProperty(fileText);
        Image fxImage = new Image(new ByteArrayInputStream(imageBytes));
        imageProperty = new SimpleObjectProperty<>(fxImage);
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Image getImageProperty() {
        return imageProperty.getValue();
    }

    public ObjectProperty<Image> imagePropertyProperty() {
        return imageProperty;
    }

    public String getFileText() {
        return fileText.getValue();
    }

    public StringProperty fileTextProperty() {
        return fileText;
    }
}
