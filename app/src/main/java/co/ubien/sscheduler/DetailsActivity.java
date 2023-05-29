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

public class DetailsActivity extends AppCompatActivity {

    ArrayList<Event> events = new ArrayList<>();
    RelativeLayout[] days = new RelativeLayout[7];

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
    }

    private void displaySchedule(){
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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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
        postsRef.document(pid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PostDB pdb = documentSnapshot.toObject(PostDB.class);
                String uid = pdb.getUid();
                String sid = pdb.getSid();
                likebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dislikes.document(pid+uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                PostDB npdb = new PostDB(pdb.getTitle(), pdb.getDesc(),
                                        sid, uid, pdb.getLikes()+1, pdb.getDislikes(),
                                        pdb.getUsername(), pdb.getAvatarIndex());
                                if(task.isSuccessful())
                                {
                                    npdb.setDislikes(npdb.getDislikes()-1);
                                }
                                postsRef.document(pid).set(npdb);
                                likes.document(pid + uid).set(new Like());
                            }
                        });
                        dislikebtn.setEnabled(true);
                        likebtn.setEnabled(false);
                    }
                });
                dislikebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        likes.document(pid+uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                PostDB npdb = new PostDB(pdb.getTitle(), pdb.getDesc(),
                                        sid, uid, pdb.getLikes(), pdb.getDislikes()+1,
                                        pdb.getUsername(), pdb.getAvatarIndex());
                                if(task.isSuccessful())
                                {
                                    npdb.setLikes(npdb.getLikes()-1);
                                }
                                postsRef.document(pid).set(npdb);
                                dislikes.document(pid + uid).set(new Dislike());
                            }
                        });
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
                    likebtn.setEnabled(false);
                    dislikebtn.setEnabled(true);
                }

            }
        });

        dislikes.document(pid+FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    dislikebtn.setEnabled(false);
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
        TextView likeCount = new TextView(this);
        TextView dislikeCount = new TextView(this);

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