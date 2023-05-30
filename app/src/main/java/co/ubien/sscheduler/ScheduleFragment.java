package co.ubien.sscheduler;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    ArrayList<Event> events = new ArrayList<>();
    static final Random rnd = new Random();
    RelativeLayout[] days;
    ScrollView outer;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean done = false;
    View rootView = null;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        outer = rootView.findViewById(R.id.ll_outer);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference schedulesRef = db.collection("Schedules");
        String sid = getArguments().getString("sid");
        Log.i("ScheduleFragment",sid);
        toolbarSetup();
        schedulesRef.document(sid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.i("ScheduleFragment","onSUccess");
                Map<String,Object> map = documentSnapshot.getData();
                Log.i("ScheduleFragment","casted to map");
                ScheduleDB scheduleDB = new ScheduleDB(map);
                Schedule schedule = scheduleDB.getSchedule();
                events = schedule.getEvents();

                days = new RelativeLayout[7];

                createDays(rootView);

                for (int i = 0; i < events.size(); ++i) {
                    Event e = events.get(i);
                    Button temp = new Button(rootView.getContext());

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
        });
        /*user = auth.getCurrentUser();
        DocumentReference userDoc = usersRef.document(user.getUid());*/

        /*events.add(new Event(0,1,"MAT", Color.RED,0));
        events.add(new Event(3,5,"CS", Color.GREEN,1));
        addEvent(new Event(12,14,"MAT",Color.RED,0));

        addEvent(new Event(30,60,"MATH", Color.RED,0));
        addEvent(new Event(180,300,"CS", Color.GREEN,1));
        addEvent(new Event(0,60,"FITNESS", Color.GRAY,2));
        addEvent(new Event(180,300,"FRENCH", Color.CYAN,3));
        addEvent(new Event(0,60,"MATH", Color.RED,3));
        addEvent(new Event(0,60,"banyo", Color.RED,4));
        addEvent(new Event(60,120,"xx", Color.GREEN,4));
*/
        return rootView;
    }

    private void addEvent(Event e){
        events.add(e);
    }

    private int dpToInt(int dp){
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());
        return (int)px;
    }

    private void createDays(View view){
        days[0] = view.findViewById(R.id.rl_mon);
        days[1] = view.findViewById(R.id.rl_tue);
        days[2] = view.findViewById(R.id.rl_wed);
        days[3] = view.findViewById(R.id.rl_thu);
        days[4] = view.findViewById(R.id.rl_fri);
        days[5] = view.findViewById(R.id.rl_sat);
        days[6] = view.findViewById(R.id.rl_sun);
    }
    private void toolbarSetup(){
        ImageView logout = rootView.findViewById(R.id.logout_icon);
        ImageView myProfile = rootView.findViewById(R.id.user_icon);

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