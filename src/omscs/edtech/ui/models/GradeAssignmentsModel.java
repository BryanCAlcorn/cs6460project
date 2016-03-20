package omscs.edtech.ui.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeAssignmentsModel {

    private final ClassModel classModel;
    private ObservableList<AssignmentModel> assignmentModels;
    private ObjectProperty<ObservableList<AssignmentModel>> assignmentModelsProperty;
    private Map<AssignmentModel, ObservableList<StudentAssignmentModel>> assignmentStudentMap;

    public GradeAssignmentsModel(ClassModel cm, List<AssignmentModel> assignments){
        classModel = cm;
        assignmentModels = FXCollections.observableArrayList(assignments);
        assignmentModelsProperty = new SimpleObjectProperty<>(assignmentModels);
        assignmentStudentMap = new HashMap<>();

        //Map assignments to students:
        for(AssignmentModel assignmentModel : assignmentModels) {
            ObservableList<StudentAssignmentModel> students = FXCollections.emptyObservableList();
            for (StudentModel studentModel : classModel.studentsProperty()) {
                students.add(new StudentAssignmentModel(studentModel, 0));
            }
            assignmentStudentMap.put(assignmentModel, students);
        }
    }

    public ClassModel getClassModel(){
        return classModel;
    }

    public ObjectProperty<ObservableList<AssignmentModel>> getAssignmentModelsProperty() {
        return assignmentModelsProperty;
    }

    public ObservableList<StudentAssignmentModel> getStudentAssignmentList(AssignmentModel assignment){
        return assignmentStudentMap.get(assignment);
    }

    @Override
    public String toString() {
        return classModel.toString();
    }
}
