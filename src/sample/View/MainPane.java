package sample.View;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.*;


public class MainPane extends Pane {
    private  Button serviceButton;
    private Button homeButton;
    private Button appointmentButton;
    private Button customerButton;
    private Button beauticanButton;
    private int width;
    private int height;


    public Button getServiceButton() {
        return serviceButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getAppointmentButton() {
        return appointmentButton;
    }

    public Button getCustomerButton() {
        return customerButton;
    }

    public Button getBeauticanButton() {
        return beauticanButton;
    }

    public MainPane(int width, int height) {
        this.width=width;
        this.height=height;
        HBox hBox=new HBox();
        homeButton=new Button();
        homeButton.setPrefHeight(80);
        homeButton.setPrefWidth(150);
        homeButton.setText("Home");
        hBox.getChildren().add(homeButton);
        appointmentButton=new Button();
        appointmentButton.setPrefWidth(150);
        appointmentButton.setPrefHeight(80);
        appointmentButton.setText("Kalendarz wizyt");
        hBox.getChildren().add(appointmentButton);
        serviceButton = new Button();
        serviceButton.setPrefWidth(150);
        serviceButton.setPrefHeight(80);
        serviceButton.setText("Usługi");
        hBox.getChildren().add(serviceButton);
        customerButton=new Button();
        customerButton.setPrefWidth(150);
        customerButton.setPrefHeight(80);
        customerButton.setText("Klienci");
        hBox.getChildren().add(customerButton);
        beauticanButton=new Button();
        beauticanButton.setPrefHeight(80);
        beauticanButton.setPrefWidth(150);
        beauticanButton.setText("Pracownicy");
        hBox.getChildren().add(beauticanButton);

        Pane pane=new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
        Text name=new Text("Salon Beuty");
        name.setFill(Color.HOTPINK);
        name.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 100));
        name.relocate(650, 300);
        name.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(name);

        VBox vbox=new VBox();
        vbox.getChildren().add(hBox);
        vbox.getChildren().add(pane);
        getChildren().add(vbox);

    }

}
