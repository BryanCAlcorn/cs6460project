package omscs.edtech.ui.controls;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import java.text.NumberFormat;

public class IntegerField extends TextField {

    private IntegerProperty integerProperty;

    public IntegerField() {
        super();
        integerProperty = new SimpleIntegerProperty();
        Bindings.bindBidirectional(super.textProperty(), integerProperty, NumberFormat.getInstance());
    }

    public int getIntegerProperty() {
        return integerProperty.getValue();
    }

    public IntegerProperty integerProperty() {
        return integerProperty;
    }

    public void setIntegerProperty(int integerProperty) {
        this.integerProperty.setValue(integerProperty);
    }

    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }
}
