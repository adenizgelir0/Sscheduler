package co.ubien.sscheduler;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<Event> events = new ArrayList<>();
    RelativeLayout[] days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        createDays();

        addEvent(new Event(30,60,"MATH", Color.RED,0));
        addEvent(new Event(180,300,"CS", Color.GREEN,1));
        addEvent(new Event(0,60,"FITNESS", Color.GRAY,2));
        addEvent(new Event(180,300,"FRENCH", Color.CYAN,3));
        addEvent(new Event(0,60,"MATH", Color.RED,3));
        addEvent(new Event(0,60,"banyo", Color.RED,4));
        addEvent(new Event(60,120,"xx", Color.GREEN,4));

        for (int i = 0; i < events.size(); ++i) {
            Event e = events.get(i);
            Button temp = new Button(this);

            temp.setBackgroundColor(e.getColor());
            temp.setText(e.getName());
            int len = e.getName().length();
            int size = Math.min((int)(dpToInt(23) / (len * 1f)) , dpToInt(7));
            temp.setPadding(0,0,0,0);

            int width = RelativeLayout.LayoutParams.MATCH_PARENT;
            int height = (int)((e.getEndMins() - e.getStartMins())/60f * dpToInt(50));

            temp.setTextSize(size);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            int topMargin = (int)(e.getStartMins()/60f * dpToInt(50) + dpToInt(45));
            params.topMargin = topMargin;

            temp.setTypeface(Typeface.DEFAULT_BOLD);
            days[e.getDay()].addView(temp, params);
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
        days[0] = findViewById(R.id.rl_mondet);
        days[1] = findViewById(R.id.rl_tuedet);
        days[2] = findViewById(R.id.rl_weddet);
        days[3] = findViewById(R.id.rl_thudet);
        days[4] = findViewById(R.id.rl_fridet);
        days[5] = findViewById(R.id.rl_satdet);
        days[6] = findViewById(R.id.rl_sundet);
    }}
