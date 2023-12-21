package demo.demo.changeReq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeReqController implements Initializable {
    @FXML
    private ComboBox<String> selectType;
    @FXML
    private VBox addVBox, editVBox, deleteVBox;
    @FXML
    private DatePicker datePicker, datePicker2, datePicker3;
    @FXML
    private TextField timeInTextField, timeInTextField2, timeOutTextField, timeOutTextField2;
    @FXML
    private TextArea textArea, textArea2, textArea3;
    @FXML
    private CheckBox checkBox;
    private ChangeType changeType;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectType.setOnMouseClicked(this::handleComboBoxClick);
        ValidationUtils.addNumericInputListener(timeInTextField);
        ValidationUtils.addNumericInputListener(timeInTextField2);
        ValidationUtils.addNumericInputListener(timeOutTextField);
        ValidationUtils.addNumericInputListener(timeOutTextField2);
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
                changeType = new AddChange(datePicker, timeInTextField, timeOutTextField, textArea);
                break;
            case "EDIT":
                editVBox.setVisible(true);
                editVBox.setManaged(true);
                changeType = new EditChange(datePicker2, timeInTextField2, timeOutTextField2, textArea2);
                break;
            case "DELETE":
                deleteVBox.setVisible(true);
                deleteVBox.setManaged(true);
                changeType = new DeleteChange(datePicker3, checkBox, textArea3);
                break;
            default:
                break;
        }
    }
    @FXML
    private void confirmAction() {
        if(changeType.validateInput()) {
            changeType.processChange();
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
        changeType.clearField();
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
        changeType.clearField();
    }
}
