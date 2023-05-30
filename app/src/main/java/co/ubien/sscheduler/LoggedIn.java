package co.ubien.sscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoggedIn extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = db.collection("Users");
    private CollectionReference schedules = db.collection("Schedules");
    private String userSid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        users.document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                userSid = user.getSid();
                BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
                navigationView.setOnNavigationItemSelectedListener(selectedListener);
                Bundle bundle = new Bundle();
                bundle.putString("sid",userSid);
                ScheduleFragment sFragment = new ScheduleFragment();
                sFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, sFragment);
                fragmentTransaction.commit();
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            bundle.putString("sid",userSid);
            switch (item.getItemId()) {
                case R.id.my_schedule:
                    ScheduleFragment sFragment = new ScheduleFragment();
                    sFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, sFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.explore:
                    ExploreFragment sFragment1 = new ExploreFragment();
                    sFragment1.setArguments(bundle);
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fragment_container, sFragment1);
                    fragmentTransaction1.commit();
                    return true;
                case R.id.build_share:
                    BuildShareFragment sFragment2 = new BuildShareFragment();
                    sFragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fragment_container, sFragment2);
                    fragmentTransaction2.commit();
                    return true;
            }
            return false;
        }
    };
}