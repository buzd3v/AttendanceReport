package demo.demo.report;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportController {
    @FXML
    private TableView<ObservableList<StringProperty>> reportTable;
    @FXML
    private TableColumn<ObservableList<StringProperty>, String> nameColumn;
    @FXML
    private TableColumn<ObservableList<StringProperty>, String> idColumn;
    @FXML
    private TableColumn<ObservableList<StringProperty>, String> unitColumn;
    @FXML
    private TableColumn<ObservableList<StringProperty>, String> monthColumn;
    @FXML
    private TableColumn<ObservableList<StringProperty>, String> totalTimeColumn;
    @FXML
    private TableColumn<ObservableList<StringProperty>, String> totalOverTimeColumn;

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private ComboBox<String> classComboBox1;

    @FXML
    private Button reportButton;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> data.getValue().get(0));
        idColumn.setCellValueFactory(data -> data.getValue().get(1));
        unitColumn.setCellValueFactory(data -> data.getValue().get(2));
        monthColumn.setCellValueFactory(data -> data.getValue().get(3));
        totalTimeColumn.setCellValueFactory(data -> data.getValue().get(4));
        totalOverTimeColumn.setCellValueFactory(data -> data.getValue().get(5));
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tkxdpm", "root", "");
            if (connection!=null){
                ObservableList<String> classOptions =
                        FXCollections.observableArrayList("worker 1", "worker 2", "officer 1", "officer 2"); // Replace with your actual class options
                classComboBox.setItems(classOptions);
                ObservableList<String> monthOptions =
                        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                                "11", "12");
                classComboBox1.setItems(monthOptions);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void handleReportButtonClick(ActionEvent event) {
        String selectedClass = classComboBox.getValue();
        String selectedMonth = classComboBox1.getValue();

        if (selectedClass != null && selectedMonth != null) {
            String tableName = selectedClass.startsWith("worker") ? "report_worker" : "report_officer";
            String query = "SELECT * FROM " + tableName + " WHERE unit = ? AND month = ?";

            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, selectedClass);
                preparedStatement.setString(2, selectedMonth);
                resultSet = preparedStatement.executeQuery();

                ObservableList<ObservableList<StringProperty>> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    ObservableList<StringProperty> row = FXCollections.observableArrayList();
                    row.add(new SimpleStringProperty(resultSet.getString("name")));
                    row.add(new SimpleStringProperty(String.valueOf(resultSet.getInt("id"))));
                    row.add(new SimpleStringProperty(resultSet.getString("unit")));
                    row.add(new SimpleStringProperty(String.valueOf(resultSet.getInt("month"))));
                    row.add(new SimpleStringProperty(String.valueOf(resultSet.getInt("total_time"))));

                    // Check the selectedClass and fetch the appropriate column
                    if (selectedClass.startsWith("worker")) {
                        row.add(new SimpleStringProperty(String.valueOf(resultSet.getInt("total_over_time"))));
                        totalOverTimeColumn.setText("Total Over Time");
                    } else {
                        row.add(new SimpleStringProperty(String.valueOf(resultSet.getInt("total_late_time"))));
                        totalOverTimeColumn.setText("Total Late");
                    }

                    data.add(row);
                }
                reportTable.setItems(data);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select both class and month.");
        }
    }

}
