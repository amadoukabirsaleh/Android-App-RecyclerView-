package hr.tvz.android.ahmadoulist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
 ImageView mainImageView;
 TextView title, description;
 String data1, data2;
 int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //mainImageView = findViewById(R.id.mainImageView);
        title= findViewById(R.id.Title);
        description = findViewById(R.id.Description);

        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data3")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data3");
            myImage = getIntent().getIntExtra("myImage", 1);
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
         title.setText(data1);
         description.setText(data2);
        // mainImageView.setImageResource(myImage);
    }

    public void viewTheImage (View v){
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("myImage", myImage);
        startActivity(intent);
    }
}