package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Controller.MainController;
import sample.Model.Model;
import sample.View.View;


public class Main extends Application {
    private static  final int width=1920;
    private static final int height=1000;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Beuty");
        View view=new View(width,height);
        Model model=new Model();
        MainController mainController=new MainController(view,primaryStage,model);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
