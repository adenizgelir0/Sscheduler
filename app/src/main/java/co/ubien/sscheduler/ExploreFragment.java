package co.ubien.sscheduler;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */


    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
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


    private ArrayList<Post> posts;
    View rootview;
    GridLayout grid;
    private void displayCards(GridLayout.LayoutParams gridParams){
        grid.removeAllViews();

        for (Post p : this.posts){
            Schedule s = p.getSchedule();
            User u = p.getUser();
            int like = p.getLike();
            int dislike = p.getDisLike();
            String title = p.getTitle();

            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(rootview.getContext());
            ImageView avatar = new ImageView(rootview.getContext());
            avatar.setImageResource(R.drawable.man);
            ImageView likeImage = new ImageView(rootview.getContext());
            ImageView dislikeImage = new ImageView(rootview.getContext());
            TextView likeCount = new TextView(rootview.getContext());
            TextView dislikeCount = new TextView(rootview.getContext());
            likeCount.setText(like+"");
            dislikeCount.setText(dislike+"");
            likeImage.setImageResource(R.drawable.baseline_thumb_up_24);
            dislikeImage.setImageResource(R.drawable.baseline_thumb_down_24);
            TextView usernameText = new TextView(rootview.getContext());
            usernameText.setText(u.getUsername());
            usernameText.setTextColor(getResources().getColor(R.color.lavender));
            usernameText.setTextSize(18);
            TextView scheduleTitle = new TextView(rootview.getContext());
            scheduleTitle.setText(p.getTitle());
            scheduleTitle.setTextColor(getResources().getColor(R.color.lavender));
            scheduleTitle.setTextSize(24);

            int w = LinearLayout.LayoutParams.MATCH_PARENT;
            int h = LinearLayout.LayoutParams.MATCH_PARENT;
            LinearLayout outer = new LinearLayout(rootview.getContext());
            outer.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams outerparams = new LinearLayout.LayoutParams(w,h);
            outerparams.weight = 1;

            LinearLayout l1 = new LinearLayout(rootview.getContext());
            l1.setOrientation(LinearLayout.VERTICAL);
            w = dpToInt(100);
            h = dpToInt(100);
            LinearLayout.LayoutParams avatarparams = new LinearLayout.LayoutParams(w,h);
            avatarparams.gravity = Gravity.CENTER;
            l1.addView(avatar,avatarparams);
            usernameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            l1.addView(usernameText);

            LinearLayout l2 = new LinearLayout(rootview.getContext());
            l2.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams titleparams = new LinearLayout.LayoutParams(w,h);
            scheduleTitle.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            titleparams.gravity = Gravity.CENTER;
            titleparams.weight = 1;
            l2.addView(scheduleTitle,titleparams);

            LinearLayout l3 = new LinearLayout(rootview.getContext());
            l3.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w,h);
            params.gravity = Gravity.CENTER;
            params.width = dpToInt(22);
            params.height = dpToInt(22);
            params.weight = 1;
            l3.addView(likeImage,params);
            l3.addView(likeCount,params);
            l3.addView(dislikeImage,params);
            l3.addView(dislikeCount,params);
            l2.addView(l3);

            outer.addView(l1,outerparams);
            outer.addView(l2,outerparams);
            card.addView(outer);
            grid.addView(card, gridParams);
        }
    }



    private int dpToInt(int dp){
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());
        return (int)px;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        posts = new ArrayList<Post>();
        Schedule schedule1 = new Schedule();
        schedule1.addEvent(new Event(30,60,"MATH", Color.RED,0));
        schedule1.addEvent(new Event(180,300,"CS", Color.GREEN,1));
        schedule1.addEvent(new Event(0,60,"FITNESS", Color.GRAY,2));
        schedule1.addEvent(new Event(180,300,"FRENCH", Color.CYAN,3));
        schedule1.addEvent(new Event(0,60,"MATH", Color.RED,3));
        schedule1.addEvent(new Event(0,60,"banyo", Color.RED,4));
        schedule1.addEvent(new Event(60,120,"xx", Color.GREEN,4));
        User u1 = new User("joshua");
        Post p1 = new Post("Fitness","",schedule1,u1);
        User u2 = new User("Maiev");
        Post p2 = new Post("Math","",schedule1,u2);
        Post p3 = new Post("Seal","",schedule1,u2);


        rootview = inflater.inflate(R.layout.fragment_explore, container, false);
        grid = rootview.findViewById(R.id.post_gridlayout);

        GridLayout.LayoutParams gridparams = new GridLayout.LayoutParams();
        gridparams.width = GridLayout.LayoutParams.MATCH_PARENT;
        gridparams.height = GridLayout.LayoutParams.MATCH_PARENT;
        gridparams.setMarginStart(dpToInt(10));
        gridparams.setMarginEnd(dpToInt(10));
        gridparams.setMargins(0,dpToInt(12),0,dpToInt(12));

        grid.setColumnCount(1);
        grid.setRowCount(10);
        grid.setOrientation(GridLayout.VERTICAL);


        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        displayCards(gridparams);
        return rootview;
    }


}