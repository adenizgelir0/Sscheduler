package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;


public class ScheduleActivity extends AppCompatActivity {

    ArrayList<Event> events = new ArrayList<>();
    static final Random rnd = new Random();
    RelativeLayout[] days;
    ScrollView outer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        outer = findViewById(R.id.ll_outer);

        events.add(new Event(0,1,"MAT", Color.RED,0));
        events.add(new Event(3,5,"CS", Color.GREEN,1));
        addEvent(new Event(12,14,"MAT",Color.RED,0));
        days = new RelativeLayout[7];

        createDays();

        for (int i = 0; i < events.size(); ++i){
            Event e = events.get(i);
            Button temp = new Button(this);

            temp.setBackgroundColor(e.getColor());
            temp.setText(e.getName());
            temp.setTextSize(20);

            int width = RelativeLayout.LayoutParams.MATCH_PARENT;
            int height = (e.getEndMins() - e.getStartMins()) * dpToInt(50);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
            int topMargin = e.getStartMins() * dpToInt(50) + dpToInt(45);
            params.topMargin = topMargin;
            days[e.getDay()].addView(temp,params);
        }
    }

    private void addEvent(Event e){
        events.add(e);
    }

    private int dpToInt(int dp){
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());
        return (int)px;
    }

    private void createDays(){
        days[0] = findViewById(R.id.rl_mon);
        days[1] = findViewById(R.id.rl_tue);
        days[2] = findViewById(R.id.rl_wed);
        days[3] = findViewById(R.id.rl_thu);
        days[4] = findViewById(R.id.rl_fri);
        days[5] = findViewById(R.id.rl_sat);
        days[6] = findViewById(R.id.rl_sun);
    }

}