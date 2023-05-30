package co.ubien.sscheduler;
import android.graphics.Color;

import java.io.Serializable;

public class Event {
    private int start;
    private int end;
    private String name;
    private int color;

    public Event(int startMins, int endMins, String name, int color, int day) {
        this.start = startMins + day*24*60;
        this.end = endMins + day*24*60;
        this.name = name;
        this.color = color;
    }
    public Event(int startMins, int endMins, String name, int color) {
        this.start = startMins;
        this.end = endMins;
        this.name = name;
        this.color = color;
    }
    public Event(Event E){
        this.start = E.getStart();
        this.end = E.getEnd();
        this.name = E.getName();
        this.color = E.getColor();
    }
    public  Event(int start, int end,String name){
        this.start = start;
        this.end = end;
        this.name = name;
        this.color = ColorUtil.strToColor(name);
    }
    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }
    public int getStartMins(){
        return start % (24*60);
    }
    public int getEndMins(){
        return end % (24*60);
    }

    public int getEnd() {
        return end;
    }

    public int getDay() {
        return start / (24*60);
    }

    public void setDay(int day) {
        this.start %= 24*60;
        this.start += 24*60*day;
        this.end %= 24*60;
        this.end += 24*60*day;
    }
    public void setStart(int start){
        this.start = start % (60*24*7);
    }
    public void setEnd(int end){
        this.end = end % (60*24*7);
    }

    public boolean intersects(Event E)
    {
        /*
          start1 <= C < end1
          start2 <= C < end2
        */
       return this.start < E.getEnd()
            && E.getStart() < this.end;
    }
    public int getDuration()
    {
        return end - start;
    }
}
