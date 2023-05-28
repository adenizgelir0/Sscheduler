package co.ubien.sscheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDB {
    private Map<String,Object> eventList = new HashMap<>();
    private Schedule schedule = new Schedule();
    public ScheduleDB(Schedule schedule)
    {
        int i=0;
        for(Event e : schedule.getEvents()){
            String s = "" + e.getStart() + " " +
                    e.getEnd() + " " + e.getName();
            eventList.put("" + (i++),s);
        }
        this.schedule = schedule;
    }
    public ScheduleDB(Map<String,Object> eventList)
    {
        for(Object s : eventList.values())
        {
            String st = (String)s;
            String[] params = st.split(" ");
            int start = Integer.parseInt(params[0]);
            int end = Integer.parseInt(params[1]);
            String name = params[2];
            Event e = new Event(start,end,name);
            schedule.addEvent(e);
        }
        this.eventList = eventList;
    }

    public Map<String,Object> getEventList() {
        return eventList;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
