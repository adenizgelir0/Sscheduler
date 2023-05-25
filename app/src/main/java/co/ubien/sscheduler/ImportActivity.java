package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

public class ImportActivity extends AppCompatActivity {
    private Schedule primarySchedule;
    private Schedule secondarySchedule;
    private Schedule currentPermutation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
    }
    public void remerge(){
        ArrayList<Event> eventsPrimary = primarySchedule.getEvents();
        ArrayList<Event> eventsSecondary = secondarySchedule.getEvents();
        currentPermutation = new Schedule();
        for(Event e : eventsPrimary)
            currentPermutation.addEvent(e);
        Collections.shuffle(eventsSecondary);
        for(Event e : eventsSecondary)
            currentPermutation.addEvent(e);
    }

}