package demo.demo.changeReq;

import demo.demo.database.databaseConnector;
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

        try (Connection connection = databaseConnector.connect()) {
            String sql = "SELECT * FROM request_timekeeping_information";
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
