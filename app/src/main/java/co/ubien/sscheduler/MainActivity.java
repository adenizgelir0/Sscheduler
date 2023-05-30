package co.ubien.sscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button LoginBtn;
    private EditText passField;
    private EditText emailField;
    private Button SignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //auth.signOut();
        FirebaseUser user = auth.getCurrentUser();
        //Log.i("MainActivity",user.getUid());
        if(user != null){
            Intent i = new Intent(MainActivity.this, LoggedIn.class); //LOGGED IN OLACAK
            startActivity(i);
        }

        LoginBtn = findViewById(R.id.LoginBtn);
        SignUpBtn = findViewById(R.id.SignUpBtn);
        passField = findViewById(R.id.passInput);
        emailField = findViewById(R.id.emailInput);
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(emailField.getText().toString())){
                    Toast.makeText(MainActivity.this, "email field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passField.getText().toString())){
                    Toast.makeText(MainActivity.this, "password field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = emailField.getText().toString().trim();
                String pass = passField.getText().toString().trim();
                auth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(MainActivity.this, task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this, LoggedIn.class);
                                startActivity(i);
                            }
                        });
            }
        });
    }
}