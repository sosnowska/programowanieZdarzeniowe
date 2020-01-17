package sample.Controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import sample.Controller.Customer.CustomerController;
import sample.Controller.Customer.Section;
import sample.Model.Model;
import sample.View.*;

public class MainController extends Pane {
    private View view;
    private Model model;
    private int width;
    private  int height;



    public MainController(View view, Stage primaryStage,Model model) {
        this.model=model;
        width=view.getWidth();
        height=view.getHeight();
        this.view=view;
        MainPane mainPane=view.getMainPane();
        CustomerPane customerPane=view.getCustomerPane();

        ServicePane servicePane=view.getServicePane();
        CalendarPane calenderPane=new CalendarPane();
        CustomerPane beuticanPane=new CustomerPane();

        Scene scene=new Scene(mainPane,width,height);

        Scene scene2=new Scene(calenderPane,width,height);
        Scene scene3=new Scene(customerPane,width,height);
        Scene scene4=new Scene(servicePane,width,height);
        Scene scene5=new Scene(beuticanPane,width,height);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        mainPane.getHomeButton().setOnAction(e -> primaryStage.setScene(scene));
        mainPane.getCustomerButton().setOnAction(e-> {
            CustomerController customerController=new CustomerController(model,customerPane);
            primaryStage.setScene(scene3);
        });
        mainPane.getAppointmentButton().setOnAction(e ->{
            CalendarController calenderController=new CalendarController(model,calenderPane);
            primaryStage.setScene(scene2);
        } );

        mainPane.getServiceButton().setOnAction(e->{
            ServiceController serviceController=new ServiceController(model,servicePane);
            primaryStage.setScene(scene4);

        });
        mainPane.getBeauticanButton().setOnAction(e->{
            BeuticanController beuticanController=new BeuticanController(model,beuticanPane);
            primaryStage.setScene(scene5);
        });

        servicePane.getHomeButton().setOnAction(e->primaryStage.setScene(scene));

        beuticanPane.getHomeButton().setOnAction(e->{ primaryStage.setScene(scene);
        });
        customerPane.getHomeButton().setOnAction(e -> primaryStage.setScene(scene));
        calenderPane.getHomeButton().setOnAction(e->primaryStage.setScene(scene));


    }

}

