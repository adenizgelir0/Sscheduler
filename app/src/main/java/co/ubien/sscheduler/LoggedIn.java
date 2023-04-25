package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedIn extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        FirebaseUser user = auth.getCurrentUser();
        TextView nameT = findViewById(R.id.nameText);
        nameT.setText(user.getEmail());
        Button signOutB = findViewById(R.id.SignOutBtn);
        signOutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i = new Intent(LoggedIn.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}