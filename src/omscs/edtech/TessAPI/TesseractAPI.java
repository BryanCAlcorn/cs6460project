package omscs.edtech.TessAPI;

import java.io.*;
import java.util.*;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import omscs.edtech.db.interfaces.OCRFileDataConnector;
import omscs.edtech.db.interfaces.StudentDataConnector;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.OCRFile;
import omscs.edtech.db.model.Student;

/**
 *  @author jle
 *  This script will return the ocrId after inserting the OCR image into table OCRFile
 *
 */
public class TesseractAPI {

    private OCRFileDataConnector ocrFileDataConnector;
    private StudentDataConnector studentDataConnector;

    public TesseractAPI(){
        ocrFileDataConnector = new OCRFileDataConnector();
        studentDataConnector = new StudentDataConnector();
    }

    public OCRFile ocrRead(Integer classId, File imageFile){
        //https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=how%20to%20store%20a%20tiff%20image%20into%20sqlite%20in%20java
        //http://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android

        Tesseract instance = new Tesseract();

        OCRFile ocrFile = null;

        try {

            //START
            //Call OCR utility to execute Tesseract & read the image
            //
            String parsedText = instance.doOCR(imageFile);
            //System.out.println(result);

            //END
            //Call OCR utility to execute Tesseract & read the image
            //

            //START
            //Parse the OCR image result & check if student name can be located in database.
            // If student name is readable than set readableName = 'Y' else readableName = 'N'
            //
            boolean readableName = false;
            StudentName studentName = findStudentName(parsedText);
//            System.out.println(FirstName);
//            System.out.println(LastName);

            //Search database for student name
            int studentId = 0;
            Student student = studentDataConnector.getStudentByName(classId, studentName.getFirstName(), studentName.getLastName());
            if (student != null && student.getId() > 0){
                readableName = true;
            }else{
                readableName = false;
            }

            //END
            //Parse the OCR image result & check if student name can be located in database.
            // If student name is readable than set readableName = 'Y' else readableName = 'N'
            //

            //START
            //create an instance of the class to access convertImage method & convert image to byte
            //before can insert into database
            //
            byte[] image = null;
            //call method below
            image = convertImage(imageFile.getPath());

            //END


            //Insert record and image to database
            int i = 0;
            ocrFile = new OCRFile();
            ocrFile.setDbClass(new Class(classId));
            ocrFile.setStudent(student);
            ocrFile.setOriginalImage(image);
            ocrFile.setParsedText(parsedText);
            ocrFile.setReadableName(readableName);

            ocrFileDataConnector.saveOCRFile(ocrFile);

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ocrFile;
    }

    private byte[] convertImage(String ImagePath) throws Exception {
        //Convert image to byte and insert to database
        byte[] InsertImage = null;
        File image = new File(ImagePath);
        FileInputStream FileImage = new FileInputStream(image);
        ByteArrayOutputStream ByteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        try {
            for (int readNum; (readNum = FileImage.read(buffer)) != -1;){
                ByteBuffer.write(buffer, 0, readNum);
//                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        InsertImage = ByteBuffer.toByteArray();
        return InsertImage;
        //return byte[] image

        //Read this link for help with retrieving image
        //http://kaninotes.blogspot.com/2011/12/inserting-and-retrieving-images-to.html
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
        private String firstName;
        private String lastName;

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
