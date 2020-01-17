package sample.View;

public class Alert {
    public Alert(String head, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.show();
    }
}
