package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class ComentsPage extends AppCompatActivity {

    private ImageButton backBtn;
    private Button makeComment;
    private EditText commentWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coments_page);

        makeComment = (Button) findViewById(R.id.commentMake);
        commentWrite = (EditText) findViewById(R.id.commentWritten);

        makeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // normalde dataBase e atacak ama şu an arrayliste atıyor.
                Comments c = new Comments();
                c.getComment().add(commentWrite.getText().toString());

                commentWrite.setText(null);
                Toast.makeText(ComentsPage.this, "Comment is made!", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComentsPage.this, ReadComments.class);
                startActivity(intent);
            }
        });



    }
}