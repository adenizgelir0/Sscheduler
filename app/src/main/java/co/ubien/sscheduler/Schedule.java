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
        Event copy = new Event(E);
        boolean notFound = true;
        while(notFound)
        {
            notFound = false;
            for(Event e : events){
                if(copy.intersects(e)){
                    int duration = E.getDuration();
                    copy.setStart(e.getEnd());
                    copy.setEnd(e.getEnd()+duration);
                    notFound = true;
                }
            }
            int startDay = copy.getStart() / (24*60);
            int endDay = copy.getEnd() / (24*60);
            if(startDay != endDay){
                int duration = E.getDuration();
                copy.setStart(copy.getStart()+duration);
                copy.setEnd(copy.getEnd()+duration);
            }
        }
        events.add(copy);
    }

}
