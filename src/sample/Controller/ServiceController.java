package sample.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Model.Customer;
import sample.Model.Model;
import sample.Model.Service;
import sample.View.Alert;
import sample.View.PositiveTransactionAlert;
import sample.View.ServicePane;

import java.math.BigDecimal;
import java.util.List;

public class ServiceController {
    Model model;
    ServicePane servicePane;

    public ServiceController(Model model, ServicePane servicePane) {
        this.servicePane = servicePane;
        this.model = model;
        circle();
        servicePane.getDeleteButton().setOnAction(e -> delete());
        servicePane.getAddButton().setOnAction(e->add());

        servicePane.getNameCol().setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Service, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Service, String> t) {
                        Service service=((Service) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        );
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                model.updateService(service.getId(),t.getNewValue());
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
        if (servicePane.getAddName().getText() == null||servicePane.getAddName().getText().trim().isEmpty() || servicePane.getAddPrice().getText() == null
                || servicePane.getAddPrice().getText().trim().isEmpty()|| servicePane.getAddTime().getText() == null|| servicePane.getAddTime().getText().trim().isEmpty()) {
            Alert alert = new Alert("Uzupelnij pola", "Wymagane: nazwa,czas,cena");
        }else if(Integer.parseInt(servicePane.getAddTime().getText())%30!=0){
            Alert alert=new Alert("Popraw dane","Usługi trwają wielokrotności pół godziny");
        }
        else{
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    model.addService(servicePane.getAddName().getText(),Long.parseLong(servicePane.getAddTime().getText()),
                            new BigDecimal(servicePane.getAddPrice().getText()));

                    return null;
                }

            };
            task.setOnSucceeded(evt -> {
                circle();
                servicePane.getAddTime().clear();
                servicePane.getAddPrice().clear();
                servicePane.getAddName().clear();
                try{
                    Thread.sleep(1500);
                }catch (Exception e){

                }
                PositiveTransactionAlert positiveTransactionAlert = new PositiveTransactionAlert("Operacja przebiegla pomyslnie", "Dodano usługę");

            });
            task.setOnFailed(event -> {
                servicePane.getAddTime().clear();
                servicePane.getAddPrice().clear();
                servicePane.getAddTime().clear();
                Alert alert = new Alert("Nie mozna dodać usługi", "Zły format pola");
            });
            new Thread(task).start();

        }
        }

    public void delete(){

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                model.removeService(Long.parseLong(servicePane.getDeleteService().getText()));
                return null;
            }
        };
        task.setOnSucceeded(evt -> {
            circle();
            servicePane.getDeleteService().clear();
            try{
                Thread.sleep(1500);
            }catch (Exception e){

            }
            PositiveTransactionAlert positiveTransactionAlert = new PositiveTransactionAlert("Operacja przebiegla pomyslnie", "Usunieto usługę");

        });
        task.setOnFailed(event -> {
            servicePane.getDeleteService().clear();
            Alert alert = new Alert("Nie mozna usunac usługi", "Złe id lub klient umówił się na tą usługę");
        });
        new Thread(task).start();

    }
    public void load(){
        Task<List<Service>> task = new Task<List<Service>>() {
            @Override
            protected List<Service> call() throws Exception {
                List<Service> services=model.getServices();
                return services;

            }
        };
        task.setOnSucceeded(evt -> {
            List<Service> services=task.getValue();
            ObservableList<Service> services1= FXCollections.observableList(services);
            TableView tableView=servicePane.getTable();
            tableView.setItems(services1);



        });
        task.setOnFailed(event -> System.out.println(task.getMessage())
                );

        new Thread(task).start();

    }
    public void circle() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100,100);
        progressIndicator.setMaxSize(100,100);
        servicePane.getChildren().add(progressIndicator);
        servicePane.setAlignment(progressIndicator, Pos.CENTER);


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    Thread.sleep(1000);

                }catch (Exception e){

                }

                load();
                return null;
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(evt -> {
            servicePane.getChildren().remove(progressIndicator);
        });
        new Thread(task).start();
    }
}

