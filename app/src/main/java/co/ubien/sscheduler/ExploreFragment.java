package co.ubien.sscheduler;

import android.content.Intent;
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
    private ArrayList<Post> posts;
    View rootview;
    GridLayout grid;
    public ExploreFragment() {
        // Required empty public constructor
    }


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



    private void displayCards(){
        grid.removeAllViews();
        grid.setColumnCount(1);
        int rowIndex = 0;

        for (Post p : this.posts){
            Schedule s = p.getSchedule();
            User u = p.getUser();
            int like = p.getLike();
            int dislike = p.getDisLike();
            String title = p.getTitle();

            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(rootview.getContext());
            card.setRadius(8);
            card.setElevation(10);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailsActivity.class);
                    startActivityForResult(i,0);
                }
            });

            ImageView avatar = u.getAvatar(rootview);

            /*ImageView avatar = new ImageView(rootview.getContext());
            avatar.setImageResource(R.drawable.man);*/

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

            GridLayout.Spec row = GridLayout.spec(rowIndex++);
            GridLayout.Spec col = GridLayout.spec(0);

            GridLayout.LayoutParams gridparams = new GridLayout.LayoutParams(row,col);
            gridparams.width = GridLayout.LayoutParams.MATCH_PARENT;
            //gridparams.height = GridLayout.LayoutParams.MATCH_PARENT;*/
            gridparams.setMarginStart(dpToInt(10));
            gridparams.setMarginEnd(dpToInt(10));
            gridparams.setMargins(0,dpToInt(12),0,dpToInt(12));

            grid.addView(card,gridparams);
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

        User u1 = new User("joshua",6);
        User u2 = new User("Maiev",2);
        User u3 = new User("ahmet",4);
        User u4 = new User("rhioni",7);
        User u5 = new User("erko",9);
        User u6 = new User("jager",11);
        User u7 = new User("machine",3);

        Post p1 = new Post("Fitness","",schedule1,u1);
        Post p2 = new Post("Math","",schedule1,u2);
        Post p3 = new Post("Seal","",schedule1,u3);
        Post p4 = new Post("Seal","",schedule1,u4);
        Post p5 = new Post("Seal","",schedule1,u5);
        Post p6 = new Post("Seal","",schedule1,u6);
        Post p7 = new Post("Seal","",schedule1,u7);


        rootview = inflater.inflate(R.layout.fragment_explore, container, false);
        grid = rootview.findViewById(R.id.post_gridlayout);

        GridLayout.LayoutParams gridparams = new GridLayout.LayoutParams();
        gridparams.width = GridLayout.LayoutParams.MATCH_PARENT;
        gridparams.height = GridLayout.LayoutParams.MATCH_PARENT;
        gridparams.setMarginStart(dpToInt(10));
        gridparams.setMarginEnd(dpToInt(10));
        gridparams.setMargins(0,dpToInt(12),0,dpToInt(12));

        grid.setOrientation(GridLayout.VERTICAL);


        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
        posts.add(p4);
        posts.add(p5);
        posts.add(p6);
        posts.add(p7);

        displayCards();
        return rootview;
    }


}