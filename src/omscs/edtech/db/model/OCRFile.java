package omscs.edtech.db.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "OCRFile")
public class OCRFile {

    public static final String STUDENT_ID = "studentId";
    public static final String CLASS_ID = "classId";
    public static final String ASSIGNMENT_ID = "assignmentId";

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = STUDENT_ID)
    private Student student;
    @DatabaseField(canBeNull = false, foreign = true, columnName = CLASS_ID)
    private Class dbClass;
    @DatabaseField(canBeNull = false, foreign = true, columnName = ASSIGNMENT_ID)
    private Assignment assignment;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
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

    public Integer getStudentId(){
        return student.getId();
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getDbClass() {
        return dbClass;
    }

    public Integer getClassId(){
        return dbClass.getId();
    }

    public void setDbClass(Class dbClass) {
        this.dbClass = dbClass;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Integer getAssignmentId(){
        return assignment.getId();
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
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
