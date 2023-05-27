package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        TimePicker startPicker=(TimePicker)findViewById(R.id.startPicker);
        EditText nameEdit = findViewById(R.id.eventName);
        startPicker.setIs24HourView(true);
        TimePicker endPicker = findViewById(R.id.endPicker);
        endPicker.setIs24HourView(true);
        Button createButton = findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start = startPicker.getHour()*60 + startPicker.getMinute();
                int end = endPicker.getHour()*60 + endPicker.getMinute();
                if(end <= start) {
                    Toast.makeText(CreateEventActivity.this, "Invalid time range", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nameEdit.getText().toString().indexOf(' ') != -1){
                    Toast.makeText(CreateEventActivity.this, "Event Name has to be one word", Toast.LENGTH_SHORT).show();
                    return;
                }

                String returnString = start
                        + " " + end + " " + nameEdit.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, returnString);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}