package co.ubien.sscheduler;
import android.graphics.Color;

public class Event {
    private int start;
    private int end;
    private String name;
    private int color;

    public Event(int start, int end, String name, int color, int day) {
        this.start = start + day*24*60;
        this.end = end + day*24*60;
        this.name = name;
        this.color = color;
    }
    public Event(int start, int end, String name, int color) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.color = color;
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

    public boolean intersects(Event E)
    {
        /*
          start1 <= C < end1
          start2 <= C < end2
        */
       return this.start < E.getEnd()
            && E.getStart() < this.end;
    }
}
