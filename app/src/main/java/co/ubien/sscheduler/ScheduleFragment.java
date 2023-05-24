package co.ubien.sscheduler;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
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
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        outer = rootView.findViewById(R.id.ll_outer);

        events.add(new Event(0,1,"MAT", Color.RED,0));
        events.add(new Event(3,5,"CS", Color.GREEN,1));
        addEvent(new Event(12,14,"MAT",Color.RED,0));
        days = new RelativeLayout[7];

        createDays(rootView);

        for (int i = 0; i < events.size(); ++i) {
            Event e = events.get(i);
            Button temp = new Button(rootView.getContext());

            temp.setBackgroundColor(e.getColor());
            temp.setText(e.getName());
            temp.setTextSize(20);

            int width = RelativeLayout.LayoutParams.MATCH_PARENT;
            int height = (e.getEndMins() - e.getStartMins()) * dpToInt(50);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            int topMargin = e.getStartMins() * dpToInt(50) + dpToInt(45);
            params.topMargin = topMargin;
            days[e.getDay()].addView(temp, params);
        }
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
}