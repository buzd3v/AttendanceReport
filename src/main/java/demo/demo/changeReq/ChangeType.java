package demo.demo.changeReq;

import javafx.scene.control.TextField;

public interface ChangeType {
    void processChange();
    boolean validateInput();
    void clearField();
    static void addNumericInputListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*[:]?\\d*")) {
                textField.setText(oldValue);
            }
        });
    }
}
