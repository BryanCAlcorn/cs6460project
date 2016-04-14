package omscs.edtech.ui.controls;

public class EmailToolbarFactory {
    public static EmailCustomToolbar getFeedbackToolbar(){
        return new FeedbackToolbar();
    }

    public static EmailCustomToolbar getMissingAssignmentToolbar(){
        return new MissingAssignmentToolbar();
    }
}
