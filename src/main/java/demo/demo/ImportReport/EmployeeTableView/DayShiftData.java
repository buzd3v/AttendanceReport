package demo.demo.ImportReport.EmployeeTableView;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DayShiftData {
    private final SimpleStringProperty id;
    private final SimpleStringProperty type;
    private final SimpleListProperty<Double> data;

    public DayShiftData(String id, String type, List<Double> data) {
        this.id = new SimpleStringProperty(id);
        this.type = new SimpleStringProperty(type);
        this.data = new SimpleListProperty<>(FXCollections.observableArrayList(data));
    }

    // Getter methods for properties
    public SimpleStringProperty idProperty() {
        return id;
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public SimpleListProperty<Double> dataProperty() {
        return data;
    }

    // Getter methods for non-property fields
    public String getId() {
        return id.get();
    }

    public String getType() {
        return type.get();
    }

    public ObservableList<Double> getData() {
        return data.get();
    }
}