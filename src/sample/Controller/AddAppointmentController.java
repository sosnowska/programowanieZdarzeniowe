package sample.Controller;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import sample.Model.*;
import sample.View.Alert;
import sample.View.CalendarPane;
import sample.View.PositiveTransactionAlert;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AddAppointmentController {
    private Model model;
    private CalendarPane calendarPane;
    private Date date;
    private String id;
    private String customerId;
    private String time;
    private String serviceId;
    private int serviceTime;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddAppointmentController(Model model, CalendarPane calendarPane) {
        try {
            Thread.sleep(100);
        }catch (Exception ex){

        }

        this.model = model;
        this.calendarPane = calendarPane;
        setCustomers();
        setServices();


    }
    public void TimeboxSetOnAction(){
        if(calendarPane.getTimebox().getValue()!=null)
            time=calendarPane.getTimebox().getValue().toString();
    }
    public void findTimeSetOnAction(){
        calendarPane.getTimebox().getItems().clear();
        LocalDate today = LocalDate.now();
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (today.compareTo(localDate) > 0) {
            Alert alert = new Alert("Data przeszła", "");
            calendarPane.getAddDatePicker().setValue(today);

        }
        else {
            circle();
           // calendarPane.getTimebox().setValue(calendarPane.getTimebox().getItems().get(0));
        }
    }

    public void setServiceTime(){
        List<Service> services=model.getServices();
        for(Service s: services){
            if(serviceId.equals(s.getId().toString())){
                serviceTime=s.getTime().intValue();
            }
        }

    }
    //ustawia wybranego klienta
    public void setCustomerId() {
        String string = (String) calendarPane.getCustomers().getValue();
        String[] strings = string.split("\\:");
        customerId = strings[0];

    }
    //wybrana usluga
    public void setServiceId() {
        String string = (String) calendarPane.getServicebox().getValue();
        String[] strings = string.split("\\:");
        serviceId = strings[0];
        setServiceTime();
        calendarPane.getTimebox().getItems().clear();

    }

//lista klientow
    public void setCustomers() {

        Task<List<Customer>> task = new Task<List<Customer>>() {
            @Override
            protected List<Customer> call() throws Exception {

                List<Customer> cutomers= model.getCustomers();
                return cutomers;

            }
        };
        task.setOnSucceeded(evt -> {

            List<Customer> customers = task.getValue();
            for (Customer c : customers) {

                calendarPane.getCustomers().getItems().add(c.getId() + ": " + c.getFirstName() + " " + c.getFamilyName());
            }

           calendarPane.getCustomers().setValue(customers.get(0).getId() + ": " + customers.get(0).getFirstName() + " " + customers.get(0).getFamilyName());

        });
        task.setOnFailed(event -> {
                    setCustomers();
                }
        );
        new Thread(task).start();
    }
    //lista uslug
    public void setServices() {

        Task<List<Service>> task = new Task<List<Service>>() {
            @Override
            protected List<Service> call() throws Exception {

                List<Service> services= model.getServices();
                return services;

            }
        };
        task.setOnSucceeded(evt -> {

           List<Service>services= task.getValue();
            for (Service s:services) {

                calendarPane.getServicebox().getItems().add(s.getId() + ": " + s.getName());
            }
            if(calendarPane.getServicebox().getItems().size()>0)
                calendarPane.getServicebox().setValue(services.get(0).getId() + ": " + services.get(0).getName());

        });
        task.setOnFailed(event -> {
                    setServices();
                }
        );
        new Thread(task).start();
    }

    public void add(){
        LocalDate today = LocalDate.now();
        LocalDate localDate = calendarPane.getAddDatePicker().getValue();
        if(time==null){
            Alert alert=new Alert("Nie wybrano godziny","Kliklij na przycisk znajdz i wybierz godzine");
        }
        else {
            java.sql.Time time1;
            time=time.trim();
            if(!time.contains(":")){

                time1=new java.sql.Time(Integer.parseInt(time),0,0);
            }
            else {
                String[] hm=time.split("\\:");
                time1=new java.sql.Time(Integer.parseInt(hm[0]),Integer.parseInt(hm[1]),0);
            }


            model.addAppointment( Long.valueOf(customerId),Long.valueOf(serviceId),Long.valueOf(id),time1,java.sql.Date.valueOf( date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
            PositiveTransactionAlert alert=new PositiveTransactionAlert("Dodano wizytę","");
        }
        calendarPane.getTimebox().getItems().clear();

    }
    //ustawienie dostepnych godzin
    public void circle() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100,100);
        progressIndicator.setMaxSize(100,100);
        calendarPane.getChildren().add(progressIndicator);
        calendarPane.setAlignment(progressIndicator, Pos.CENTER);


        Task< List<String>> task = new Task< List<String>>() {
            @Override
            protected  List<String> call() throws Exception {
                try{
                    Thread.sleep(1000);

                }catch (Exception e){

                }
                List<String> strings;
                    List<AppointmentObject> objects=model.getAppointments(Long.parseLong(id), date);
                    FreeTime freeTime = new FreeTime(objects,serviceTime);
                    strings = freeTime.setTime();


                return strings;
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(evt -> {
            if(task.getValue().size()==0) {
                Alert alert = new Alert("Brak dostępnych godzin", "Wybierz inny dzień");
            }

            for(String s:task.getValue()){
                calendarPane.getTimebox().getItems().add(s);
            }

            calendarPane.getChildren().remove(progressIndicator);
            if(calendarPane.getTimebox().getItems().size()>0)
            calendarPane.getTimebox().setValue(calendarPane.getTimebox().getItems().get(0));
        });
        task.setOnFailed(event -> {
            List<String> strings;
            FreeTime freeTime1=new FreeTime();
            freeTime1.setFreeTime();
            strings= freeTime1.getAllFree();
        for(String s:strings){
            calendarPane.getTimebox().getItems().add(s);
        }
            calendarPane.getChildren().remove(progressIndicator);
        });
        new Thread(task).start();
    }
}
