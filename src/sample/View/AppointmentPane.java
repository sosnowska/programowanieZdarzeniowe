package sample.View;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
public class AppointmentPane extends Pane {
    private Button homeButton;
    private Button calenderButton;
    private Button bookButton;
    private CalendarPane calenderPane;

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getCalenderButton() {
        return calenderButton;
    }

    public CalendarPane getCalenderPane() {
        return calenderPane;
    }

    public Button getBookButton() {
        return bookButton;
    }

    public AppointmentPane(int width, int height){
        HBox hBox=new HBox();
        homeButton=new Button();
        homeButton.setPrefHeight(80);
        homeButton.setPrefWidth(150);
        homeButton.setText("Home");
        hBox.getChildren().add(homeButton);
        bookButton=new Button();
        bookButton.setText("Umow wizyte");
        bookButton.setPrefWidth(150);
        bookButton.setPrefHeight(80);
        hBox.getChildren().add(bookButton);
        calenderButton=new Button();
        calenderButton.setPrefHeight(80);
        calenderButton.setPrefWidth(150);
        calenderButton.setText("Kalendarz wizyt");
        hBox.getChildren().add(calenderButton);
        Pane pane=new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
        Text name=new Text("wizyta");
        pane.getChildren().add(name);
        VBox vbox=new VBox();
        vbox.getChildren().add(hBox);
        vbox.getChildren().add(pane);
        getChildren().add(vbox);
        calenderPane=new CalendarPane();



    }
}
