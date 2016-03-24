package omscs.edtech.ui.interfaces;

import omscs.edtech.ui.models.AssignmentModel;
import omscs.edtech.ui.models.ClassModel;
import omscs.edtech.ui.models.GradeAssignmentsModel;
import omscs.edtech.ui.models.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class GradeAssignmentsDataAdapter {

    private List<GradeAssignmentsModel> gradeAssignmentsModels;

    public GradeAssignmentsDataAdapter(){
        gradeAssignmentsModels = new ArrayList<>();

        ClassModel c1 = new ClassModel();
        c1.setClassName("English");
        c1.setClassPeriod(1);
        c1.setClassYear(2016);
        c1.setActive(true);
        c1.addStudent(new StudentModel("Jim Jones", "Jim@gmail.com"));
        c1.addStudent(new StudentModel("James Jones", "James@gmail.com"));

        ClassModel c2 = new ClassModel();
        c2.setClassName("English");
        c2.setClassPeriod(2);
        c2.setClassYear(2016);
        c2.setActive(true);
        c2.addStudent(new StudentModel("Jim Bob", "Jimbo@gmail.com"));
        c2.addStudent(new StudentModel("James Bob", "Jamesbo@gmail.com"));
        c2.addStudent(new StudentModel("Jimmy Bob", "Jimmybo@gmail.com"));
        c2.addStudent(new StudentModel("Jack Bob", "Jackbo@gmail.com"));

        List<AssignmentModel> assignmentModels = new ArrayList<>();

        AssignmentModel a1 = new AssignmentModel();
        a1.setName("Essay");
        a1.setMaxPoints(100);
        a1.setDescription("Essay about history!");

        AssignmentModel a2 = new AssignmentModel();
        a2.setName("Simple Assignment");
        a2.setMaxPoints(10);
        a2.setDescription("This one is simple");

        assignmentModels.add(a1);
        assignmentModels.add(a2);

        gradeAssignmentsModels.add(new GradeAssignmentsModel(c1, assignmentModels));
        gradeAssignmentsModels.add(new GradeAssignmentsModel(c2, assignmentModels));
    }

    public List<GradeAssignmentsModel> getGradeAssignmentsModels(){
        return gradeAssignmentsModels;
    }
}
