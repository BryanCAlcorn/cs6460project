package omscs.edtech.ui.interfaces;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import omscs.edtech.ui.models.AssignmentModel;
import omscs.edtech.ui.models.ClassAssignmentModel;

public class AssignmentDataAdapter {

    private ObservableList<ClassAssignmentModel> classAssignmentModels;
    private ObservableList<AssignmentModel> assignmentModels;
    ObjectProperty<ObservableList<AssignmentModel>> assignmentsProperty;

    public AssignmentDataAdapter(){
        classAssignmentModels = FXCollections.observableArrayList();

        ClassAssignmentModel c1 = new ClassAssignmentModel("C1", false);
        ClassAssignmentModel c2 = new ClassAssignmentModel("C2", false);

        classAssignmentModels.add(c1);
        classAssignmentModels.add(c2);

        AssignmentModel a1 = new AssignmentModel();
        a1.setName("Assignment 1");
        a1.setMaxPoints(100);
        a1.setDescription("This assignment is worth 100 points");
        a1.assignToClass(c2);

        AssignmentModel a2 = new AssignmentModel();
        a2.setName("Assignment 2");
        a2.setMaxPoints(50);
        a2.setDescription("This assignment is worth 50 points");
        a2.assignToClass(c1);

        assignmentModels = FXCollections.observableArrayList();
        assignmentModels.add(a1);
        assignmentModels.add(a2);

        assignmentsProperty = new SimpleObjectProperty<>(assignmentModels);
    }

    public ObservableList<ClassAssignmentModel> getClassAssignmentModels(){
        return classAssignmentModels;
    }

    public ObservableList<AssignmentModel> getAssignmentModels(){
        return assignmentModels;
    }

    public ObjectProperty<ObservableList<AssignmentModel>> getAssignmentsProperty(){
        return assignmentsProperty;
    }

    public boolean containsAssignment(AssignmentModel assignmentModel){
        return assignmentModels.contains(assignmentModel);
    }

    public void addAssignment(AssignmentModel assignmentModel){
        assignmentModels.add(assignmentModel);
    }
}
