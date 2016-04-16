package omscs.edtech.ui.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class OCRFileModel {

    private Integer fileId;
    private Integer studentId;
    private Integer classId;
    private Integer assignmentId;
    private StringProperty fileText;
    private ObjectProperty<Image> imageProperty;

    public OCRFileModel(String fileText, Image image) {
        this.fileText = new SimpleStringProperty(fileText);

        imageProperty = new SimpleObjectProperty<>(image);
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Image getImageProperty() {
        return imageProperty.getValue();
    }

    public double getImageWidth(){
        return imageProperty.getValue().getWidth();
    }

    public double getImageHeight(){
        return imageProperty.getValue().getHeight();
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
