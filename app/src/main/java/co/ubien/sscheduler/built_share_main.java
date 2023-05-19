package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class built_share_main extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button newActivity;
    String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday", "Saturday","Sunday"};
    public ArrayList<String> selectedDays = new ArrayList<String>();
    public String selectedName= "Null";

    public ArrayList<int[]> selectedTimes = new ArrayList<int[]>();



    public void openNewEventPage() {
        Intent intent = new Intent(this, built_share_initial.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_share_main);

        //Button that opens another page for creating new event
        newActivity = (Button) findViewById(R.id.newEvent);
        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewEventPage();
            }
        });

        //combobox
        Spinner spin = (Spinner) findViewById(R.id.spinner); //spinner's id is spinner
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = ArrayAdapter.createFromResource(this,R.array.days, android.R.layout.simple_spinner_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(this);
        //</combobox>

    }

    //for combobox section
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT);
        setSelectedDay(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        setSelectedDay(0);
    }
    //end of combobox section
    public void setSelectedDay(int a) {
        selectedDays.add(days[a]);
    }
    public void setSelectedDay(String a) {
        selectedDays.add(a);
    }
    public void setSelectedName(String s) {
        selectedName = s;
    }
    public void setSelectedTime(int a, int b) {
        int[] timeSlot = new int[2];
        timeSlot[0] = a;
        timeSlot[1] = b;
        selectedTimes.add(timeSlot) ;
    }

}
