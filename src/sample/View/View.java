package sample.View;

public class View {

    private int width;
    private int height;
    private CustomerPane customerPane=new CustomerPane();
    private ServicePane servicePane=new ServicePane();
    private StartPane mainPane=new StartPane(width,height);
    private AppointmentPane appointmentPane =new AppointmentPane(width,height);
    private CustomerPane beuticanPane=new CustomerPane();

    public CustomerPane getBeuticanPane() {
        return beuticanPane;
    }

    public CustomerPane getCustomerPane() {
        return customerPane;
    }

    public ServicePane getServicePane() {
        return servicePane;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public View(int width, int height) {
        this.width = width;
        this.height = height;

    }

    public StartPane getMainPane() {
        return mainPane;
    }

    public AppointmentPane getAppointmentPane() {
        return appointmentPane;
    }
}

