package co.ubien.sscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements ActivityUtil{

    ArrayList<Event> events = new ArrayList<>();
    RelativeLayout[] days = new RelativeLayout[7];

    private TextView likeCount,dislikeCount;
    User user;
    Post post;



    private void displayUserCard(){
        ImageView avatar = findViewById(R.id.avatar_details);
        int avatarIndex = user.getAvatarIndex();
        avatar.setImageResource(findAvatar(avatarIndex));
        TextView scheduleText = findViewById(R.id.schedule_details);
        scheduleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        scheduleText.setGravity(Gravity.CENTER);
        scheduleText.setTextSize(dpToInt(8));
        scheduleText.setTextColor(getResources().getColor(R.color.lavender));
        TextView usernameText = findViewById(R.id.username_details);
        usernameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        usernameText.setGravity(Gravity.CENTER);
        usernameText.setTextSize(dpToInt(6));
        usernameText.setTextColor(getResources().getColor(R.color.lavender));
        scheduleText.setText(post.getTitle());
        usernameText.setText(user.getUsername());

        TextView descriptionview = findViewById(R.id.description_text);
        descriptionview.setText("No description...");
        if (post != null){
            String str = post.getDescription();
            descriptionview.setText(str);
        }

    }

    private void displaySchedule(){
        for (int i = 0; i < events.size(); ++i) {
            Event e = events.get(i);
            Button temp = new Button(this);

            temp.setBackgroundColor(ColorUtil.strToColor(e.getName()));
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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String user_id = FirebaseAuth.getInstance().getUid();
        likeCount = new TextView(this);
        dislikeCount = new TextView(this);

        createDays();
        Bundle b = getIntent().getExtras();
        String pid = b.getString("pid");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference postsRef = db.collection("Posts");
        CollectionReference usersRef = db.collection("Users");
        CollectionReference schedulesRef = db.collection("Schedules");
        CollectionReference likes = db.collection("Likes");
        CollectionReference dislikes = db.collection("Dislikes");
        Button likebtn = findViewById(R.id.likebutton);
        Button dislikebtn = findViewById(R.id.dislikebutton);
        Button importbtn = findViewById(R.id.importbutton_details);
        postsRef.document(pid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PostDB pdb = documentSnapshot.toObject(PostDB.class);
                String uid = pdb.getUid();
                String sid = pdb.getSid();
                importbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent import_intent = new Intent(DetailsActivity.this, ImportActivity.class);
                        Bundle b = new Bundle();
                        b.putString("sid",sid);
                        import_intent.putExtras(b);
                        startActivity(import_intent);

                    }
                });
                likebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int likec = Integer.parseInt(likeCount.getText().toString());
                        likeCount.setText(likec+1+"");
                        if(likec+1 == 100)
                        {
                            usersRef.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot PUserData) {
                                    User puser = PUserData.toObject(User.class);
                                    puser.setLike100(true);
                                    usersRef.document(uid).set(puser);
                                }
                            });
                        }
                        PostDB npdb = new PostDB(pdb.getTitle(), pdb.getDesc(),
                                sid, uid, likec+1, pdb.getDislikes(),
                                pdb.getUsername(), pdb.getAvatarIndex());
                        dislikes.document(pid+user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    int dislikec = Integer.parseInt(dislikeCount.getText().toString());
                                    if(task.getResult().exists())
                                    {
                                        dislikeCount.setText(dislikec-1+"");
                                        dislikes.document(pid+user_id).delete();
                                        dislikec--;
                                    }
                                    npdb.setDislikes(dislikec);
                                    postsRef.document(pid).set(npdb);
                                }
                            }
                        });
                        likes.document(pid+user_id).set(new Like());
                        likebtn.setEnabled(false);
                        dislikebtn.setEnabled(true);
                    }
                });
                dislikebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int dislikec = Integer.parseInt(dislikeCount.getText().toString());
                        dislikeCount.setText(dislikec+1+"");
                        PostDB npdb = new PostDB(pdb.getTitle(), pdb.getDesc(),
                                sid, uid, pdb.getLikes(), dislikec+1,
                                pdb.getUsername(), pdb.getAvatarIndex());
                        likes.document(pid+user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    int likec = Integer.parseInt(likeCount.getText().toString());
                                    if(task.getResult().exists())
                                    {
                                        likeCount.setText(likec-1+"");
                                        likes.document(pid+user_id).delete();
                                        likec--;
                                    }
                                    npdb.setLikes(likec);
                                    postsRef.document(pid).set(npdb);
                                }
                            }
                        });
                        dislikes.document(pid+user_id).set(new Dislike());
                        likebtn.setEnabled(true);
                        dislikebtn.setEnabled(false);
                    }
                });
                usersRef.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        schedulesRef.document(sid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ScheduleDB sdb = new ScheduleDB(documentSnapshot.getData());
                                Schedule schedule = sdb.getSchedule();
                                post = new Post(pdb.getTitle(),pdb.getDesc(),schedule,user,pid);
                                post.setLike(pdb.getLikes());
                                post.setDisLike(pdb.getDislikes());
                                events = schedule.getEvents();
                                displayUserCard();
                                displaySchedule();
                                displayLikeDislike();
                            }
                        });
                    }
                });
            }
        });


        Button commentsButton = findViewById(R.id.commentsbutton_details);
        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, CommentActivity.class);
                Bundle b  = new Bundle();
                b.putString("pid",pid);
                i.putExtras(b);
                startActivity(i);
            }
        });
        likes.document(pid+FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        likebtn.setEnabled(false);
                    }
                }

            }
        });

        dislikes.document(pid+FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        dislikebtn.setEnabled(false);
                    }
                }

            }
        });
    }

    private void displayLikeDislike(){
        LinearLayout outer = findViewById(R.id.outer_details);
        int w = LinearLayout.LayoutParams.MATCH_PARENT;
        int h = LinearLayout.LayoutParams.MATCH_PARENT;
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w,h);
        params.weight = 1;

        ImageView likeImage = new ImageView(this);
        ImageView dislikeImage = new ImageView(this);

        likeImage.setImageResource(R.drawable.baseline_thumb_up_24);
        dislikeImage.setImageResource(R.drawable.baseline_thumb_down_24);
        likeCount.setText(post.getLike() + "");
        dislikeCount.setText(post.getDisLike() + "");

        panel.addView(likeImage, params);
        panel.addView(likeCount, params);
        panel.addView(dislikeImage, params);
        panel.addView(dislikeCount, params);

        w = dpToInt(100);
        LinearLayout.LayoutParams outerparams = new LinearLayout.LayoutParams(w,h);
        outerparams.gravity = Gravity.CENTER;
        outer.addView(panel,outerparams);
    }

    public int findAvatar(int avatarIndex){
        if (avatarIndex == 1){
            return R.drawable.cat;

        }
        else if (avatarIndex == 2){
            return R.drawable.gamer;

        }
        else if (avatarIndex == 3){
            return R.drawable.man1 ;
        }
        else if (avatarIndex == 4){
            return R.drawable.man2 ;

        }
        else if (avatarIndex == 5){
            return R.drawable.man3;

        }
        else if (avatarIndex == 6){
            return R.drawable.profile;

        }
        else if (avatarIndex == 7){
            return R.drawable.user;

        }
        else if (avatarIndex == 8){
            return R.drawable.woman;

        }
        else if (avatarIndex == 9){
            return R.drawable.woman1;

        }

        return R.drawable.man;
    }

}