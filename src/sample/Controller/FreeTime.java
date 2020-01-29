package sample.Controller;

import sample.Model.AppointmentObject;

import java.util.LinkedList;
import java.util.List;

public class FreeTime {
    private List<AppointmentObject> appointments;
    private  List<String> freeTime;
    private List<String> allFree=new LinkedList<>();
    private List<Time> times;
    private int serviceTime;
    public FreeTime(){

    }

    public List<String> getAllFree() {
        return allFree;
    }

    public FreeTime(List<AppointmentObject> appointments,int serviceTime) {
        this.appointments = appointments;
        this.serviceTime=serviceTime;
    }

    public void setFreeTime(){
        times=new LinkedList<>();
        int hour=9;
        int minutes=0;
        for(int i=0;i<19;i++) {
            if(i%2==0) {
                Time time=new Time();
                minutes=0;
                time.setTime(Integer.toString(hour));
                times.add(time);

            }
            else {
                Time time=new Time();
                minutes=30;
                time.setTime(Integer.toString(hour)+":"+Integer.toString(minutes));
                hour++;
                times.add(time);
            }
        }
        for(Time t: times){
            allFree.add(t.getTime());
        }


    }
    public List<String>  setTime(){
        setFreeTime();

        //List<AppointmentObject> appointments = model.getAppointments(Long.parseLong(id), date);

        for(AppointmentObject a:appointments){

            String from=a.getTimeFrom();
            String to=a.getTimeTo();
            if(from.startsWith("0")){
                from="9";
            }
            if(from.contains(":00"))
                from=from.replace(":00","");
            if(to.contains(":00"))
                to=to.replace(":00","");
            //System.out.println(from);
            //System.out.println(to);
            setBusy(from,to);
        }


        listTime(serviceTime);
        //showtime();

        return freeTime;
    }

    public void setBusy(String time,String end){

        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).getTime().equals(time)) {
                times.get(i).setFree(false);
                for (int j = i + 1; j < times.size(); j++) {
                    if (!times.get(j).getTime().equals(end)) {
                        times.get(j).setFree(false);
                    } else {
                        break;
                    }
                }

            }
        }


    }
    public void listTime(int serviceTime){
        int count=serviceTime/30;
        boolean check=true;
        freeTime=new LinkedList<>();
        for (int i = 0; i < times.size()-count; i++) {
            if (times.get(i).getFree()) {
                if (i+count-2<times.size()){
                    for (int j = i + 1; j < i+count; j++) {
                        if (!times.get(j).getFree())
                        {

                            j=i+count-1;
                            check=false;
                        }
                    }
                    if(check) {

                        freeTime.add(times.get(i).getTime());
                    }
                    check=true;
                }

            }
        }

    }
    public void showtime(){
        for(Time time:times){
            System.out.println(time.getTime()+time.getFree());
        }
    }
}
