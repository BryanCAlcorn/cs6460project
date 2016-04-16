package omscs.edtech.ui.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.ui.models.AssignmentModel;
import omscs.edtech.ui.models.GradeAssignmentsModel;
import omscs.edtech.ui.models.OCRFileModel;
import omscs.edtech.ui.models.StudentAssignmentModel;

public class PickStudentDialog {

    private Pane parentBox;
    private GradeAssignmentsModel gradeAssignmentsModel;
    private AssignmentModel currentAssignment;

    public PickStudentDialog(Pane parent, GradeAssignmentsModel gradeAssignmentsModel, AssignmentModel currentAssignment){
        this.parentBox = parent;
        this.gradeAssignmentsModel = gradeAssignmentsModel;
        this.currentAssignment = currentAssignment;
    }

    public StudentAssignmentModel showDialog(OCRFileModel ocrFileModel){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parentBox.getScene().getWindow());
        dialog.setTitle("Match Student and Assignment");
        VBox dialogVbox = new VBox(20);

        dialogVbox.getChildren().add(new Text("We could not match a student with this assignment\n Please select the student:"));

        final ComboBox<StudentAssignmentModel> studentModelComboBox =
                new ComboBox<>(gradeAssignmentsModel.getStudentAssignmentList(currentAssignment));
        dialogVbox.getChildren().add(studentModelComboBox);

        if(ocrFileModel != null) {
            ImageView assignmentImage = new ImageView(ocrFileModel.getImageProperty());
            assignmentImage.setFitHeight(600);
            assignmentImage.setFitWidth(800);
            dialogVbox.getChildren().add(assignmentImage);
        }

        HBox buttonBox = new HBox();
        dialogVbox.getChildren().add(buttonBox);

        Button selectButton = new Button("Select");
        buttonBox.getChildren().add(selectButton);
        SelectEventHandler eventHandler = new SelectEventHandler(dialog, studentModelComboBox);
        selectButton.setOnAction(eventHandler);

        Button cancelButton = new Button("Cancel");
        buttonBox.getChildren().add(cancelButton);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.close();
            }
        });

        Scene dialogScene = new Scene(dialogVbox, 1000,800);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        return eventHandler.getStudentAssignmentModel();
    }

    private class SelectEventHandler implements EventHandler<ActionEvent> {
        StudentAssignmentModel studentAssignmentModel;
        ComboBox<StudentAssignmentModel> studentAssignmentModelComboBox;
        Stage dialog;

        public SelectEventHandler(Stage stage, ComboBox<StudentAssignmentModel> comboBox){
            dialog = stage;
            studentAssignmentModelComboBox = comboBox;
            studentAssignmentModel = null;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            studentAssignmentModel = studentAssignmentModelComboBox.getSelectionModel().getSelectedItem();
            dialog.close();
        }

        public StudentAssignmentModel getStudentAssignmentModel() {
            return studentAssignmentModel;
        }
    }
}
