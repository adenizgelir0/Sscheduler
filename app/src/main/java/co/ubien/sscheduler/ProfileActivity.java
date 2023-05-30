package co.ubien.sscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("Users");
    private CollectionReference schedulesRef = db.collection("Schedules");
    private CollectionReference postsRef = db.collection("Posts");

    private EditText nameField;
    private EditText bioField;
    private EditText avatarField;
    private Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = auth.getCurrentUser();
        nameField = findViewById(R.id.nameInput);
        bioField = findViewById(R.id.bioInput);
        avatarField = findViewById(R.id.avatarInput);
        saveBtn = findViewById(R.id.saveBtn);
        boolean isfirst = getIntent().getExtras().getBoolean("first");
        if(!isfirst)
        {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(nameField.getText().toString())){
                        Toast.makeText(ProfileActivity.this, "Full Name field is empty", Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(bioField.getText().toString())){
                        Toast.makeText(ProfileActivity.this, "Bio Field is empty", Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(avatarField.getText().toString())){
                        Toast.makeText(ProfileActivity.this, "Avatar Field is empty", Toast.LENGTH_SHORT).show();
                    }
                    String fullname = nameField.getText().toString().trim();
                    String bio = bioField.getText().toString().trim();
                    String avatarIndex = avatarField.getText().toString().trim();
                    usersRef.document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user = documentSnapshot.toObject(User.class);
                            user.setBio(bio);
                            user.setName(fullname);
                            user.setAvatar(Integer.parseInt(avatarIndex)-1);
                            usersRef.document(FirebaseAuth.getInstance().getUid()).set(user);
                            postsRef.whereEqualTo("uid",FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(!task.isSuccessful()) return;
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        PostDB pdb = document.toObject(PostDB.class);
                                        pdb.setAvatarIndex(Integer.parseInt(avatarIndex)-1);
                                        postsRef.document(document.getId()).set(pdb);
                                    }
                                    Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ProfileActivity.this, LoggedIn.class);
                                    startActivity(i);
                                }
                            });
                        }
                    });
                }
            });
            return;
        }
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
                if(TextUtils.isEmpty(avatarField.getText().toString())){
                    Toast.makeText(ProfileActivity.this, "Avatar Field is empty", Toast.LENGTH_SHORT).show();
                }
                String fullname = nameField.getText().toString().trim();
                String bio = bioField.getText().toString().trim();
                String avatarIndex = avatarField.getText().toString().trim();
                User userObj = new User(username, Integer.parseInt(avatarIndex)-1);
                userObj.setBio(bio);
                userObj.setName(fullname);
                schedulesRef.add((Map) new HashMap<Integer,String>()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String sid = documentReference.getId();
                        userObj.setSid(sid);
                        usersRef.document(user.getUid()).set(userObj);
                        Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProfileActivity.this, LoggedIn.class);
                        startActivity(i);
                    }
                });

            }
        });
    }
}