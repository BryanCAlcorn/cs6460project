package omscs.edtech.ui.interfaces;


import omscs.edtech.TessAPI.TesseractAPI;
import omscs.edtech.db.interfaces.GradeDataConnector;
import omscs.edtech.db.interfaces.OCRFileDataConnector;
import omscs.edtech.db.model.*;
import omscs.edtech.db.model.Class;
import omscs.edtech.ui.models.OCRFileModel;
import omscs.edtech.ui.models.StudentAssignmentModel;

import java.io.File;
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
                studentAssignmentModel.getStudentModel().getId(),
                studentAssignmentModel.getClassId(),
                studentAssignmentModel.getAssignmentId());

        return fromOCRFile(ocrFile);
    }

    private OCRFileModel fromOCRFile(OCRFile ocrFile){
        return new OCRFileModel(ocrFile.getStudent().getId(), ocrFile.getParsedText(),
                TesseractAPI.getImage(ocrFile.getOriginalImage()));
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
