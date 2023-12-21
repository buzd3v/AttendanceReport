package demo.demo.changeReq;

import demo.demo.DB.DatabaseSingleton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        try (Connection connection = DatabaseSingleton.getInstance().getConnection()) {
            String sql = "INSERT INTO request_timekeeping_information (id, staff_id, date, description, status)" +
                    "VALUES (1, 123," + datePicker.getValue() + ", xóa thông tin chấm công ngày này ghi chú: " +
                    textArea.getText() +", '0')";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
