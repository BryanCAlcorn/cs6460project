package omscs.edtech.TessAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

            String result = instance.doOCR(imageFile);
            System.out.println(result);

/*
            //NEED TO FINISH CODING IN THIS SECTION
            //Check if OCR image can read student name by reading the result. If student name is readable than
            //set readableName = 'Y' else readableName = 'N'
            String readableName = null;

            //parse result & check if student name existing in Students table
            //readableName = "Y";
            //readableName = "N";


            //create an instance of the class to access ConvertImage method & convert image to byte
            //before can insert into database
            TesseractAPI CallCovertImage = new TesseractAPI();
            byte[] image = null;
            //call method below
            image = CallCovertImage.ConvertImage(FilePath);

            //Connect to Database
            SQLiteDBConnection Connect = new SQLiteDBConnection();
            Connection c = null;
            c = Connect.getConnection();

            //Insert record and image to database
            int i = 0;
            String sql = "INSERT INTO OCRFile (classId, ocrFile, parsedText, readableImage) VALUES(?,?,?,?)";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, classId);
            pstmt.setBytes(2, image);
            pstmt.setString(3, result);
            pstmt.setString(4, readableName);

            i = pstmt.executeUpdate();

            if (i > 0) {
                //System.out.print("Image Uploaded");
                c.commit();

                //Get the ocrId and return

                //String lastRowId = "Select Last_Insert_Rowid() From OCRFile";
                //pstmt = c.prepareStatement(lastRowId);
                Statement stmt = null;
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT seq FROM sqlite_sequence WHERE name='Students';");
                while (rs.next()) {
                    ocrId = rs.getInt("seq");
                    System.out.println("ocrId = " + ocrId);
                }
                rs.close();
                stmt.close();
            }
            pstmt.close();
            c.close();
*/
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
                System.out.println("read " + readNum + " bytes,");
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
