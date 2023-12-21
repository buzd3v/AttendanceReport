package demo.demo.changeReq;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddChange implements ChangeType {
    private DatePicker datePicker;
    private TextField timeInTextField, timeOutTextField;
    private TextArea textArea;
    public AddChange(DatePicker datePicker, TextField timeInTextField, TextField timeOutTextField, TextArea textArea) {
        this.datePicker = datePicker;
        this.timeInTextField = timeInTextField;
        this.timeOutTextField = timeOutTextField;
        this.textArea = textArea;
    }
    @Override
    public void processChange() {

    }
    @Override
    public boolean validateInput() {
        return datePicker.getValue() != null && !timeInTextField.getText().trim().isEmpty() && !timeOutTextField.getText().trim().isEmpty();
    }
    @Override
    public void clearField() {
        datePicker.setValue(null);
        timeInTextField.clear();
        timeOutTextField.clear();
        textArea.clear();
    }
}
