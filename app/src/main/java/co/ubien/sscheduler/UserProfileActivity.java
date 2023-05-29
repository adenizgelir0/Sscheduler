package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity implements ActivityUtil{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("uid");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                TextView username = findViewById(R.id.username_profile);
                TextView fullname = findViewById(R.id.fullname_profile);
                TextView bio = findViewById(R.id.bio_profile);

                ImageView avatar = findViewById(R.id.user_profile_avatar);
                ImageView badge = findViewById(R.id.user_profile_badge);
                ImageView like100 = findViewById(R.id.user_profile_like_badge);

                username.setText("Username: " + user.getUsername());
                fullname.setText("Fullname: "+ user.getName());
                bio.setText(user.getBio());

                int avatarIndex = user.getAvatarIndex();
                avatar.setImageResource(findAvatar(avatarIndex));

                if (user.getLike100()){
                    like100.setImageResource(R.drawable.likehun);
                }
                if (user.getVerified()){
                    badge.setImageResource(R.drawable.medal);
                }
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

}