package omscs.edtech.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "EmailTemplates")
public class EmailTemplate {

    public static final String NAME_COL = "templateName";

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String template;
    @DatabaseField(columnName = NAME_COL)
    private String name;

    public EmailTemplate(){

    }

    public EmailTemplate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
