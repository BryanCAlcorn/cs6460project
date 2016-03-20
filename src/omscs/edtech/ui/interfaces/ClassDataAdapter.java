package omscs.edtech.ui.interfaces;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import omscs.edtech.ui.models.ClassModel;
import omscs.edtech.ui.models.StudentModel;

public class ClassDataAdapter {

    private ObjectProperty<ObservableList<ClassModel>> classesProperty;
    private ObservableList<ClassModel> allClasses;

    public ClassDataAdapter(){
        allClasses = FXCollections.observableArrayList();

        //Temp data, should be obtained from DB:
        ClassModel c1 = new ClassModel();
        c1.addStudent(new StudentModel("C1 Jim", "C11@yahoo.com"));
        c1.addStudent(new StudentModel("C1 Jon", "C12@yahoo.com"));
        c1.setClassName("History");
        c1.setClassPeriod(1);
        c1.setClassYear(2016);
        ClassModel c2 = new ClassModel();
        c2.addStudent(new StudentModel("C2 Jimmy", "C21@yahoo.com"));
        c2.addStudent(new StudentModel("C2 Jonny", "C22@yahoo.com"));
        c2.setClassName("History");
        c2.setClassPeriod(2);
        c2.setClassYear(2016);

        allClasses.add(c1);
        allClasses.add(c2);

        classesProperty = new SimpleObjectProperty<>(allClasses);
    }

    public ObjectProperty<ObservableList<ClassModel>> getClassesProperty(){
        return classesProperty;
    }

    public ObservableList<ClassModel> getAllClasses(){
        return allClasses;
    }

    public void addClass(ClassModel classModel){
        allClasses.add(classModel);
    }

    public boolean containsClass(ClassModel classModel){
        return allClasses.contains(classModel);
    }
}
