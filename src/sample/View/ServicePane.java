package sample.View;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.Model.Service;

import java.math.BigDecimal;

public class ServicePane extends StackPane {
    private Button homeButton;
    private TableView table;
    private TableColumn idCol;
    private TableColumn nameCol;
    private TableColumn priceCol;
    private TableColumn timeCol;

    private TextField addName;
    private TextField addPrice;
    private TextField addTime;
    private Button deleteButton;
    private TextField deleteService;
    private Button addButton;

    public Button getDeleteButton() {
        return deleteButton;
    }

    public TextField getDeleteService() {
        return deleteService;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public TableView getTable() {
        return table;
    }

    public TableColumn getIdCol() {
        return idCol;
    }

    public TableColumn getNameCol() {
        return nameCol;
    }

    public TableColumn getPriceCol() {
        return priceCol;
    }

    public TableColumn getTimeCol() {
        return timeCol;
    }



    public TextField getAddName() {
        return addName;
    }

    public TextField getAddPrice() {
        return addPrice;
    }

    public TextField getAddTime() {
        return addTime;
    }

    public ServicePane() {
        HBox hBox=new HBox();
        homeButton=new Button();
        homeButton.setPrefHeight(80);
        homeButton.setPrefWidth(150);
        homeButton.setText("Home");
        hBox.getChildren().add(homeButton);
        table=new TableView();
        table.setMaxWidth(700);
        table.setEditable(true);
        nameCol = new TableColumn("Nazwa");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Service, String>("name"));
       nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        idCol = new TableColumn("id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<Service, Long>("id"));

        priceCol = new TableColumn("Cena");

        priceCol.setMinWidth(200);
        priceCol.setCellValueFactory(
                new PropertyValueFactory<Service, String>("price"));
        //priceCol.setCellFactory(TextFieldTableCell.forTableColumn());


        timeCol = new TableColumn("Czas trwania(min)");
        timeCol.setMinWidth(200);
        timeCol.setCellValueFactory(
                new PropertyValueFactory<Service, Long>("time"));
       // timeCol.setCellFactory(TextFieldTableCell.forTableColumn());


        table.getColumns().addAll(idCol,nameCol,timeCol,priceCol);

        addName = new TextField();
        addName.setPromptText("Nazwa");
        addName.setPrefWidth(150);
        addPrice = new TextField();
        addPrice.setPrefWidth(150);
        addPrice.setPromptText("Cena");
        addTime = new TextField();
        addTime.setPrefWidth(150);
        addTime.setPromptText("Czas trwania");
        addButton=new Button("Dodaj");
        Label label=new Label("Dodaj usuługę:");
        Label label1=new Label("Usuń usługę:");
        HBox hb=new HBox();
        hb.getChildren().addAll(addName, addTime, addPrice,addButton);
        hb.setSpacing(3);
        deleteService=new TextField();
        deleteService.setPrefWidth(100);
        deleteService.setPromptText("Id");
        deleteButton=new Button("Usuń");
        HBox hBox1=new HBox();
        hBox1.getChildren().addAll(deleteService,deleteButton);
        final VBox vbox = new VBox();
        vbox.setSpacing(50);
        vbox.getChildren().addAll(hBox, table,label, hb,label1,hBox1);
        getChildren().add(vbox);
        vbox.setAlignment(Pos.TOP_CENTER);


    }
}
