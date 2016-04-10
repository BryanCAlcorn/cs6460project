package omscs.edtech.ui.interfaces;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import omscs.edtech.db.interfaces.AssignmentDataConnector;
import omscs.edtech.db.interfaces.ClassDataConnector;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.ui.models.AssignmentModel;
import omscs.edtech.ui.models.ClassAssignmentModel;

public class AssignmentDataAdapter {

    AssignmentDataConnector assignmentDataConnector;
    ClassDataConnector classDataConnector;
    private ObservableList<ClassAssignmentModel> classAssignmentModels;
    private ObservableList<AssignmentModel> assignmentModels;
    ObjectProperty<ObservableList<AssignmentModel>> assignmentsProperty;

    public AssignmentDataAdapter(){
        assignmentDataConnector = new AssignmentDataConnector();
        classDataConnector = new ClassDataConnector();
        classAssignmentModels = FXCollections.observableArrayList();
        assignmentModels = FXCollections.observableArrayList();

        for(Class dbClass : classDataConnector.getActiveClasses()){
            classAssignmentModels.add(fromClass(dbClass));
        }

        for (Assignment assignment : assignmentDataConnector.getAssignments()){
            AssignmentModel model = fromAssignment(assignment);

            //Assign classes to assignments:
            for(Class dbClass : classDataConnector.lookupClassesForAssignment(assignment)){
                ClassAssignmentModel assignedClass = null;
                for(ClassAssignmentModel classAssignmentModel : classAssignmentModels){
                    if(classAssignmentModel.getClassId() == dbClass.getId()){
                        assignedClass = classAssignmentModel;
                        break;
                    }
                }
                model.assignToClass(assignedClass);
            }

            assignmentModels.add(model);
        }

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

    public void
    saveAssignment(AssignmentModel assignmentModel){
        Assignment assignment = toAssignment(assignmentModel);
        assignmentDataConnector.saveAssignmentWithClasses(assignment);
    }

    public void addAssignment(AssignmentModel assignmentModel){
        assignmentModels.add(assignmentModel);
    }

    private AssignmentModel fromAssignment(Assignment assignment){
        AssignmentModel model = new AssignmentModel();

        model.setId(assignment.getId());
        model.setName(assignment.getName());
        model.setMaxPoints(assignment.getMaxPoints());
        model.setDescription(assignment.getDescription());

        return model;
    }

    private ClassAssignmentModel fromClass(Class dbClass){
        ClassAssignmentModel model = new ClassAssignmentModel(dbClass.getId(), dbClass.toString());
        return model;
    }

    private Assignment toAssignment(AssignmentModel assignmentModel){
        Assignment assignment = new Assignment(assignmentModel.getId());

        assignment.setName(assignmentModel.getName());
        assignment.setDescription(assignmentModel.getDescription());
        assignment.setMaxPoints(assignmentModel.getMaxPoints());
        for(ClassAssignmentModel classAssignmentModel : assignmentModel.getAssignedClasses()){
            assignment.addDbClass(toClass(classAssignmentModel));
        }

        return assignment;
    }

    private Class toClass(ClassAssignmentModel classAssignmentModel){
        Class dbClass = new Class(classAssignmentModel.getClassId());
        return dbClass;
    }
}
