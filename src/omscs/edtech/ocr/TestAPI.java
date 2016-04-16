package omscs.edtech.ocr;

import java.io.File;

public class TestAPI {

    public static void main(String[] args){

        OCRAdapter tess = new TesseractAPI();

        tess.ocrRead(1,2, new File("C:\\Users\\10119365\\IdeaProjects\\CS6460Project\\testfiles\\testdata3.tif"));

    }
}
