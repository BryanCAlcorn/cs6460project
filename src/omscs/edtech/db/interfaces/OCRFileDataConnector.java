package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.OCRFile;

import java.sql.SQLException;

public class OCRFileDataConnector {

    SQLiteDBConnection connection;
    Dao<OCRFile, Integer> ocrDao;

    public OCRFileDataConnector(){
        connection = new SQLiteDBConnection(OCRFile.class);
    }

    public boolean saveOCRFile(OCRFile file){
        return connection.basicSave(file);
    }
}
