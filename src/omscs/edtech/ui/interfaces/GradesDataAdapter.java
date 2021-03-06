package omscs.edtech.ui.interfaces;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import omscs.edtech.db.interfaces.*;
import omscs.edtech.db.model.*;
import omscs.edtech.db.model.Class;
import omscs.edtech.mail.SendMail;
import omscs.edtech.ocr.OCRAdapter;
import omscs.edtech.ocr.OCRAdapterFactory;
import omscs.edtech.ui.controllers.EmailEditorController;
import omscs.edtech.ui.controls.EmailControlConstants;
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
    private EmailTemplateDataConnector emailTemplateDataConnector;
    private AssignmentDataConnector assignmentDataConnector;
    private ClassDataConnector classDataConnector;

    public GradesDataAdapter(){
        gradeDataConnector = new GradeDataConnector();
        ocrFileDataConnector = new OCRFileDataConnector();
        emailTemplateDataConnector = new EmailTemplateDataConnector();
        assignmentDataConnector = new AssignmentDataConnector();
        classDataConnector = new ClassDataConnector();
    }

    public void saveGrades(List<StudentAssignmentModel> gradedAssignments){
        if(gradedAssignments != null && !gradedAssignments.isEmpty()) {
            for (StudentAssignmentModel assignment : gradedAssignments) {
                gradeDataConnector.saveGrade(toGrade(assignment));
            }
        }
    }

    private OCRAdapter ocrAdapter;
    public OCRFileModel importAssignmentImage(Integer classId, Integer assignmentId, File assignmentFile){
        if(ocrAdapter == null){
            ocrAdapter = OCRAdapterFactory.getInstance();
        }
        OCRFile ocrFile = ocrAdapter.ocrRead(classId, assignmentId, assignmentFile);
        return fromOCRFile(ocrFile);
    }

    public OCRFileModel getAssignmentImage(StudentAssignmentModel studentAssignmentModel){
        OCRFile ocrFile = ocrFileDataConnector.getOCRFile(
                studentAssignmentModel.getStudentId(),
                studentAssignmentModel.getAssignmentId(),
                studentAssignmentModel.getClassId());

        return fromOCRFile(ocrFile);
    }

    public void saveOCRFile(OCRFileModel ocrFileModel){
        ocrFileDataConnector.saveOCRFile(toOCRFile(ocrFileModel));
    }

    public void sendFeedbackEmail(StudentAssignmentModel studentAssignmentModel){
        EmailTemplate template = emailTemplateDataConnector.getTemplateByName(EmailControlConstants.EMAIL_FEEDBACK_NAME);
        String emailBody = template.getTemplate();
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_STUDENT_NAME,
                studentAssignmentModel.getStudentModel().getStudentName());
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_FEEDBACK,
                studentAssignmentModel.getAssignmentFeedback());
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_GRADE,
                studentAssignmentModel.getStudentGrade().toString());
        Assignment assignment = assignmentDataConnector.getAssignmentById(studentAssignmentModel.getAssignmentId());
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_ASSIGNMENT_NAME,
                assignment.getName());
        Class dbClass = classDataConnector.getClassById(studentAssignmentModel.getClassId());
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_CLASS_NAME,
                dbClass.getName());

        SendMail.sendFeedback(studentAssignmentModel.getStudentModel().getStudentEmail(), emailBody);
    }

    public void sendMissingAssignmentEmail(StudentAssignmentModel studentAssignmentModel){
        EmailTemplate template = emailTemplateDataConnector.getTemplateByName(EmailControlConstants.EMAIL_MISSING_NAME);

        String emailBody = template.getTemplate();
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_STUDENT_NAME,
                studentAssignmentModel.getStudentModel().getStudentName());
        Assignment assignment = assignmentDataConnector.getAssignmentById(studentAssignmentModel.getAssignmentId());
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_ASSIGNMENT_NAME,
                assignment.getName());
        Class dbClass = classDataConnector.getClassById(studentAssignmentModel.getClassId());
        emailBody = emailBody.replace(
                EmailControlConstants.TOKEN_CLASS_NAME,
                dbClass.getName());

        SendMail.sendMissingAssignment(studentAssignmentModel.getStudentModel().getStudentEmail(), emailBody);
    }

    private OCRFileModel fromOCRFile(OCRFile ocrFile){
        OCRFileModel model = null;
        if(ocrFile != null) {
            model = new OCRFileModel(ocrFile.getParsedText(), getImageFromBytes(ocrFile.getOriginalImage()));
            if(ocrFile.getStudent() != null) {
                model.setStudentId(ocrFile.getStudentId());
            }
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

        grade.setStudent(new Student(model.getStudentId()));
        grade.setDbClass(new Class(model.getClassId()));
        grade.setAssignment(new Assignment(model.getAssignmentId()));

        return grade;
    }
}
