package co.ubien.sscheduler;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuildShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildShareFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootview = null;
    private int day = 0;
    Schedule schedule = new Schedule();
    public BuildShareFragment() {
        // Required empty public constructor
    }

    public void displaySchedule(){

        ArrayList<Event> events = schedule.getEvents();
        RelativeLayout currentDay = rootview.findViewById(R.id.rl_day);
        currentDay.removeAllViews();
        for (int i = 0; i < events.size(); ++i) {
            Event e = events.get(i);
            if (e.getDay() == day){
                Button temp = new Button(rootview.getContext());

                temp.setBackgroundColor(e.getColor());
                temp.setText(e.getName());
                temp.setPadding(0,0,0,0);

                int width = RelativeLayout.LayoutParams.MATCH_PARENT;
                int height = (int)((e.getEndMins() - e.getStartMins())/60f * dpToInt(60));

                temp.setTextSize(Math.min((int)(height/5.5f), 24));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                int topMargin = (int)(e.getStartMins()/60f * dpToInt(60) + dpToInt(50));
                params.topMargin = topMargin;
                params.rightMargin = dpToInt(30);
                BuildShareFragment b = this;

                final int index = i;
                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteDialog dialog = new DeleteDialog(events, index, b);
                        dialog.show(getActivity().getSupportFragmentManager(), "Dialog");

                    }
                });

                currentDay.addView(temp, params);
            }
        }

    }

    public static BuildShareFragment newInstance(String param1, String param2) {
        BuildShareFragment fragment = new BuildShareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        day = 0;

        rootview = inflater.inflate(R.layout.fragment_build_share, container, false);
        String userSid = getArguments().getString("sid");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        toolbarSetup();
        CollectionReference scheduleRef = db.collection("Schedules");
        scheduleRef.document(userSid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> map = documentSnapshot.getData();
                ScheduleDB scheduleDB = new ScheduleDB(map);
                schedule = scheduleDB.getSchedule();
                displaySchedule();
            }
        });


        Button prev = rootview.findViewById(R.id.prev_button);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDay(day-1);
            }
        });
        Button next = rootview.findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDay(day+1);
            }
        });
        Button addEvent = rootview.findViewById(R.id.eventButton);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateEventActivity.class);
                startActivityForResult(i,SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
        Button saveBtn = rootview.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleDB scheduleDB = new ScheduleDB(schedule);
                Map<String,Object> map = scheduleDB.getEventList();
                scheduleRef.document(userSid).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Button shareBtn = rootview.findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleDB scheduleDB = new ScheduleDB(schedule);
                Map<String, Object> map = scheduleDB.getEventList();
                scheduleRef.add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String newSid = documentReference.getId();
                        Intent i = new Intent(getActivity(),ShareActivity.class);
                        Bundle b = new Bundle();
                        b.putString("sid",newSid);
                        i.putExtras(b);
                        startActivity(i);
                    }
                });
            }
        });
        return rootview;
    }

    public void setDay(int day) {
        TextView t = rootview.findViewById(R.id.day_text);
        if(day<0)day = 7-((-day)%7);
        day %= 7;
        t.setText(dayString(this.day = day));
        displaySchedule();
    }
    private String dayString(int day)
    {
        if(day == 0) return "Mon";
        if(day == 1) return "Tue";
        if(day == 2) return "Wed";
        if(day == 3) return "Thur";
        if(day == 4) return "Fri";
        if(day == 5) return "Sat";
        return "Sun";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SECOND_ACTIVITY_REQUEST_CODE)
            if(resultCode == RESULT_OK)
            {
                String returnString = data.getStringExtra(Intent.EXTRA_TEXT);
                String[] params = returnString.split(" ");
                int start = Integer.parseInt(params[0]);
                int end = Integer.parseInt(params[1]);
                String name = params[2];
                Event E = new Event(start+24*day*60,end+24*day*60,name);
                // coloring will be done here
                Random rnd = new Random();
                float red = rnd.nextInt(255);
                float green = rnd.nextInt(255);
                float blue = rnd.nextInt(255);

                int xxx = 10;

                schedule.addEvent(E);
                displaySchedule();
            }
    }

    private int dpToInt(int dp){
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());
        return (int)px;
    }
    private void toolbarSetup(){
        ImageView logout = rootview.findViewById(R.id.logout_icon);
        ImageView myProfile = rootview.findViewById(R.id.user_icon);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra("first",false);
                startActivity(i);
            }
        });

    }
}