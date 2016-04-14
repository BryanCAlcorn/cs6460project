package omscs.edtech.db.interfaces;


import com.j256.ormlite.dao.Dao;
import omscs.edtech.db.model.EmailTemplate;

import java.sql.SQLException;
import java.util.List;

public class EmailTemplateDataConnector {

    SQLiteDBConnection<EmailTemplate> connection;
    Dao<EmailTemplate, Integer> templateDao;

    public EmailTemplateDataConnector(){
        connection = new SQLiteDBConnection<>(EmailTemplate.class);
    }

    public boolean saveTemplate(EmailTemplate template){
        return connection.basicSave(template);
    }

    public EmailTemplate getTemplateByName(String name){
        try {
            templateDao = connection.getDao();
            List<EmailTemplate> classes = templateDao.queryForEq(EmailTemplate.NAME_COL, name);
            connection.destroyConnection();
            if(classes != null && !classes.isEmpty()) {
                return classes.get(0);
            }else {
                EmailTemplate template = new EmailTemplate();
                template.setName(name);
                return template;
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
