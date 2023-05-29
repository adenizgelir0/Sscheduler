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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity implements ActivityUtil{

    Post post;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Bundle bundle = getIntent().getExtras();
        String pid = bundle.getString("pid");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference posts = db.collection("Posts");
        CollectionReference users = db.collection("Users");
        CollectionReference comments = db.collection("Comments");
        posts.document(pid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PostDB pdb = documentSnapshot.toObject(PostDB.class);
                users.document(pdb.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        post = new Post(pdb.getTitle(),pdb.getDesc(),null,user,pid);
                        post.setLike(pdb.getLikes());
                        post.setDisLike(pdb.getDislikes());

                        comments.whereEqualTo("pid",pid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(!task.isSuccessful())
                                {
                                    Toast.makeText(CommentActivity.this, "unable to fetch comments", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    CommentDB cdb = document.toObject(CommentDB.class);
                                    User commenter = new User(cdb.getUsername(),0);
                                    post.addComment(cdb.getContent(),commenter);
                                }
                                displayUserCard();
                                displayComments();
                            }
                        });
                    }
                });
            }
        });


        Button addCommentButton = findViewById(R.id.addcomment);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommentActivity.this, AddCommentActivity.class);
                Bundle b = new Bundle();
                b.putString("pid",pid);
                i.putExtras(b);
                first = false;
                startActivity(i);

            }
        });

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

    private void displayUserCard(){
        ImageView avatar = findViewById(R.id.avatar_comment);
        int avatarIndex = post.getUser().getAvatarIndex();
        avatar.setImageResource(findAvatar(avatarIndex));
        TextView scheduleText = findViewById(R.id.schedule_comment);
        scheduleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        scheduleText.setGravity(Gravity.CENTER);
        scheduleText.setTextSize(dpToInt(8));
        scheduleText.setTextColor(getResources().getColor(R.color.lavender));
        TextView usernameText = findViewById(R.id.username_comment);
        usernameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        usernameText.setGravity(Gravity.CENTER);
        usernameText.setTextSize(dpToInt(6));
        usernameText.setTextColor(getResources().getColor(R.color.lavender));
        scheduleText.setText(post.getTitle());
        usernameText.setText(post.getUser().getUsername());
    }

    private void displayComments(){

        GridLayout grid = findViewById(R.id.comment_gridlayout);
        grid.setColumnCount(1);
        int rowIndex = 0;

        for (Comment c : post.getComments()){

            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(this);
            card.setRadius(8);
            card.setElevation(10);

            TextView comm = new TextView(this);
            TextView commentUser = new TextView(this);
            comm.setText(c.getComment());
            comm.setTextSize(dpToInt(7));
            commentUser.setText(c.getUser().getUsername().toUpperCase() +": ");
            commentUser.setTextSize(dpToInt(8));
            commentUser.setTypeface(Typeface.DEFAULT_BOLD);

            int w = LinearLayout.LayoutParams.MATCH_PARENT;
            int h = LinearLayout.LayoutParams.MATCH_PARENT;
            LinearLayout outer = new LinearLayout(this);
            outer.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams outerparams = new LinearLayout.LayoutParams(w,h);
            outerparams.leftMargin = 24;
            outerparams.rightMargin = 24;
            outer.addView(commentUser,outerparams);
            outer.addView(comm,outerparams);
            card.addView(outer);

            GridLayout.Spec row = GridLayout.spec(rowIndex++);
            GridLayout.Spec col = GridLayout.spec(0);

            GridLayout.LayoutParams gridparams = new GridLayout.LayoutParams(row,col);
            gridparams.width = GridLayout.LayoutParams.MATCH_PARENT;
            gridparams.height = GridLayout.LayoutParams.WRAP_CONTENT;
            gridparams.setMarginStart(dpToInt(10));
            gridparams.setMarginEnd(dpToInt(10));
            gridparams.setMargins(0,dpToInt(12),0,dpToInt(12));
            card.setBackgroundResource(R.drawable.purple_border);
            grid.addView(card,gridparams);
        }
    }

    private int dpToInt(int dp){
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());
        return (int)px;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!first)
        {
            finish();
            startActivity(getIntent());
        }
    }
}