package omscs.edtech.ocr;

public class OCRAdapterFactory {
    public static OCRAdapter getInstance(){
        return new TesseractAPI();
    }
}
