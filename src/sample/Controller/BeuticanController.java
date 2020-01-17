package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Model.Beutican;

import sample.Model.Model;
import sample.View.Alert;
import sample.View.CustomerPane;
import sample.View.PositiveTransactionAlert;

import java.math.BigDecimal;
import java.util.List;

public class BeuticanController {
    CustomerPane beuticanPane;
    Model model;

    public BeuticanController(Model model,CustomerPane beuticanPane){
        this.model=model;
        this.beuticanPane=beuticanPane;
        circle();
        beuticanPane.getDeleteButton().setOnAction(e -> delete());
        beuticanPane.getAddButton().setOnAction(e->add());
        beuticanPane.getEmailCol().setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Beutican, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Beutican, String> t) {
                        Beutican beutican=((Beutican) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        );
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                model.updateBeutican(beutican.getId(),"","","",t.getNewValue());
                                return null;
                            }
                        };
                        task.setOnSucceeded(evt -> {
                            circle();
                        });
                        new Thread(task).start();

                    }
                }
        );

        beuticanPane.getPhoneCol().setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Beutican, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Beutican, String> t) {
                        Beutican beutican=((Beutican) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        );
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                model.updateBeutican(beutican.getId(),"","",t.getNewValue(),"");
                                return null;
                            }
                        };
                        task.setOnSucceeded(evt -> {
                            circle();
                        });
                        new Thread(task).start();

                    }
                }
        );

        beuticanPane.getFirstNameCol().setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Beutican, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Beutican, String> t) {
                       Beutican beutican=((Beutican) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        );
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                model.updateBeutican(beutican.getId(),t.getNewValue(),"","","");
                                return null;
                            }
                        };
                        task.setOnSucceeded(evt -> {
                            circle();
                        });
                        new Thread(task).start();

                    }
                }
        );

        beuticanPane.getLastNameCol().setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Beutican, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Beutican, String> t) {
                        Beutican beutican=((Beutican) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        );
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                model.updateBeutican(beutican.getId(),"",t.getNewValue(),"","");
                                return null;
                            }
                        };
                        task.setOnSucceeded(evt -> {
                            circle();
                        });
                        new Thread(task).start();

                    }
                }
        );
    }
    public void add(){
        if (beuticanPane.getAddFirstName().getText() == null||beuticanPane.getAddFirstName().getText().trim().isEmpty() || beuticanPane.getAddLastName().getText() == null
                || beuticanPane.getAddLastName().getText().trim().isEmpty()|| beuticanPane.getAddPhone().getText() == null|| beuticanPane.getAddPhone().getText().trim().isEmpty()) {
            Alert alert = new Alert("Uzupelnij pola", "Wymagane: imię, nazwisko, numer");
        } else{
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    model.addBeutican(beuticanPane.getAddFirstName().getText(),beuticanPane.getAddLastName().getText(),
                            beuticanPane.getAddPhone().getText(),beuticanPane.getAddEmail().getText());
                    return null;
                }

            };
            task.setOnSucceeded(evt -> {
                circle();
                beuticanPane.getAddFirstName().clear();
                beuticanPane.getAddLastName().clear();
                beuticanPane.getAddEmail().clear();
                beuticanPane.getAddPhone().clear();
                try{
                    Thread.sleep(1500);
                }catch (Exception e){

                }
                PositiveTransactionAlert positiveTransactionAlert = new PositiveTransactionAlert("Operacja przebiegla pomyslnie", "Dodano usługę");

            });
            task.setOnFailed(event -> {
                beuticanPane.getAddFirstName().clear();
                beuticanPane.getAddLastName().clear();
                beuticanPane.getAddEmail().clear();
                beuticanPane.getAddPhone().clear();
                Alert alert = new Alert("Nie mozna dodać usługi", "Zły format pola");
            });
            new Thread(task).start();

        }
    }

    public void delete(){

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                model.removeBeutican(Long.parseLong(beuticanPane.getDeleteCustomer().getText()));
                return null;
            }
        };
        task.setOnSucceeded(evt -> {
            circle();
            beuticanPane.getDeleteCustomer().clear();

            try{
                Thread.sleep(1500);
            }catch (Exception e){

            }
            PositiveTransactionAlert positiveTransactionAlert = new PositiveTransactionAlert("Operacja przebiegla pomyslnie", "Usunieto pracownika");

        });
        task.setOnFailed(event -> {
            beuticanPane.getDeleteCustomer().clear();
            Alert alert=new Alert("Nie mozna usunac klienta","Złe id lub klient jest zapisany na wizyte");
        });
        new Thread(task).start();

    }
    public void load(){
        Task<List<Beutican>> task = new Task<List<Beutican>>() {
            @Override
            protected List<Beutican> call() throws Exception {
                List<Beutican> beuticans=model.getBeuticans();
                return beuticans;

            }
        };
        task.setOnSucceeded(evt -> {
            ObservableList<Beutican> beuticans= FXCollections.observableList(task.getValue());
            TableView tableView=beuticanPane.getTable();
            tableView.setItems(beuticans);

        });

        new Thread(task).start();

    }
    public void circle() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(100,100);
        progressIndicator.setPrefSize(100,100);
        beuticanPane.getChildren().add(progressIndicator);
        beuticanPane.setAlignment(progressIndicator, Pos.CENTER);


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    Thread.sleep(1000);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                load();
                return null;
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(evt -> {
            beuticanPane.getChildren().remove(progressIndicator);
        });
        new Thread(task).start();
    }
}


