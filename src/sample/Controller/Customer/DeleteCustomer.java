package sample.Controller.Customer;

import sample.Model.Model;
import sample.View.CustomerPane;

public class DeleteCustomer extends Thread {
    Model model;
    CustomerPane customerPane;
    Section section;

    public DeleteCustomer(Model model, CustomerPane customerPane, Section section ) {
        this.model = model;
        this.customerPane = customerPane;
        this.section=section;

    }

    @Override
    public void run() {

        boolean running=true;
        while(running){
            synchronized (section){
                try {
                    model.removeCustomer(Long.parseLong(customerPane.getDeleteCustomer().getText()));
                    section.setResponse(2);

                }catch (Exception e){
                    section.setResponse(1);
                }
                finally {
                    running=false;
                }
            }
        }



    }
}
