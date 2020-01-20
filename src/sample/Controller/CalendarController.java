package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import sample.Model.AppointmentObject;
import sample.Model.Beutican;
import sample.Model.Model;
import sample.View.Alert;
import sample.View.CalendarPane;
import sample.View.PositiveTransactionAlert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CalendarController {
    private Model model;
    private CalendarPane calenderPane;
    private String id;
    private Date date;

    public CalendarController(Model model, CalendarPane calenderPane) {
        this.model = model;
        this.calenderPane=calenderPane;
        LocalDate loc=LocalDate.now();
        calenderPane.getDatePicker().setValue(loc);
        getDate();
        setChoiceBox();
        circle();
        AddCustomerController addCustomerController=new AddCustomerController(model,calenderPane);
        calenderPane.getChoiceBox().setOnAction(e->{
            getId();
        });
        calenderPane.getDatePicker().setOnAction(e->{
           getDate();
        }
        );
        calenderPane.getCalenderButton().setOnAction(event ->{ circle();}
        );
        calenderPane.getDeleteButton().setOnAction(e -> delete());


    }
    //laduje liste pracownikow
    public void  setChoiceBox(){

        Task<List<Beutican>> task = new Task<List<Beutican>>() {
            @Override
            protected List<Beutican> call() throws Exception {
                List<Beutican> beuticans=model.getBeuticans();
                return beuticans;

            }
        };
        task.setOnSucceeded(evt -> {
            List<Beutican> beuticans=task.getValue();
            for (Beutican b:beuticans){
                calenderPane.getChoiceBox().getItems().add(b.getId()+": "+b.getFirstName()+" "+b.getFamilyName());
            }

            calenderPane.getChoiceBox().setValue(beuticans.get(0).getId()+": "+beuticans.get(0).getFirstName()+" "+beuticans.get(0).getFamilyName());
        });
        task.setOnFailed(event ->
                setChoiceBox());

        new Thread(task).start();

    }
    //pobiera wybrane
    public void getId(){
        String string=(String)calenderPane.getChoiceBox().getValue();
        String[] strings=string.split("\\:");
        id=strings[0];

    }
    //pobiera wybrana date
    public void getDate(){
        LocalDate localDate =calenderPane.getDatePicker().getValue();
        localDate = localDate.plusYears(0).plusMonths(0).plusDays(1);
         date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public void load(){
        List<AppointmentObject> appointments=model.getAppointments(Long.parseLong(id),date);
        ObservableList<AppointmentObject> appointments1= FXCollections.observableList(appointments);
        TableView<AppointmentObject> tableView=calenderPane.getTable();
        tableView.setItems(appointments1);
    }
    public void circle() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100,100);
        progressIndicator.setMaxSize(100,100);
        calenderPane.getChildren().add(progressIndicator);
        calenderPane.setAlignment(progressIndicator, Pos.CENTER);


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
            calenderPane.getChildren().remove(progressIndicator);
        });
        task.setOnFailed(evt -> {
            calenderPane.getChildren().remove(progressIndicator);
        });
        new Thread(task).start();
    }
    public void delete(){

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                model.removeAppointment(Long.parseLong(calenderPane.getDeleteAppoinment().getText()));
                return null;
            }
        };
        task.setOnSucceeded(evt -> {
            circle();
            calenderPane.getDeleteAppoinment().clear();
            try{
                Thread.sleep(1500);
            }catch (Exception e){

            }
            PositiveTransactionAlert positiveTransactionAlert = new PositiveTransactionAlert("Operacja przebiegla pomyslnie", "Usunieto usługę");

        });
        task.setOnFailed(event -> {
            calenderPane.getDeleteAppoinment().clear();
            Alert alert = new Alert("Nie mozna usunac wizyty", "Złe id ");
        });
        new Thread(task).start();

    }

}


