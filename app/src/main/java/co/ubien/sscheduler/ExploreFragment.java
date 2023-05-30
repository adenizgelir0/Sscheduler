package co.ubien.sscheduler;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


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
    boolean first = true;

    ImageView searchIcon;
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

    private void search(){
        EditText input = rootview.findViewById(R.id.explore_search);
        String searched = input.getText().toString().toLowerCase();
        ArrayList<Post> searchedPosts = new ArrayList<>();

        for (int i = 0; i < this.posts.size(); i++){
            Post temp = this.posts.get(i);
            String title = temp.getTitle().toLowerCase();

            if (doSearchMatch(searched,title)){
                searchedPosts.add(temp);
            }
        }

        displayCards(searchedPosts);
    }

    private boolean doSearchMatch(String searched, String title){
        int len = searched.length();
        String[] strs = title.split(" ");
        for (int i = 0; i < strs.length; i++){
            String compare = strs[i];
            if (compare.length() >= len && searched.substring(0,len).equals(compare.substring(0,len))){
                return true;
            }
        }
        return false;
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

    private void sortPosts(ArrayList<Post> posts){

        SortPostsUtil util = new SortPostsUtil();
        Collections.sort(posts, util);
    }

    private void displayCards(ArrayList<Post> searchedPosts){
        grid.removeAllViews();
        grid.setColumnCount(1);
        int rowIndex = 0;

        sortPosts(searchedPosts);
        for (Post p : searchedPosts){
            Schedule s = p.getSchedule();
            User u = p.getUser();
            int like = p.getLike();
            int dislike = p.getDisLike();
            String title = p.getTitle();

            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(rootview.getContext());
            card.setRadius(8);
            card.setElevation(10);

            ImageView avatar = u.getAvatar(rootview);

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

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putString("pid",p.getPID());
                    i.putExtras(b);
                    startActivity(i);
                }
            });

            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), UserProfileActivity.class);
                    Bundle b = new Bundle();
                    b.putString("uid",p.getUid());
                    i.putExtras(b);
                    startActivity(i);
                }
            });

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

    private void displayCards(){
        grid.removeAllViews();
        grid.setColumnCount(1);
        int rowIndex = 0;

        sortPosts(this.posts);
        for (Post p : this.posts){
            Schedule s = p.getSchedule();
            User u = p.getUser();
            int like = p.getLike();
            int dislike = p.getDisLike();
            String title = p.getTitle();

            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(rootview.getContext());
            card.setRadius(8);
            card.setElevation(10);

            ImageView avatar = u.getAvatar(rootview);

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

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putString("pid",p.getPID());
                    i.putExtras(b);
                    startActivity(i);
                }
            });

            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), UserProfileActivity.class);
                    Bundle b = new Bundle();
                    b.putString("uid",p.getUid());
                    i.putExtras(b);
                    startActivity(i);
                }
            });

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
        rootview = inflater.inflate(R.layout.fragment_explore, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        posts = new ArrayList<Post>();
        CollectionReference users = db.collection("Users");
        toolbarSetup();
        db.collection("Posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getActivity(), "Can't fetch posts", Toast.LENGTH_SHORT).show();
                }
                for(QueryDocumentSnapshot document : task.getResult())
                {
                    PostDB post = document.toObject(PostDB.class);
                    User user = new User(post.getUsername(),post.getAvatarIndex());
                    Post postobj = new Post(post.getTitle(),post.getDesc(),null, user,document.getId());
                    postobj.setUid(post.getUid());
                    postobj.setLike(post.getLikes());
                    postobj.setDisLike(post.getDislikes());
                    posts.add(postobj);
                }

                grid = rootview.findViewById(R.id.post_gridlayout);
                grid.setOrientation(GridLayout.VERTICAL);

                displayCards();

                searchIcon = rootview.findViewById(R.id.search_icon);
                searchIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search();
                    }
                });

            }
        });


        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(first) first = false;
        else {
            ExploreFragment sFragment = new ExploreFragment();
            FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.fragment_container, sFragment);
            fragmentTransaction1.commit();
        }
    }
}