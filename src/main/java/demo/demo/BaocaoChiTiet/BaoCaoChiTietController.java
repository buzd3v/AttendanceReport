package demo.demo.BaocaoChiTiet;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class BaoCaoChiTietController implements Initializable {

    @FXML
    private TableView<String> TableBaoCao;

    @FXML
    private TableColumn<String, String> Ngay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Tạo dữ liệu cho cột "Ngày"
        ObservableList<String> ngayData = FXCollections.observableArrayList("Ngày", "Ngày 1", "Ngày 2", "Ngày 3");
        Ngay.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));


        // Thiết lập dữ liệu cho TableView
        ObservableList<String> rowData = FXCollections.observableArrayList("Sáng", "Chiều", "Đi muộn", "Về Sớm");
        TableBaoCao.setItems(rowData);

    }
}
