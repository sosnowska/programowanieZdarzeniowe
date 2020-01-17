package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import sample.Model.*;
import sample.View.Alert;
import sample.View.CalendarPane;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AddCustomerController {
    Model model;
    CalendarPane calendarPane;
    Date date;
    String id;
    String customerId;
    String time;
    String serviceId;

    public AddCustomerController(Model model, CalendarPane calendarPane) {
        try {
            Thread.sleep(100);
        }catch (Exception ex){

        }

        this.model = model;
        this.calendarPane = calendarPane;
        setCustomers();
        LocalDate loc=LocalDate.now();
        calendarPane.getAddDatePicker().setValue(loc);
        setAddDate();
        setBeuticans();
        setServices();
        calendarPane.getAddDatePicker().setOnAction(e -> { setAddDate(); calendarPane.getTimebox().getItems().clear();});
        calendarPane.getBeuticans().setOnAction(e->{ setId();calendarPane.getTimebox().getItems().clear(); });
        calendarPane.getTimebox().setOnAction(event ->{ time=calendarPane.getTimebox().getValue().toString(); });
        calendarPane.getCustomers().setOnAction(e->{ setCustomerId(); });
        calendarPane.getCheckButon().setOnAction(e->{ calendarPane.getTimebox().getItems().clear();
            circle(); });
        calendarPane.getServicebox().setOnAction(event -> setServiceId());
        calendarPane.getAddButton().setOnAction(event -> {
            add();

        });

    }
//pobiera wybrana date
    public void setAddDate() {
        LocalDate today = LocalDate.now();
        LocalDate localDate = calendarPane.getAddDatePicker().getValue();
        if (today.compareTo(localDate) > 0) {
            Alert alert = new Alert("Data przeszła", "");
            calendarPane.getAddDatePicker().setValue(today);
        } else {
            localDate = localDate.plusYears(0).plusMonths(0).plusDays(1);
            date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }
//ustawia wybrana kosmetyczke
    public void setId() {
        String string = (String) calendarPane.getBeuticans().getValue();
        String[] strings = string.split("\\:");
        id = strings[0];

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

    }

    //lista kosemtyczek
    public void setBeuticans() {

        Task<List<Beutican>> task = new Task<List<Beutican>>() {
            @Override
            protected List<Beutican> call() throws Exception {

                List<Beutican> beuticans = model.getBeuticans();
                return beuticans;

            }
        };
        task.setOnSucceeded(evt -> {

            List<Beutican> beuticans = task.getValue();
            for (Beutican b : beuticans) {
                calendarPane.getBeuticans().getItems().add(b.getId() + ": " + b.getFirstName() + " " + b.getFamilyName());
            }

            calendarPane.getBeuticans().setValue(beuticans.get(0).getId() + ": " + beuticans.get(0).getFirstName() + " " + beuticans.get(0).getFamilyName());

        });
        task.setOnFailed(event -> {
            setBeuticans();
                }
        );
        new Thread(task).start();
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

            calendarPane.getServicebox().setValue(services.get(0).getId() + ": " + services.get(0).getName());

        });
        task.setOnFailed(event -> {
                    setServices();
                }
        );
        new Thread(task).start();
    }

    public void add(){
        if(time==null){
            Alert alert=new Alert("Nie wybrano daty","Kliklij na przycisk znajdz i wybierz datę");
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
        }

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
                    FreeTime freeTime = new FreeTime(objects);
                    strings = freeTime.setTime();


                return strings;
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(evt -> {
            System.out.println("hheeeeeeeereeeeee");
            for(String s:task.getValue()){
                calendarPane.getTimebox().getItems().add(s);
            }

            calendarPane.getChildren().remove(progressIndicator);
        });
        task.setOnFailed(event -> {
            System.out.println("faaaail");
            List<String> strings;
            FreeTime freeTime1=new FreeTime();
            freeTime1.setFreeTime();
            strings= freeTime1.getAllFree();
        for(String s:strings){
            calendarPane.getTimebox().getItems().add(s);
        }
        });
        new Thread(task).start();
    }
}
  /*  List<String> strings;

                  try {
                          List<AppointmentObject> objects=model.getAppointments(Long.parseLong(id), date);
        FreeTime freeTime = new FreeTime(objects);
        strings= freeTime.setTime();

        }catch (Exception ex){
        FreeTime freeTime1=new FreeTime();
        freeTime1.setFreeTime();
        strings= freeTime1.getAllFree();
        }
        for(String s:strings){
        calendarPane.getTimebox().getItems().add(s);
        }

        }
        );
        */