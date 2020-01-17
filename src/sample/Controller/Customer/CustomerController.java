package sample.Controller.Customer;
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
import sample.View.Alert;
import sample.View.CustomerPane;
import sample.View.PositiveTransactionAlert;

public class CustomerController {
    Model model;
    CustomerPane customerPane;


    public void circle() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100,100);
        progressIndicator.setMaxSize(100,100);
        customerPane.getChildren().add(progressIndicator);
        customerPane.setAlignment(progressIndicator,Pos.CENTER);


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                load();
                return null;
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(evt -> {
            customerPane.getChildren().remove(progressIndicator);
        });
        new Thread(task).start();
    }


    public void load(){
        LoadSection loadSection=new LoadSection();
        LoadCustomers loadCustomers=new LoadCustomers(model,loadSection);
        loadCustomers.start();
        boolean running=true;

        while (running) {
            synchronized (loadSection) {
                if (loadSection.isResponse()) {
                    loadSection.setResponse(false);
                    running = false;
                }
            }
        }
        ObservableList<Customer> appointments= FXCollections.observableList(loadSection.getCustomers());
        TableView tableView=customerPane.getTable();
        tableView.setItems(appointments);

    }

    public CustomerController(Model model, CustomerPane customerPane) {
        this.model=model;
        this.customerPane=customerPane;

        circle();

        customerPane.getAddButton().setOnAction(e-> {
            Section section = new Section();
            if (customerPane.getAddFirstName().getText() == null||customerPane.getAddFirstName().getText().trim().isEmpty() || customerPane.getAddLastName().getText() == null
                    || customerPane.getAddLastName().getText().trim().isEmpty()|| customerPane.getAddPhone().getText() == null|| customerPane.getAddPhone().getText().trim().isEmpty()) {
                Alert alert = new Alert("Uzupelnij pola", "Wymagane: imię, nazwisko, numer");
            } else{
                AddCustomer addCustomer = new AddCustomer(model, customerPane, section);
            addCustomer.start();

            boolean running = true;
            while (running) {
                synchronized (section) {
                    if (section.getResponse() == 1) {
                        Alert alert = new Alert("Operacja nie powiodła się", "");
                    }
                    if (section.getResponse() == 2) {
                        circle();
                        try{
                            Thread.sleep(1500);
                        }catch (Exception ex){

                        }
                        PositiveTransactionAlert positiveTransactionAlert = new PositiveTransactionAlert("Operacja przebiegła pomyślnie", "Dodano klienta");
                    }
                    if (section.getResponse() != 0) {
                        section.setResponse(0);
                        running = false;
                    }
                }
            }
            customerPane.getAddFirstName().clear();
            customerPane.getAddLastName().clear();
            customerPane.getAddEmail().clear();
            customerPane.getAddPhone().clear();

        }
        });


        customerPane.getDeleteButton().setOnAction(e-> {
            Section section=new Section();
            DeleteCustomer deleteCustomerController = new DeleteCustomer(model, customerPane,section);
            deleteCustomerController.start();
            boolean running=true;
            while (running) {
                synchronized (section) {
                    if(section.getResponse()==1){
                        customerPane.getDeleteCustomer().clear();
                        Alert alert=new Alert("Nie mozna usunac klienta","Złe id lub klient jest zapisany na wizyte");
                    }
                    if(section.getResponse()==2){
                        customerPane.getDeleteCustomer().clear();
                        circle();
                        try{
                            Thread.sleep(1500);
                        }catch (Exception ex){

                        }
                        PositiveTransactionAlert positiveTransactionAlert=new PositiveTransactionAlert("Operacja przebiegła pomyślnie","Usunięto klienta");
                    }
                    if(section.getResponse()!=0){
                       section.setResponse(0);
                       running=false;
                    }
                }
            }


        }
        );

        customerPane.getEmailCol().setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                        Customer customer=((Customer) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        );
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                model.updateCustomer(customer.getId(),"","","",t.getNewValue());
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

        customerPane.getPhoneCol().setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                Customer customer=((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                );
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        model.updateCustomer(customer.getId(),"","",t.getNewValue(),"");
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

        customerPane.getFirstNameCol().setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                Customer customer=((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                );
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        model.updateCustomer(customer.getId(),t.getNewValue(),"","","");
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

        customerPane.getLastNameCol().setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                Customer customer=((Customer) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                );
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        model.updateCustomer(customer.getId(),"",t.getNewValue(),"","");
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
}





