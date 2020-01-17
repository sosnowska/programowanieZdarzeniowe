package sample.Controller.Customer;

import sample.Model.Customer;

import java.util.List;

public class LoadSection {
    private List<Customer> customers;
    private boolean response;

    public LoadSection() {
        this.response = false;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

}
