package sample.View;

import javafx.scene.control.Alert;

public class PositiveTransactionAlert {
    public PositiveTransactionAlert(String head, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.show();
    }
}
