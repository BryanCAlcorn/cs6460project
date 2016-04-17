package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.OCRFile;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OCRFileDataConnector {

    SQLiteDBConnection connection;
    Dao<OCRFile, Integer> ocrDao;

    public OCRFileDataConnector(){
        connection = new SQLiteDBConnection(OCRFile.class);
    }

    public OCRFile getOCRFile(Integer studentId, Integer assignmentId, Integer classId){
        try {
            ocrDao = connection.getDao();

            Map<String, Object> keyMap = new HashMap<>();

            keyMap.put(OCRFile.STUDENT_ID, studentId);
            keyMap.put(OCRFile.ASSIGNMENT_ID, assignmentId);
            keyMap.put(OCRFile.CLASS_ID, classId);

            List<OCRFile> grades = ocrDao.queryForFieldValues(keyMap);
            connection.destroyConnection();
            if(grades != null && !grades.isEmpty()) {
                return grades.get(0);
            }else {
                return null;
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean saveOCRFile(OCRFile file){
        return connection.basicSave(file);
    }

    public boolean deleteOCRFileByAssignment(Assignment assignment){

        boolean deleteSuccessful = true;
        try{
            ocrDao = connection.getDao();

            DeleteBuilder<OCRFile, Integer> deleteBuilder = ocrDao.deleteBuilder();
            deleteBuilder.where().eq(OCRFile.ASSIGNMENT_ID, assignment.getId());

            deleteSuccessful = deleteBuilder.delete() >= 1;

            connection.destroyConnection();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            deleteSuccessful = false;
        }

        return deleteSuccessful;
    }

    public boolean deleteOCRFileByClass(Class dbClass){
        boolean deleteSuccessful = true;
        try{
            ocrDao = connection.getDao();

            DeleteBuilder<OCRFile, Integer> deleteBuilder = ocrDao.deleteBuilder();
            deleteBuilder.where().eq(OCRFile.CLASS_ID, dbClass.getId());

            deleteSuccessful = deleteBuilder.delete() >= 1;

            connection.destroyConnection();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            deleteSuccessful = false;
        }

        return deleteSuccessful;
    }
}
