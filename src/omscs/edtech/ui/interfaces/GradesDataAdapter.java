package omscs.edtech.ui.interfaces;


import omscs.edtech.db.interfaces.GradeDataConnector;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Grade;
import omscs.edtech.db.model.Student;
import omscs.edtech.ui.models.StudentAssignmentModel;

import java.util.List;

public class GradesDataAdapter {

    GradeDataConnector gradeDataConnector;

    public GradesDataAdapter(){
        gradeDataConnector = new GradeDataConnector();
    }

    public void saveGrades(List<StudentAssignmentModel> gradedAssignments){
        if(gradedAssignments != null && !gradedAssignments.isEmpty()) {
            for (StudentAssignmentModel assignment : gradedAssignments) {
                gradeDataConnector.saveGrade(toGrade(assignment));
            }
        }
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
