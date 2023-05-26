package co.ubien.sscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExploreV1 extends AppCompatActivity {
    private static ArrayList<Post> posts = new ArrayList<Post>();

    String[] title =  {"sa", "as", "asdsa" , "asdasd", "dasd", "sa", "as", "asdsa" , "asdasd", "dasd", "sa", "as", "asdsa" , "asdasd", "dasd"};
    String[] description = {"sa", "as", "asdsa" , "asdasd","sa", "as", "asdsa" , "asdasd", "dasd","sa", "as", "asdsa" , "asdasd", "dasd"};
    int image = R.drawable.back_arrow;
    ArrayAdapter adapter;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_v1);

        list = findViewById(R.id.list);
        list.setAdapter(new myAdapter(this, title, description, image));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent = new Intent(ExploreV1.this, ReadComments.class);
              startActivity(intent);

            }
        });



    }

    class myAdapter extends ArrayAdapter<String>{
        Context context;
        String[] title;
        String[] description;
        int image;

        public myAdapter(Context c, String[] title, String[] description, int image) {
            super(c, R.layout.explore_inside_list, R.id.title, title);
            this.context = c;
            this.title = title;
            this.description = description;
            this.image = image;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View line = layoutInflater.inflate(R.layout.explore_inside_list,parent,false);

            ImageView image = line.findViewById(R.id.image);
            TextView title = line.findViewById(R.id.title);
            TextView description = line.findViewById(R.id.description);

            image.setImageResource(this.image);
            title.setText(this.title[position]);
            description.setText(this.title[position]);

            return line;

        }
    }
}