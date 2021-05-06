package hr.tvz.android.ahmadoulist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
     ImageView lastImage;
     int myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        lastImage = findViewById(R.id.lastImage);

        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("myImage")){
            myImage = getIntent().getIntExtra("myImage", 1);
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){

         lastImage.setImageResource(myImage);
    }

}