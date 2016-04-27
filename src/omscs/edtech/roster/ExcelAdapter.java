package omscs.edtech.roster;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.StreamHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ExcelAdapter {

    private Workbook wb;

    public ExcelAdapter(File excelFile) throws InvalidFormatException, IOException{
        wb = WorkbookFactory.create(excelFile);
    }

    public int getNumStudents(int sheet, int startRow){
        Sheet currentSheet = wb.getSheetAt(sheet);

        return currentSheet.getLastRowNum() - startRow + 1;
    }

    public String[] getCellValuesAsStrings(DataRowLocation cellLocation){
        Sheet currentSheet = wb.getSheetAt(cellLocation.getSheet());
        List<String> values = new ArrayList<>();
        Row row = currentSheet.getRow(cellLocation.getRow());
        for (int colNum : cellLocation.getCols()) {
            Cell cell = row.getCell(colNum);
//            CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
//            System.out.print(cellRef.formatAsString());
//            System.out.print(" - ");

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    values.add(cell.getRichStringCellValue().getString());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        values.add(cell.getDateCellValue().toString());
                    } else {
                        values.add(String.valueOf(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    values.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    values.add(cell.getCellFormula());
                    break;
                default:
                    values.add(null);
            }
        }
        return values.toArray(new String[values.size()]);
    }

}
