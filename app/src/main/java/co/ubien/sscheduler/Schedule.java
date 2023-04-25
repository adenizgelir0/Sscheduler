package co.ubien.sscheduler;

import java.util.ArrayList;

public class Schedule {
    private ArrayList<Event> events;

    public Schedule() {
        events = new ArrayList<>();
    }
    public ArrayList<Event> getEvents(){
        return events;
    }

}
