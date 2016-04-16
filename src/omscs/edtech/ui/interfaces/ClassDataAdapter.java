package omscs.edtech.ui.interfaces;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import omscs.edtech.db.interfaces.ClassDataConnector;
import omscs.edtech.db.interfaces.StudentDataConnector;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Student;
import omscs.edtech.ui.models.ClassModel;
import omscs.edtech.ui.models.StudentModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassDataAdapter {

    private ClassDataConnector classDataConnector;
    private StudentDataConnector studentDataConnector;
    private ObjectProperty<ObservableList<ClassModel>> classesProperty;
    private ObservableList<ClassModel> allClasses;

    public ClassDataAdapter(){
        allClasses = FXCollections.observableArrayList();
        classDataConnector = new ClassDataConnector();
        studentDataConnector = new StudentDataConnector();
        List<Class> classes = classDataConnector.getAllClasses();
        if(classes != null){
            for(Class aClass : classes) {
                Collection<Student> students = aClass.getStudents();
                allClasses.add(fromClass(aClass, students));
            }
        }

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

    public Integer saveClass(ClassModel classModel){
        Class dbClass = toClass(classModel);

        classDataConnector.saveClass(dbClass);

        List<Student> students = new ArrayList<>();
        for(StudentModel studentModel : classModel.studentsProperty()){
            Student student = toStudent(studentModel, dbClass);
            students.add(student);
        }

        studentDataConnector.saveStudents(students);

        return dbClass.getId();
    }

    public boolean deleteClass(ClassModel classModel){
        Class dbClass = toClass(classModel);

        boolean success = classDataConnector.deleteClass(dbClass);
        success &= studentDataConnector.deleteStudentsByClass(dbClass);

        return success;
    }

    private ClassModel fromClass(Class aClass, Collection<Student> students){
        ClassModel classModel = new ClassModel();

        if(aClass != null) {
            classModel.setId(aClass.getId());
            classModel.setClassName(aClass.getName());
            classModel.setClassPeriod(aClass.getPeriod());
            classModel.setClassYear(aClass.getYear());
            classModel.setActive(aClass.isActive());

            if (students != null) {
                for (Student student : students) {
                    classModel.addStudent(fromStudent(student));
                }
            }
        }

        return classModel;
    }

    private Class toClass(ClassModel classModel){
        Class dbClass = new Class(classModel.getId());

        dbClass.setName(classModel.getClassName());
        dbClass.setActive(classModel.getActive());
        dbClass.setPeriod(classModel.getClassPeriod());
        dbClass.setYear(classModel.getClassYear());

        return dbClass;
    }

    private StudentModel fromStudent(Student student){
        StudentModel studentModel = new StudentModel(
                student.getFirstName() + " " + student.getLastName(),
                student.geteMailAddress());

        studentModel.setId(student.getId());

        return studentModel;
    }

    private Student toStudent(StudentModel studentModel, Class dbClass ){
        Student student = new Student(studentModel.getId());

        student.setDbClass(dbClass);
        String[] name = studentModel.getStudentName().split(" ");
        student.setFirstName(name[0]);
        student.setLastName(name[1]);
        student.seteMailAddress(studentModel.getStudentEmail());

        return student;
    }

}
