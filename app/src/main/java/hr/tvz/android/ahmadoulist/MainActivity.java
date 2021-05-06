package hr.tvz.android.ahmadoulist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
     MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    MyBroadcastReceiver batteryReceiver = new MyBroadcastReceiver();

    RecyclerView recyclerView;
      TextView batteryLevel;
    String s1[], s2[],s3[];
    int images[] = {R.drawable.aroma,R.drawable.facehero,R.drawable.facetransparent,R.drawable.lumin,R.drawable.soyfacecleanser,R.drawable.derma,R.drawable.lipo,R.drawable.aroma,R.drawable.facehero,R.drawable.facetransparent,R.drawable.lumin,R.drawable.soyfacecleanser,R.drawable.derma,R.drawable.lipo};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Skincare Products");
/*-------------FOR BATTERY BROADCAST RECEİVER-----------------------------*/
        batteryLevel=findViewById(R.id.batteryLevel);
        batteryReceiver = new MyBroadcastReceiver(batteryLevel);
        registerReceiver(batteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
 /*---------------------------------------------------------*/
        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.List);
        s2 = getResources().getStringArray(R.array.Description);
        s3 = getResources().getStringArray(R.array.about);
        MyAdapter myAdapter = new MyAdapter(this,s1,s2,s3,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    protected void onStart(){
        super.onStart();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadcastReceiver,filter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
        unregisterReceiver(batteryReceiver);

    }

    /*---FOR MENU OPTİON----------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    /*-------FOR THE LANGUAGE---------------------*/
    private void showChangeLanguageDialog(){
        final String[] listItems = {"French","Turkish","Croatian","English"};

        //aray of languages to display in alert dialog
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    setLocale("fr");
                    recreate();
                }
                if(i==1){
                    setLocale("tr");
                    recreate();
                }
                if(i==2){
                    setLocale("hr");
                    recreate();
                }
                if(i==3){
                    setLocale("en");
                    recreate();
                }
                //dismiss alert dialog when language selected
                dialogInterface.dismiss();
            }
        });
        android.app.AlertDialog mDialog = mBuilder.create();
        //show alert
        mDialog.show();

    }


    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //save date to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences prefs= getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String Language = prefs.getString("My_Lang","");
        setLocale(Language);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_share:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you really want to share this content?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody="Just trying to mess up with you";
                        String shareSubject="Hey there";

                        sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);

                        startActivity(Intent.createChooser(sharingIntent, "Share Using"));

                }})
                   .setNegativeButton("Cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_language:
              showChangeLanguageDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
            }


        }
}