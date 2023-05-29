package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class ImportActivity extends AppCompatActivity {
    RelativeLayout[] days = new RelativeLayout[7];
    private Schedule primarySchedule;
    private Schedule secondarySchedule;
    private Schedule currentPermutation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        String sid = getIntent().getExtras().getString("sid");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference schedules = db.collection("Schedules");
        CollectionReference users = db.collection("Users");
        Button importbtn = findViewById(R.id.import_btn);
        createDays();
        schedules.document(sid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot secondaryDoc) {
                users.document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot userDoc) {
                        User user = userDoc.toObject(User.class);
                        schedules.document(user.getSid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot primaryDoc) {
                                ScheduleDB psdb = new ScheduleDB(primaryDoc.getData());
                                primarySchedule = psdb.getSchedule();
                                ScheduleDB ssdb = new ScheduleDB(secondaryDoc.getData());
                                secondarySchedule = ssdb.getSchedule();
                                remerge();
                                displaySchedule();
                            }
                        });
                        importbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ScheduleDB sdb = new ScheduleDB(currentPermutation);
                                schedules.document(user.getSid()).set(sdb.getEventList()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ImportActivity.this, "imported", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        Button remergeBtn = findViewById(R.id.remerge_btn);
        remergeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remerge();
                displaySchedule();
            }
        });
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
    private void createDays(){
        days[0] = findViewById(R.id.rl_mon);
        days[1] = findViewById(R.id.rl_tue);
        days[2] = findViewById(R.id.rl_wed);
        days[3] = findViewById(R.id.rl_thu);
        days[4] = findViewById(R.id.rl_fri);
        days[5] = findViewById(R.id.rl_sat);
        days[6] = findViewById(R.id.rl_sun);
    }
    private void displaySchedule(){
        for(int i=0; i<7; i++)
        {
            days[i].removeAllViews();
        }
        ArrayList<Event> events = currentPermutation.getEvents();
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
    private int dpToInt(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

}