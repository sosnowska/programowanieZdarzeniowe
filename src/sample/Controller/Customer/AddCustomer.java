package sample.Controller.Customer;

import sample.Model.Model;
import sample.View.CustomerPane;

public class AddCustomer extends Thread {
    Model model;
    CustomerPane customerPane;
    Section section;

    public AddCustomer(Model model, CustomerPane customerPane, Section section ) {
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
                    model.addCustomer(customerPane.getAddFirstName().getText(),customerPane.getAddLastName().getText(),
                            customerPane.getAddPhone().getText(),customerPane.getAddEmail().getText());
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
