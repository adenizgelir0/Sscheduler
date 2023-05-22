package co.ubien.sscheduler;

import java.util.ArrayList;

public class Schedule {
    private ArrayList<Event> events;

    public Schedule(ArrayList<Event> events)
    {
        this.events = events;
    }
    public Schedule() {
        events = new ArrayList<>();
    }
    public ArrayList<Event> getEvents(){
        return events;
    }
    public void addEvent(Event E)
    {
       // Event copy = new Event()
        boolean notFound = true;
        while(notFound)
        {
            for(Event e : events){
              //  if(Event)
            }
        }
    }

}
