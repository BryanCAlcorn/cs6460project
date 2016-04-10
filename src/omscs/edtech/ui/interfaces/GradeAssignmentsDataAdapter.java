package omscs.edtech.ui.interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import omscs.edtech.db.interfaces.AssignmentDataConnector;
import omscs.edtech.db.interfaces.ClassDataConnector;
import omscs.edtech.db.interfaces.GradeDataConnector;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Grade;
import omscs.edtech.db.model.Student;
import omscs.edtech.ui.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeAssignmentsDataAdapter {

    private ClassDataConnector classDataConnector;
    private AssignmentDataConnector assignmentDataConnector;
    private GradeDataConnector gradeDataConnector;
    private List<GradeAssignmentsModel> gradeAssignmentsModels;

    public GradeAssignmentsDataAdapter(){

        classDataConnector = new ClassDataConnector();
        assignmentDataConnector = new AssignmentDataConnector();
        gradeDataConnector = new GradeDataConnector();
        gradeAssignmentsModels = new ArrayList<>();

        List<Class> activeClasses = classDataConnector.getActiveClasses();
        for (Class dbClass : activeClasses){
            ClassModel classModel = fromClass(dbClass);
            Map<AssignmentModel, ObservableList<StudentAssignmentModel>> assignmentMap = new HashMap<>();
            List<Assignment> assignments = assignmentDataConnector.lookupAssignmentsForClasses(dbClass);
            for(Assignment assignment : assignments){
                AssignmentModel assignmentModel = fromAssignment(assignment);
                List<Grade> assignmentGrades = gradeDataConnector.getGrades(dbClass, assignment);
                ObservableList<StudentAssignmentModel> studentAssignmentModels = FXCollections.observableArrayList();
                for(Grade grade : assignmentGrades){
                    studentAssignmentModels.add(fromGrade(grade));
                }
                assignmentMap.put(assignmentModel, studentAssignmentModels);
            }
            gradeAssignmentsModels.add(new GradeAssignmentsModel(classModel, assignmentMap));
        }
    }

    public List<GradeAssignmentsModel> getGradeAssignmentsModels(){
        return gradeAssignmentsModels;
    }



    private ClassModel fromClass(Class dbClass){
        ClassModel classModel = new ClassModel();
        classModel.setClassName(dbClass.getName());
        classModel.setClassPeriod(dbClass.getPeriod());
        classModel.setClassYear(dbClass.getYear());
        classModel.setActive(dbClass.isActive());

        for(Student student : dbClass.getStudents()){
            classModel.addStudent(fromStudent(student));
        }

        return classModel;
    }

    private AssignmentModel fromAssignment(Assignment assignment){
        AssignmentModel model = new AssignmentModel();

        model.setId(assignment.getId());
        model.setName(assignment.getName());
        model.setMaxPoints(assignment.getMaxPoints());
        model.setDescription(assignment.getDescription());

        return model;
    }

    private StudentAssignmentModel fromGrade(Grade grade){
        StudentAssignmentModel studentAssignmentModel =
                new StudentAssignmentModel(fromStudent(grade.getStudent()), grade.getScore());

        studentAssignmentModel.setGradeId(grade.getId());
        studentAssignmentModel.setAssignmentId(grade.getAssignment().getId());
        studentAssignmentModel.setAssignmentMissing(grade.isMissing());
        studentAssignmentModel.setClassId(grade.getDbClass().getId());

        return studentAssignmentModel;
    }

    private StudentModel fromStudent(Student student){
        StudentModel studentModel = new StudentModel(
                student.getFirstName() + " " + student.getLastName(),
                student.geteMailAddress());

        studentModel.setId(student.getId());

        return studentModel;
    }
}
