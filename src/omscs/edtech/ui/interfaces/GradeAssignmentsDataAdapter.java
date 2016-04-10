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

//
//        ClassModel c1 = new ClassModel();
//        c1.setClassName("English");
//        c1.setClassPeriod(1);
//        c1.setClassYear(2016);
//        c1.setActive(true);
//        c1.addStudent(new StudentModel("Jim Jones", "Jim@gmail.com"));
//        c1.addStudent(new StudentModel("James Jones", "James@gmail.com"));
//
//        ClassModel c2 = new ClassModel();
//        c2.setClassName("English");
//        c2.setClassPeriod(2);
//        c2.setClassYear(2016);
//        c2.setActive(true);
//        c2.addStudent(new StudentModel("Jim Bob", "Jimbo@gmail.com"));
//        c2.addStudent(new StudentModel("James Bob", "Jamesbo@gmail.com"));
//        c2.addStudent(new StudentModel("Jimmy Bob", "Jimmybo@gmail.com"));
//        c2.addStudent(new StudentModel("Jack Bob", "Jackbo@gmail.com"));
//
//        List<AssignmentModel> assignmentModels = new ArrayList<>();
//
//        AssignmentModel a1 = new AssignmentModel();
//        a1.setName("Essay");
//        a1.setMaxPoints(100);
//        a1.setDescription("Essay about history!");
//
//        AssignmentModel a2 = new AssignmentModel();
//        a2.setName("Simple Assignment");
//        a2.setMaxPoints(10);
//        a2.setDescription("This one is simple");
//
//        assignmentModels.add(a1);
//        assignmentModels.add(a2);
//
//        gradeAssignmentsModels.add(new GradeAssignmentsModel(c1, assignmentModels));
//        gradeAssignmentsModels.add(new GradeAssignmentsModel(c2, assignmentModels));
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
            classModel.addStudent(new StudentModel(
                    student.getFirstName() + " " + student.getLastName(), student.geteMailAddress()));
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
