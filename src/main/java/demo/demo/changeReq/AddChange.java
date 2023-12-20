package demo.demo.changeReq;

import javafx.scene.control.TextField;

public class AddChange implements ChangeType {

    private DatePicker datePicker;
    private TextField timeInTextField;
    
    @Override
    public void processChange() {

    }

    @Override
    public boolean validateInput() {
        return return datePicker.getValue() != null && !timeInTextField.getText().trim().isEmpty() && !timeOutTextField.getText().trim().isEmpty();
    }
}
