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

public class built_share_main extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button newActivity;
    String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday", "Saturday","Sunday"};
    public String selectedDay = "Monday";

    public void openNewEventPage() {
        Intent intent = new Intent(this, built_share_initial.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_share_main);

        //BUtton opens other pagge for create new event
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
    }

    //for combobox section
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT);
        selectedDay = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //end of combobox section

}
