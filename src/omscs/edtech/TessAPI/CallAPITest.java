package omscs.edtech.TessAPI;

/**
 * Created by jle on 3/29/2016.
 */

import omscs.edtech.TessAPI.TesseractAPI;

public class CallAPITest {

    public static void main( String args[] ) {
        //create an instance of the class to access ConvertImage method & convert image to byte
        //before can insert into database
        TesseractAPI CallTesseractAPI = new TesseractAPI();
        int ocrId = 0, classId = 1;
        String path = "C:\\testdata3.tif";
        //call method below
        ocrId = CallTesseractAPI.OCRRead(classId,path);

    }

}
