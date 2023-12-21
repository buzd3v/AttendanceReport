package demo.demo.changeReq;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddChange implements ChangeType {

    private DatePicker datePicker;
    private TextField timeInTextField;
    private TextField timeOutTextField; // Thêm khai báo cho timeOutTextField

    @Override
    public void processChange() {
        // Thêm logic xử lý khi cần thiết
    }

    @Override
    public boolean validateInput() {
        return datePicker.getValue() != null && !timeInTextField.getText().trim().isEmpty() && !timeOutTextField.getText().trim().isEmpty();
    }
}
