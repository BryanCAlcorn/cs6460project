package omscs.edtech.TessAPI;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import omscs.edtech.db.SQLiteDBConnection;

/**
 *  @author jle
 *  This script will return the ocrId after inserting the OCR image into table OCRFile
 *
 */


public class TesseractAPI {

    //    public static void main(Integer classId, String ImgPath ){
    public int OCRRead(Integer classId, String ImgPath ){
        //File imageFile = new File("<path of your image>");
        //File imageFile = new File(args[1]);
        //https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=how%20to%20store%20a%20tiff%20image%20into%20sqlite%20in%20java
        //http://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android
        int ocrId = 0;
        //String FilePath = "C:\\testdata2.tif";
        //File imageFile = new File("C:\\testdata2.tif");
        File imageFile = new File(ImgPath);
        //Tesseract instance = Tesseract.
        //Tesseract instance = Tesseract.getInstance(); //
        Tesseract instance = new Tesseract();

        try {

            //START
            //Call OCR utility to execute Tesseract & read the image
            //
            String result = instance.doOCR(imageFile);
            //System.out.println(result);

            //END
            //Call OCR utility to execute Tesseract & read the image
            //

            //START
            //Parse the OCR image result & check if student name can be located in database.
            // If student name is readable than set readableName = 'Y' else readableName = 'N'
            //
            String readableName = "N";

            //Read each line at a time
            String FirstName = "", LastName = "";
            Integer EndofLine = 0;
            boolean ProcessFirstName = false, ProcessLastName = false;

            Scanner scanner = new Scanner(result);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                line = line.trim();
                EndofLine = line.length()-1; //-1 to accommodate "a" starting at zero in for the for loop

                // process the line & read each one character at a time
                for (int a = 0; a < line.length();a++) {
                    char aChar = line.charAt(a);
                    if (aChar == ':'){
//                        System.out.println("Process First Name");
//                        System.out.println(a);
                        ProcessFirstName = true;
                        a++; //the next character should be a space, so we need to skip
                    }else {
                        if (ProcessFirstName) {
                            if (aChar != ' ') {
                                FirstName = FirstName + Character.toString(aChar);
                            } else {
                                //next word
                                ProcessFirstName = false;
                                ProcessLastName = true;
                            }
                        }else {
                            if (ProcessLastName) {
                                //Check if end of line
                                if (a==EndofLine){
                                    ProcessLastName = false;
                                }
                                if (aChar != ' ') {
                                    LastName = LastName + Character.toString(aChar);
                                } else {
                                    //next word
                                    ProcessLastName = false;
                                }

                            }
                        }
                    }
                }
            }
            scanner.close();

            //Connect to Database
            SQLiteDBConnection Connect = new SQLiteDBConnection();
            Connection c = null;
            c = Connect.getConnection();

            FirstName = FirstName.toLowerCase();
            LastName = LastName.toLowerCase();
            FirstName = FirstName.trim();
            LastName = LastName.trim();

//            System.out.println(FirstName);
//            System.out.println(LastName);

            //Search database for student name
            int studentId = 0;
            Statement stdsql = null;
            stdsql = c.createStatement();

            String prestdsql = "SELECT studentId FROM Students WHERE firstName = " + "'" + FirstName + "'" + " and lastName = " + "'" + LastName + "'";

            ResultSet stdresult = stdsql.executeQuery(prestdsql);
            while ( stdresult.next() ) {
                int stdId = stdresult.getInt("studentId");
                studentId = stdId;
            }
            stdresult.close();
            stdsql.close();

//            System.out.println( "studnetId = " + studentId);
            if (studentId > 0){
                readableName = "Y";
            }else{
                readableName = "N";
            }

            //END
            //Parse the OCR image result & check if student name can be located in database.
            // If student name is readable than set readableName = 'Y' else readableName = 'N'
            //

            //START
            //create an instance of the class to access ConvertImage method & convert image to byte
            //before can insert into database
            //
            TesseractAPI CallCovertImage = new TesseractAPI();
            byte[] image = null;
            //call method below
            image = CallCovertImage.ConvertImage(ImgPath);

            //END


            //Insert record and image to database
            int i = 0;
            String sql = "INSERT INTO OCRFile (classId, studentId, ocrFile, parsedText, readableImage) VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, classId);
            pstmt.setInt(2, studentId);
            pstmt.setBytes(3, image);
            pstmt.setString(4, result);
            pstmt.setString(5, readableName);

            i = pstmt.executeUpdate();

            if (i > 0) {
                //System.out.print("Image Uploaded");
                c.commit();

                //Get the ocrId and return

                //String lastRowId = "Select Last_Insert_Rowid() From OCRFile";
                //pstmt = c.prepareStatement(lastRowId);
                Statement stmt = null;
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name='OCRFile';");
                while (rs.next()) {
                    ocrId = rs.getInt("seq");
//                    System.out.println("ocrId = " + ocrId);
                }
                rs.close();
                stmt.close();
            }
            pstmt.close();
            c.close();

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ocrId;
    }

    public byte[] ConvertImage(String ImagePath) throws Exception {
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



}
