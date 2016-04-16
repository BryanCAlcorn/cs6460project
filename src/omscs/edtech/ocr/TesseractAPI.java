package omscs.edtech.ocr;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import omscs.edtech.db.interfaces.OCRFileDataConnector;
import omscs.edtech.db.interfaces.StudentDataConnector;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.OCRFile;
import omscs.edtech.db.model.Student;

/**
 *  @author jle & bryan alcorn
 *  This script will return the ocr file after inserting the OCR image into table OCRFile
 */
class TesseractAPI implements OCRAdapter {

    private OCRFileDataConnector ocrFileDataConnector;
    private StudentDataConnector studentDataConnector;

    public TesseractAPI(){
        ocrFileDataConnector = new OCRFileDataConnector();
        studentDataConnector = new StudentDataConnector();
    }

    public OCRFile ocrRead(Integer classId, Integer assignmentId, File imageFile){
        //https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=how%20to%20store%20a%20tiff%20image%20into%20sqlite%20in%20java
        //http://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android

        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage("amh"); //Trained on handwriting
        OCRFile ocrFile = null;

        try {
            String parsedText = tesseract.doOCR(imageFile);
            byte[] image = Files.readAllBytes(Paths.get(imageFile.getPath()));

            StudentName studentName = findStudentName(parsedText);
            Student student = studentDataConnector.getStudentByName(classId, studentName.getFirstName(), studentName.getLastName());
            boolean matchedWithStudent = student != null && student.getId() > 0;

            //Insert record and image to database
            int i = 0;
            ocrFile = new OCRFile();
            ocrFile.setDbClass(new Class(classId));
            ocrFile.setAssignment(new Assignment(assignmentId));
            if(matchedWithStudent) {
                ocrFile.setStudent(student);
            }
            ocrFile.setOriginalImage(image);
            ocrFile.setParsedText(parsedText);
            ocrFile.setReadableName(matchedWithStudent);

            ocrFileDataConnector.saveOCRFile(ocrFile);

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ocrFile;
    }

    private StudentName findStudentName(String parsedText){
        //Read each line at a time
        String firstName = "", lastName = "";
        Integer endOfLine = 0;
        boolean processFirstName = false, processLastName = false;

        Scanner scanner = new Scanner(parsedText);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            line = line.trim();
            endOfLine = line.length()-1; //-1 to accommodate "a" starting at zero in for the for loop

            // process the line & read each one character at a time
            for (int a = 0; a < line.length();a++) {
                char aChar = line.charAt(a);
                if (aChar == ':'){
//                        System.out.println("Process First Name");
//                        System.out.println(a);
                    processFirstName = true;
                    a++; //the next character should be a space, so we need to skip
                }else {
                    if (processFirstName) {
                        if (aChar != ' ') {
                            firstName = firstName + Character.toString(aChar);
                        } else {
                            //next word
                            processFirstName = false;
                            processLastName = true;
                        }
                    }else {
                        if (processLastName) {
                            //Check if end of line
                            if (a==endOfLine){
                                processLastName = false;
                            }
                            if (aChar != ' ') {
                                lastName = lastName + Character.toString(aChar);
                            } else {
                                //next word
                                processLastName = false;
                            }

                        }
                    }
                }
            }
        }
        scanner.close();

        firstName = firstName.toLowerCase().trim();
        lastName = lastName.toLowerCase().trim();

        return new StudentName(firstName, lastName);
    }

    private class StudentName{
        private final String firstName;
        private final String lastName;

        public StudentName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}
