package co.ubien.sscheduler;
import android.graphics.Color;

public class Event {
    private int start;
    private int end;
    private String name;
    private int color;
    private int day;

    public Event(int start, int end, String name, int color, int day) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.color = color;
        this.day = day;
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

    public int getEnd() {
        return end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
