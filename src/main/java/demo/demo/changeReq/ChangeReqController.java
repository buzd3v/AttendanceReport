package demo.demo.changeReq;

import com.jfoenix.controls.JFXBadge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ChangeReqController implements Initializable {
    @FXML
    private ComboBox<String> selectType;
    @FXML
    private VBox addVBox;
    @FXML
    private VBox editVBox;
    @FXML
    private VBox deleteVBox;
    @FXML
    private DatePicker datePicker, datePicker2, datePicker3;
    @FXML
    private TextField timeInTextField, timeInTextField2, timeInTextField3;
    @FXML
    private TextField timeOutTextField, timeOutTextField2, timeOutTextField3;
    @FXML
    private TextArea textArea, textArea2, textArea3;
    @FXML
    private CheckBox checkBox;

    @FXML
    public void onChoiceChanged() {
        String selectedChangeType = selectType.getValue();
        System.out.println("Bạn đã chọn: " + selectedChangeType);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectType.setOnMouseClicked(this::handleComboBoxClick);
        timeInTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*[:]?\\d*")) {
                timeInTextField.setText(oldValue);
            }
        });
        timeOutTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*[:]?\\d*")) {
                timeOutTextField.setText(oldValue);
            }
        });
        timeInTextField2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*[:]?\\d*")) {
                timeInTextField2.setText(oldValue);
            }
        });
        timeOutTextField2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*[:]?\\d*")) {
                timeOutTextField2.setText(oldValue);
            }
        });

    }
    private void handleComboBoxClick(MouseEvent event) {
        // Mở danh sách lựa chọn khi mũi tên được nhấp
        selectType.show();
    }
    @FXML
    private void handleComboBoxSelection(ActionEvent event) {
        // Ẩn tất cả các VBox trước khi hiển thị VBox tương ứng với lựa chọn
        addVBox.setVisible(false);    addVBox.setManaged(false);
        editVBox.setVisible(false);   editVBox.setManaged(false);
        deleteVBox.setVisible(false); deleteVBox.setManaged(false);

        // Hiển thị VBox tương ứng với lựa chọn trong ComboBox
        switch (selectType.getValue()) {
            case "ADD":
                addVBox.setVisible(true);
                addVBox.setManaged(true);
                break;
            case "EDIT":
                editVBox.setVisible(true);
                editVBox.setManaged(true);
                break;
            case "DELETE":
                deleteVBox.setVisible(true);
                deleteVBox.setManaged(true);
                break;
            default:
                break;
        }
    }
    @FXML
    private void confirmAction() {
        LocalDate selectedDate = datePicker.getValue();
        String timeIn = timeInTextField.getText().trim();
        String timeOut = timeOutTextField.getText().trim();
        if(selectedDate != null && !timeIn.isEmpty() && !timeOut.isEmpty()) {
            showSuccessAlert();
        } else {
            showWarningAlert("Vui lòng điền đầy đủ thông tin.");
        }
    }
    @FXML
    private void confirmDelAction() {
        LocalDate selectedDate = datePicker3.getValue();
        boolean isCheckBoxSelected = checkBox.isSelected();

        if(selectedDate != null && isCheckBoxSelected) {
            showSuccessAlert();
        } else {
            showWarningAlert("Vui lòng điền đầy đủ thông tin.");
        }
    }
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Gửi yêu cầu thành công!");
        alert.showAndWait();
        clearField();
    }
    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void clearField() {
        textArea.clear(); textArea2.clear(); textArea3.clear();
        timeInTextField.clear(); timeInTextField2.clear();
        timeOutTextField.clear(); timeOutTextField2.clear();
        datePicker.setValue(null); datePicker2.setValue(null); datePicker3.setValue(null);
        checkBox.setSelected(false);
    }
}
