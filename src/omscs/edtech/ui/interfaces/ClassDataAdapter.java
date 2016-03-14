package omscs.edtech.ui.interfaces;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import omscs.edtech.ui.models.ClassModel;
import omscs.edtech.ui.models.StudentModel;

public class ClassDataAdapter {

    private ObjectProperty<ObservableList<ClassModel>> itemObjects;
    private ObservableList<ClassModel> allClasses;

    public ClassDataAdapter(){
        allClasses = FXCollections.observableArrayList();

        //Temp data, should be obtained from DB:
        ClassModel c1 = new ClassModel();
        c1.addStudent(new StudentModel("C1 Jim", "C11@yahoo.com"));
        c1.addStudent(new StudentModel("C1 Jon", "C12@yahoo.com"));
        c1.setClassName("Class 1");
        ClassModel c2 = new ClassModel();
        c2.addStudent(new StudentModel("C2 Jimmy", "C21@yahoo.com"));
        c2.addStudent(new StudentModel("C2 Jonny", "C22@yahoo.com"));
        c2.setClassName("Class 2");

        allClasses.add(c1);
        allClasses.add(c2);

        itemObjects = new SimpleObjectProperty<>(allClasses);
    }

    public ObjectProperty<ObservableList<ClassModel>> getItemObjects(){
        return itemObjects;
    }

    public ObservableList<ClassModel> classesProperty(){
        return allClasses;
    }

    public void addClass(ClassModel classModel){
        allClasses.add(classModel);
    }

    public boolean containsClass(ClassModel classModel){
        return allClasses.contains(classModel);
    }
}
