package sample.Controller.Customer;

import sample.Model.Model;

public class LoadCustomers extends Thread {
    Model model;
    LoadSection loadSection;
    public LoadCustomers(Model model,LoadSection loadSection){
        this.model=model;
        this.loadSection=loadSection;

    }

    @Override
    public void run() {
        boolean running=true;
        while(running){
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
            synchronized (loadSection){
                loadSection.setCustomers(model.getCustomers());
                loadSection.setResponse(true);
                running=false;

            }
        }

    }
}
