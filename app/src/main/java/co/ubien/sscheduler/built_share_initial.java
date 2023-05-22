package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class built_share_initial extends AppCompatActivity {
    private Button addActivity;

    private EditText eventNameBox;
    private EditText timeStartBox;
    private EditText timeEndBox;
    private EditText colorBox;


    public String eventName;
    public int startTime;
    public int endTime;

    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_share_initial);
        //Button that opens main built share page
        addActivity = (Button) findViewById(R.id.addThisEvent);
        eventNameBox = (EditText) findViewById(R.id.eventNameBox);
        timeStartBox = (EditText) findViewById(R.id.startTimeBox);
        timeEndBox = (EditText) findViewById(R.id.endTimeBox);
        colorBox = (EditText) findViewById(R.id.colorBox);

        addActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int start = getStartInput();
                int end = getEndInput();
                String str = getNameInput();
                int color = getColor();
                //int day = ;
                event = new Event(start, end,str, color,0);
                built_share_main.addToEvents(event);

                openNewEventPage();

            }
        });
    }
    public int getStartInput() {
        return Integer.parseInt(timeStartBox.getText().toString());

    }
    public int getEndInput() {
        return Integer.parseInt(timeEndBox.getText().toString());

    }
    public String getNameInput() {
        return eventNameBox.getText().toString();

    }
    public int getColor() {
        return Integer.parseInt(colorBox.getText().toString());

    }
    public void openNewEventPage() {
        Intent intent = new Intent(this, built_share_main.class);
        startActivity(intent);
    }
}