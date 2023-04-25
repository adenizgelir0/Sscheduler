package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("Users");

    private EditText nameField;
    private EditText bioField;
    private Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = auth.getCurrentUser();
        nameField = findViewById(R.id.nameInput);
        bioField = findViewById(R.id.bioInput);
        saveBtn = findViewById(R.id.saveBtn);
        String username = getIntent().getExtras().getString("username");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nameField.getText().toString())){
                    Toast.makeText(ProfileActivity.this, "Full Name field is empty", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(bioField.getText().toString())){
                    Toast.makeText(ProfileActivity.this, "Bio Field is empty", Toast.LENGTH_SHORT).show();
                }
                String fullname = nameField.getText().toString().trim();
                String bio = bioField.getText().toString().trim();
                User userObj = new User(username);
                userObj.setBio(bio);
                userObj.setName(fullname);
                usersRef.document(user.getUid()).set(userObj);
                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}