package omscs.edtech.ui.interfaces;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import omscs.edtech.ui.models.ClassAssignmentModel;

public class AssignmentDataAdapter {

    private ObservableList<ClassAssignmentModel> classAssignmentModels;

    public AssignmentDataAdapter(){
        classAssignmentModels = FXCollections.observableArrayList();

        ClassAssignmentModel c1 = new ClassAssignmentModel("C1", false);
        ClassAssignmentModel c2 = new ClassAssignmentModel("C2", false);

        classAssignmentModels.add(c1);
        classAssignmentModels.add(c2);
    }

    public ObservableList<ClassAssignmentModel> getClassAssignmentModels(){
        return classAssignmentModels;
    }
}
