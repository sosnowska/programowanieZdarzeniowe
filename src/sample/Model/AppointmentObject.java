package sample.Model;

import java.sql.Date;
import java.sql.Time;

public class AppointmentObject {
    private String id;
    private String timeFrom;
    private String timeTo;
    private String name;
    private String customer;
    private int time;

    public String deleteMinutes(String timeFrom){
        String[] strings= timeFrom.split(":");
        String string=strings[0]+":"+strings[1];

        return string;
    }
    public AppointmentObject(String id, String timeFrom, String  timeTo, String name, String customer,String time) {
        this.id = id;
        this.timeFrom = deleteMinutes(timeFrom);
        this.timeTo = deleteMinutes(timeTo);
        this.name = name;
        this.customer = customer;
        this.time=Integer.parseInt(time);
    }


    public String getId() {
        return id;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getName() {
        return name;
    }

    public String getCustomer() {
        return customer;
    }
}
