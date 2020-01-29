package sample.View;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.*;

import sample.Model.AppointmentObject;


public class CalendarPane extends StackPane{
    private TableView<AppointmentObject> table;

    private Button homeButton;
    private ChoiceBox choiceBox;
    private ChoiceBox beuticans;
    private ChoiceBox customers;
    private ChoiceBox timebox;
    private ChoiceBox servicebox;
    private DatePicker addDatePicker;
    private Button checkButon;


    private DatePicker datePicker;
    private TextField  deleteAppoinment;
    private Button deleteButton;
    private Button addButton;

    public Button getCheckButon() {
        return checkButon;
    }

    public ChoiceBox getServicebox() {
        return servicebox;
    }

    public TextField getDeleteAppoinment() {
        return deleteAppoinment;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }

    public ChoiceBox getCustomers() {
        return customers;
    }

    public ChoiceBox getTimebox() {
        return timebox;
    }

    public DatePicker getAddDatePicker() {
        return addDatePicker;
    }

    public Button getAddButton() {
        return addButton;
    }

    public TableView<AppointmentObject> getTable() {
        return table;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public CalendarPane() {
        HBox hBox=new HBox();
        homeButton=new Button();
        homeButton.setPrefHeight(80);
        homeButton.setPrefWidth(150);
        homeButton.setText("Home");
        hBox.getChildren().add(homeButton);

        datePicker = new DatePicker();
        choiceBox = new ChoiceBox();
        choiceBox.setMinWidth(200);

        table=new TableView<AppointmentObject>();
        table.setEditable(true);

        TableColumn hourCol = new TableColumn("Od godziny");
        hourCol.setMinWidth(150);
        hourCol.setCellValueFactory(
                new PropertyValueFactory<AppointmentObject, String>("timeFrom")
        );
        TableColumn hourtoCol = new TableColumn("Do godziny");
        hourtoCol.setMinWidth(150);
        hourtoCol.setCellValueFactory(
                new PropertyValueFactory<AppointmentObject, String>("timeTo")
        );

        TableColumn idCol = new TableColumn("id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
        new PropertyValueFactory<AppointmentObject, String>("id"));


        TableColumn nameCol = new TableColumn("Usługa");
        nameCol.setMinWidth(300);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<AppointmentObject, String>("name"));

        TableColumn customerCol = new TableColumn("Klient");
        customerCol.setMinWidth(300);
        customerCol.setCellValueFactory(
                new PropertyValueFactory<AppointmentObject, String>("customer"));


        table.getColumns().addAll(idCol,hourCol,hourtoCol,nameCol,customerCol);
        table.setMaxWidth(1000);

        deleteAppoinment=new TextField();
        deleteAppoinment.setPrefWidth(100);
        deleteAppoinment.setPromptText("Id");
        deleteButton=new Button("Anuluj");
        HBox hBox2=new HBox();
        hBox2.getChildren().addAll(deleteAppoinment,deleteButton);
        checkButon=new Button("Znajdź dostępne godziny:");

        addButton =new Button("Umów");
        addDatePicker = new DatePicker();
        beuticans = new ChoiceBox();
        beuticans.setMinWidth(200);
        customers=new ChoiceBox();
        customers.setMinWidth(200);
        timebox=new ChoiceBox();
        timebox.setMinWidth(150);
        servicebox=new ChoiceBox();
        servicebox.setMinWidth(200);
        HBox hBox3=new HBox();
        Label label=new Label("Umów wizytę:");
        Label label1=new Label("Usługa:");
        hBox3.getChildren().addAll(customers,servicebox,checkButon,timebox,addButton);



        VBox vbox=new VBox();
        HBox hBox1=new HBox();
        hBox1.getChildren().addAll(datePicker,choiceBox);
        vbox.setSpacing(50);
        vbox.getChildren().addAll(hBox,hBox1,table,label,hBox3,hBox2);
        vbox.setAlignment(Pos.TOP_CENTER);
        getChildren().add(vbox);
        //label.setAlignment(Pos.BOTTOM_LEFT);



    }
}
