package omscs.edtech.ui.interfaces;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import omscs.edtech.TessAPI.TesseractAPI;
import omscs.edtech.db.interfaces.GradeDataConnector;
import omscs.edtech.db.interfaces.OCRFileDataConnector;
import omscs.edtech.db.model.*;
import omscs.edtech.db.model.Class;
import omscs.edtech.ui.models.OCRFileModel;
import omscs.edtech.ui.models.StudentAssignmentModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GradesDataAdapter {

    private GradeDataConnector gradeDataConnector;
    private OCRFileDataConnector ocrFileDataConnector;

    public GradesDataAdapter(){
        gradeDataConnector = new GradeDataConnector();
        ocrFileDataConnector = new OCRFileDataConnector();
    }

    public void saveGrades(List<StudentAssignmentModel> gradedAssignments){
        if(gradedAssignments != null && !gradedAssignments.isEmpty()) {
            for (StudentAssignmentModel assignment : gradedAssignments) {
                gradeDataConnector.saveGrade(toGrade(assignment));
            }
        }
    }

    private TesseractAPI tesseractAPI;
    public OCRFileModel importAssignmentImage(Integer classId, File assignmentFile){
        if(tesseractAPI == null){
            tesseractAPI = new TesseractAPI();
        }
        OCRFile ocrFile = tesseractAPI.ocrRead(classId, assignmentFile);
        return fromOCRFile(ocrFile);
    }

    public OCRFileModel getAssignmentImage(StudentAssignmentModel studentAssignmentModel){
        OCRFile ocrFile = ocrFileDataConnector.getOCRFile(
                studentAssignmentModel.getStudentId(),
                studentAssignmentModel.getClassId(),
                studentAssignmentModel.getAssignmentId());

        return fromOCRFile(ocrFile);
    }

    public void saveOCRFile(OCRFileModel ocrFileModel){
        ocrFileDataConnector.saveOCRFile(toOCRFile(ocrFileModel));
    }

    private OCRFileModel fromOCRFile(OCRFile ocrFile){
        OCRFileModel model = null;
        if(ocrFile != null) {
            model = new OCRFileModel(ocrFile.getStudentId(), ocrFile.getParsedText(),
                        getImageFromBytes(ocrFile.getOriginalImage()));
            model.setFileId(ocrFile.getId());
            model.setClassId(ocrFile.getClassId());
            model.setAssignmentId(ocrFile.getAssignmentId());
        }
        return model;
    }

    private Image getImageFromBytes(byte[] imageBytes){

        Image fxImage = null;

        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            fxImage = SwingFXUtils.toFXImage(img, new WritableImage(img.getWidth(), img.getHeight()));
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        return fxImage;
    }

    private OCRFile toOCRFile(OCRFileModel model){
        OCRFile ocrFile = new OCRFile(model.getFileId());

        ocrFile.setParsedText(model.getFileText());
        ocrFile.setDbClass(new Class(model.getClassId()));
        ocrFile.setAssignment(new Assignment(model.getAssignmentId()));
        ocrFile.setStudent(new Student(model.getStudentId()));
        ocrFile.setReadableName(true);
        ocrFile.setOriginalImage(getBytesFromImage(model.getImageProperty()));

        return ocrFile;
    }

    private byte[] getBytesFromImage(Image fxImage){

        byte[] imageBytes = null;

        try {
            BufferedImage img =
                SwingFXUtils.fromFXImage(fxImage,
                        new BufferedImage((int) fxImage.getWidth(), (int) fxImage.getHeight(),
                                BufferedImage.TYPE_BYTE_BINARY));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(img, "tif", outputStream);
            imageBytes = outputStream.toByteArray();

        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        return imageBytes;
    }

    private Grade toGrade(StudentAssignmentModel model){
        Grade grade = new Grade(model.getGradeId());

        grade.setScore(model.getStudentGrade().doubleValue());
        grade.setMissing(model.getAssignmentMissing());
        grade.setFeedback(model.getAssignmentFeedback());

        grade.setStudent(new Student(model.getStudentModel().getId()));
        grade.setDbClass(new Class(model.getClassId()));
        grade.setAssignment(new Assignment(model.getAssignmentId()));

        return grade;
    }
}
