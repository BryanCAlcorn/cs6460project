package omscs.edtech.ui.controls;

import com.sun.javafx.webkit.Accessor;
import com.sun.org.apache.xerces.internal.util.EncodingMap;
import com.sun.webkit.WebPage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

class FeedbackToolbar implements EmailCustomToolbar{

    HTMLEditor currentEditor;

    @Override
    public String getEmailName() {
        return EmailControlConstants.EMAIL_FEEDBACK_NAME;
    }

    @Override
    public Button[] getCustomButtons() {

        Button[] customButtons = new Button[5];

        Button nameButton = new Button("Student Name");

        nameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertText(currentEditor, EmailControlConstants.TOKEN_STUDENT_NAME);
            }
        });

        Button assignmentButton = new Button("Assignment Name");

        assignmentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertText(currentEditor, EmailControlConstants.TOKEN_ASSIGNMENT_NAME);
            }
        });

        Button classButton = new Button("Class Name");

        classButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertText(currentEditor, EmailControlConstants.TOKEN_CLASS_NAME);
            }
        });

        Button gradeButton = new Button("Student Grade");

        gradeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertText(currentEditor, EmailControlConstants.TOKEN_GRADE);
            }
        });

        Button feedbackButton = new Button("Feedback");

        feedbackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insertText(currentEditor, EmailControlConstants.TOKEN_FEEDBACK);
            }
        });

        customButtons[0] = nameButton;
        customButtons[1] = assignmentButton;
        customButtons[2] = classButton;
        customButtons[3] = gradeButton;
        customButtons[4] = feedbackButton;

        return customButtons;
    }

    @Override
    public void setEditor(HTMLEditor editor) {
        currentEditor = editor;
    }

    private static void insertText(HTMLEditor editor, String text){
        if(editor != null){
            WebView webView = (WebView)editor.lookup("WebView");
            WebPage webPage = Accessor.getPageFor(webView.getEngine());
            webPage.executeCommand("insertText", text);
        }
    }
}
