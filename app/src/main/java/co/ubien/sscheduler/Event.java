package co.ubien.sscheduler;

public class Event {
    private int start;
    private int end;
    private String name;
    private EventType type;

    public Event(int start, int end, String name, EventType type) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.type = type;
    }

    public  Event(int start, int end, String name) {
        this(start, end, name, EventType.RED);
    }

    public  Event(int start, int end){
        this(start,end,"unnamed");
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
