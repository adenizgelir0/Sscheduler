package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Bundle bundle = getIntent().getExtras();
        String sid = bundle.getString("sid");
        Button share = findViewById(R.id.shareBtn);
        EditText nameInput = findViewById(R.id.nameInput);
        EditText descInput = findViewById(R.id.descInput);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nameInput.getText().toString())){
                    Toast.makeText(ShareActivity.this, "Title can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(descInput.getText().toString())){
                    Toast.makeText(ShareActivity.this, "Description can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = nameInput.getText().toString();
                String desc = descInput.getText().toString();
                String uid = FirebaseAuth.getInstance().getUid();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference users = db.collection("Users");
                users.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        PostDB postDB = new PostDB(name,desc,sid,uid,0,0,user.getUsername(),user.getAvatarIndex());
                        db.collection("Posts").add(postDB).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(ShareActivity.this, "Shared", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }
        });
        Button cancel = findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Schedules").document(sid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });
            }
        });
    }
}