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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("Users");

    private EditText emailField;
    private EditText usernameField;
    private EditText passField;
    private Button SignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailField = findViewById(R.id.emailInput);
        usernameField = findViewById(R.id.usernameInput);
        passField = findViewById(R.id.passInput);
        SignUpBtn = findViewById(R.id.SignUpBtn);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
            }
        };
        SignUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(emailField.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "email field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(usernameField.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "username field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passField.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = emailField.getText().toString().trim();
                String username = usernameField.getText().toString().trim();
                String pass = passField.getText().toString().trim();
                createUser(email, username, pass);

            }
        });
    }
    private void createUser(String email, String username, String pass)
    {
        AggregateQuery userQuery = usersRef.whereEqualTo("username",username).count();
        userQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if(!task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "could not retrieve usernames", Toast.LENGTH_SHORT).show();
                return;
            }
            AggregateQuerySnapshot snapshot = task.getResult();
            if(snapshot.getCount() > 0){
                Toast.makeText(SignUpActivity.this, "Username taken", Toast.LENGTH_SHORT).show();
                return;
            }
            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) return;
                            user = auth.getCurrentUser();
                            String userId = user.getUid();
                            User userObj = new User(username);
                            usersRef.document(userId).set(userObj);
                            Toast.makeText(SignUpActivity.this, "user created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUpActivity.this, ProfileActivity.class);
                            Bundle b = new Bundle();
                            b.putBoolean("first",true);
                            b.putString("username", username);
                            i.putExtras(b);
                            startActivity(i);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
}