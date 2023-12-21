package demo.demo.changeReq;

import demo.demo.DB.DatabaseSingleton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        try (Connection connection = DatabaseSingleton.getInstance().getConnection()) {
            String sql = "INSERT INTO request_timekeeping_information (id, staff_id, date, description, status)" +
                    "VALUES (1, 123," + datePicker.getValue() + ", thêm thông tin: từ" +timeInTextField.getText()+
                    " đến " + timeOutTextField.getText() + "ghi chú: " + textArea.getText() +", '0')";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
