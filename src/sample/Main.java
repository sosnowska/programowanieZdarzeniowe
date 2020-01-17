package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.MainController;
import sample.Model.Model;
import sample.View.MainPane;
import sample.View.View;


public class Main extends Application {
    private static  final int width=1900;
    private static final int height=1000;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Beuty");
        View view=new View(width,height);
        Model model=new Model();
        MainController mainController=new MainController(view,primaryStage,model);

    }


    public static void main(String[] args) {
        //Model model=new Model();
       // model.addCustomer("Barbara", "Szyman", "666 666 666", "szyman@xx.pl");
        //model.removeCustomer(1l);
       // model.updateCustomer(4l,"basia@wp.pl");
       // model.addService("przedłużanie rzęs 1:1",120, new BigDecimal(150.00));
        //model.addService("przedłużanie rzęs 2D",150, new BigDecimal(150.00));
       // model.updateService(2l,"przedłużanie rzęs 2D",150, new BigDecimal(180.00));
       // model.addService("d",120, new BigDecimal(150.00));
       // model.removeService(3l);
       // model.addBeutican("Klara", "Nowak", "666 666 666");
        //model.removeCustomer(1l);
        // model.updateBeutican(1l,"Klara", "Nowak", "777 666 666");

        // Time time=new Time(16,45,0);

     //model.addAppointment(4L,2L,2L,time,java.sql.Date.valueOf("2020-01-25"));


//model.getAppointment(1L,java.sql.Date.valueOf("2020-01-25"));
        //model.close();

        launch(args);
    }
}
