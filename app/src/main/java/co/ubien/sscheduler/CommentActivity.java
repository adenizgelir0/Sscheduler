package co.ubien.sscheduler;

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

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Schedule schedule1 = new Schedule();
        schedule1.addEvent(new Event(30,60,"MATH", Color.RED,0));
        schedule1.addEvent(new Event(180,300,"CS", Color.GREEN,1));
        schedule1.addEvent(new Event(0,60,"FITNESS", Color.GRAY,2));
        schedule1.addEvent(new Event(180,300,"FRENCH", Color.CYAN,3));
        schedule1.addEvent(new Event(0,60,"MATH", Color.RED,3));
        schedule1.addEvent(new Event(0,60,"banyo", Color.RED,4));
        schedule1.addEvent(new Event(60,120,"xx", Color.GREEN,4));

        User u1 = new User("joshua",6);
        post = new Post("Fitness","",schedule1,u1);

        displayUserCard();
        displayComments();

        Button addCommentButton = findViewById(R.id.addcomment);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommentActivity.this, AddCommentActivity.class);
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
            return R.drawable.woman1 ;
        }
        else if (avatarIndex == 4){
            return R.drawable.man1 ;

        }
        else if (avatarIndex == 5){
            return R.drawable.man2;

        }
        else if (avatarIndex == 6){
            return R.drawable.man3;

        }
        else if (avatarIndex == 7){
            return R.drawable.profile;

        }
        else if (avatarIndex == 8){
            return R.drawable.user;

        }
        else if (avatarIndex == 9){
            return R.drawable.woman;

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
        User u1 = new User("joshua",6);
        post.addComment("ASIRI BOKTAN BIR PROGRAMDIsdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",u1);
        post.addComment("holyShit",u1);
        post.addComment("Oh shit here we go again",u1);
        post.addComment("yapanın ellerinden operim",u1);
        post.addComment("bu cocugun adı niye boyle",u1);
        post.addComment("bidaha asla program yazma",u1);
        post.addComment("ehehaeehehee",u1);

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


}