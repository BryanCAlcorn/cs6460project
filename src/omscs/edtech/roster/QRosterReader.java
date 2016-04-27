package omscs.edtech.roster;

import omscs.edtech.db.model.Student;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class QRosterReader implements RosterReader {

    @Override
    public Student[] readRoster(File file) {
        ExcelAdapter excelAdapter = null;
        try {
            excelAdapter = new ExcelAdapter(file);
        }catch (IOException ioex){
            System.out.println(ioex.getMessage());
        }catch (InvalidFormatException ifex){
            System.out.println(ifex.getMessage());
        }

        List<Student> students = new ArrayList<>();

        if(excelAdapter != null){
            final int headerRows = 5;
            int numStudents = excelAdapter.getNumStudents(0, headerRows);

            for(int i = headerRows; i < numStudents + headerRows - 1; i++){
                DataRowLocation dataRow = new DataRowLocation(0, i, 6);
                String[] dataValues = excelAdapter.getCellValuesAsStrings(dataRow);

                String[] name = dataValues[0].split(",");
                String firstName = name[1].split("\\(")[0];

                Student student = new Student();
                student.setLastName(name[0].trim());
                student.setFirstName(firstName.trim());
                students.add(student);
            }
        }

        return students.toArray(new Student[students.size()]);
    }
}
