package omscs.edtech.ocr;

import omscs.edtech.db.model.OCRFile;

import java.io.File;

public interface OCRAdapter {
    OCRFile ocrRead(Integer classId, File imageFile);
}
