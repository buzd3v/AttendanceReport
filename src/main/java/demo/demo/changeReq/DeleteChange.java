package demo.demo.changeReq;

import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DeleteChange implements ChangeType{
    private DatePicker datePicker;
    private CheckBox checkBox;
    private TextArea textArea;
    public DeleteChange(DatePicker datePicker, CheckBox checkBox, TextArea textArea) {
        this.datePicker = datePicker;
        this.checkBox = checkBox;
        this.textArea = textArea;
    }
    @Override
    public void processChange() {
        //Xử lý các hành động Delete
    }
    @Override
    public boolean validateInput() {
        return datePicker.getValue() != null && checkBox.isSelected();
    }
    @Override
    public void clearField() {
        datePicker.setValue(null);
        checkBox.setSelected(false);
        textArea.clear();
    }
}
