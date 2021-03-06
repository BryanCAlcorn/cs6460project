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

    public GradeAssignmentsModel(ClassModel cm, Map<AssignmentModel, ObservableList<StudentAssignmentModel>> assignmentStudentMap){
        classModel = cm;
        this.assignmentStudentMap = assignmentStudentMap;
        assignmentModels = FXCollections.observableArrayList(assignmentStudentMap.keySet());
        assignmentModelsProperty = new SimpleObjectProperty<>(assignmentModels);
    }

    public ClassModel getClassModel(){
        return classModel;
    }

    public Integer getClassId(){
        return classModel.getId();
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
