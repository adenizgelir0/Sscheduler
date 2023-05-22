package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ReadComments extends AppCompatActivity {
    private ListView commentList;
    private ArrayAdapter<String> adapter;
    private Button btn;


    //private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comments);

        Comments commentS = new Comments();

        btn = findViewById(R.id.addC);
        commentList = (ListView)findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, commentS.getComment());

        commentList.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadComments.this, ComentsPage.class);
            }
        });




    }

    // getter methods


}