package omscs.edtech.ocr;

import omscs.edtech.db.model.OCRFile;

import java.io.File;

public class TestAPI {

    public static void main(String[] args){

        OCRAdapter tess = new TesseractAPI();

        OCRFile file = tess.ocrRead(0,0, new File("C:\\Users\\10119365\\IdeaProjects\\CS6460Project\\testfiles\\testdata3.tif"));

        System.out.println(file.getParsedText());

    }
}
