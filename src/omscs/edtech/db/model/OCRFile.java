package omscs.edtech.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "OCRFile")
public class OCRFile {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    private Student student;
    @DatabaseField(canBeNull = false, foreign = true)
    private Class dbClass;
    @DatabaseField
    private byte[] originalImage;
    @DatabaseField
    private String parsedText;
    @DatabaseField
    private boolean readableName;

    public OCRFile(){

    }

    public OCRFile(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getDbClass() {
        return dbClass;
    }

    public void setDbClass(Class dbClass) {
        this.dbClass = dbClass;
    }

    public byte[] getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(byte[] originalImage) {
        this.originalImage = originalImage;
    }

    public String getParsedText() {
        return parsedText;
    }

    public void setParsedText(String parsedText) {
        this.parsedText = parsedText;
    }

    public boolean isReadableName() {
        return readableName;
    }

    public void setReadableName(boolean readableName) {
        this.readableName = readableName;
    }
}
