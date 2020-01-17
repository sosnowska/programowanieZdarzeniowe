package sample.View;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.Model.Customer;


public class CustomerPane extends StackPane {
    private Button homeButton;
    private TableView table;
    private Button addButton;
    private TextField addFirstName;
    private TextField addLastName;
    private TextField addEmail;
    private TextField addPhone;
    private TextField deleteCustomer;
    private  Button deleteButton;
    private TableColumn firstNameCol;
    private TableColumn lastNameCol;
    private TableColumn phoneCol;
    private TableColumn emailCol;

    public TableColumn getFirstNameCol() {
        return firstNameCol;
    }

    public TableColumn getLastNameCol() {
        return lastNameCol;
    }

    public TableColumn getPhoneCol() {
        return phoneCol;
    }

    public TableColumn getEmailCol() {
        return emailCol;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public TextField getDeleteCustomer() {
        return deleteCustomer;
    }

    public Button getAddButton() {
        return addButton;
    }

    public TextField getAddFirstName() {
        return addFirstName;
    }

    public TextField getAddLastName() {
        return addLastName;
    }

    public TextField getAddEmail() {
        return addEmail;
    }

    public TextField getAddPhone() {
        return addPhone;
    }

    public TableView getTable() {
        return table;
    }

    public Button getHomeButton() {
        return homeButton;
    }
    public CustomerPane() {
        HBox hBox=new HBox();
        homeButton=new Button();
        homeButton.setPrefHeight(80);
        homeButton.setPrefWidth(150);
        homeButton.setText("Home");
        hBox.getChildren().add(homeButton);
        table=new TableView();
        table.setEditable(true);
        firstNameCol = new TableColumn("Imie");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        lastNameCol = new TableColumn("Nazwisko");
        lastNameCol.setMinWidth(200);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("familyName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        emailCol = new TableColumn("e-mail");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        phoneCol = new TableColumn("numer telefonu");
        phoneCol.setMinWidth(200);
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("phoneNumber"));
        TableColumn idCol = new TableColumn("id");
       // idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<Customer, Long>("id"));

        table.getColumns().addAll(idCol,firstNameCol, lastNameCol,phoneCol,emailCol);
        table.setMaxWidth(900);

        addFirstName = new TextField();
        addFirstName.setPromptText("Imie");
        addFirstName.setPrefWidth(150);
        addLastName = new TextField();
        addLastName.setPrefWidth(150);
        addLastName.setPromptText("Nazwisko");
        addEmail = new TextField();
        addEmail.setPrefWidth(200);
        addEmail.setPromptText("E-mail");
        addPhone = new TextField();
        addPhone.setPrefWidth(150);
        addPhone.setPromptText("numer");
        addButton = new Button("Dodaj");
        Label label=new Label("Dodaj osobę:");
        Label label1=new Label("Usuń osobę:");
        HBox hb=new HBox();
        hb.getChildren().addAll(addFirstName, addLastName, addPhone,addEmail, addButton);
        hb.setSpacing(3);
        deleteCustomer=new TextField();
        deleteCustomer.setPrefWidth(100);
        deleteCustomer.setPromptText("Id");
        deleteButton=new Button("Usuń");
        HBox hBox1=new HBox();
        hBox1.getChildren().addAll(deleteCustomer,deleteButton);
        VBox vbox = new VBox();
        vbox.setSpacing(50);
        vbox.getChildren().addAll(hBox, table,label, hb,label1,hBox1);
        vbox.setAlignment(Pos.TOP_CENTER);



        getChildren().add(vbox);


    }
}
