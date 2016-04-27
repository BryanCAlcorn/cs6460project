package omscs.edtech.roster;

import omscs.edtech.db.model.Student;

import java.io.File;

public class RosterTest {

    public static void main(String[] args){
        String pathToXL = "C:\\Users\\10119365\\IdeaProjects\\CS6460Project\\testfiles\\TestRoster.xlsx";
        RosterReader rosterReader = new QRosterReader();
        Student[] students = rosterReader.readRoster(new File(pathToXL));

        for(Student student : students){
            System.out.println(student.getFirstName() + " " + student.getLastName());
        }
    }
}
