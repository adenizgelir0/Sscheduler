package co.ubien.sscheduler;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<Event> events = new ArrayList<>();
    RelativeLayout[] days;

    User user;
    Post post;
    private void displayUserCard(){
        ImageView avatar = findViewById(R.id.avatar_details);
        int avatarIndex = user.getAvatarIndex();
        avatar.setImageResource(findAvatar(avatarIndex));
        TextView scheduleText = findViewById(R.id.schedule_details);
        TextView usernameText = findViewById(R.id.username_details);
        scheduleText.setText(post.getTitle());
        usernameText.setText(user.getUsername());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        createDays();

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
        user = post.getUser();

        displayUserCard();

        displaySchedule();
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

}
