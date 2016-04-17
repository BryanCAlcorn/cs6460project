package omscs.edtech.ui.controls;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import omscs.edtech.ui.models.AssignmentModel;
import omscs.edtech.ui.models.StudentAssignmentModel;

public class SetGradeEventHandler implements EventHandler<TableColumn.CellEditEvent<StudentAssignmentModel, Number>> {

    private AssignmentModel currentAssignment;
    public SetGradeEventHandler(AssignmentModel currentAssignment){
        this.currentAssignment = currentAssignment;
    }

    @Override
    public void handle(TableColumn.CellEditEvent<StudentAssignmentModel, Number> studentAssignmentModel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Extra Credit");
        alert.setHeaderText("Extra Credit");
        alert.setContentText("Are you sure you want to give " +
                studentAssignmentModel.getRowValue().getStudentNameProperty().getValue() +
                " extra credit?");

        boolean extraCreditAssigned =
                studentAssignmentModel.getNewValue().intValue() > currentAssignment.getMaxPoints();

        if(extraCreditAssigned){
            alert.showAndWait();
        }

        if(!extraCreditAssigned || alert.getResult() == ButtonType.OK){
            studentAssignmentModel.getTableView().getItems().get(
                    studentAssignmentModel.getTablePosition().getRow())
                    .setStudentGrade(studentAssignmentModel.getNewValue());
        }else{
            studentAssignmentModel.getTableView().getItems().get(
                    studentAssignmentModel.getTablePosition().getRow())
                    .setStudentGrade(studentAssignmentModel.getOldValue());
        }

        studentAssignmentModel.getTableView().refresh();
    }
}
