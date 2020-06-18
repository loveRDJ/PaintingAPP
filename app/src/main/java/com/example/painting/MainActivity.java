package com.example.painting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.painting.music.MusicCanon;
import com.example.painting.music.MusicDream;
import com.example.painting.music.MusicKiss;
import com.example.painting.music.MusicRiver;
import com.example.painting.music.MusicStory;
import com.example.painting.music.MusicSummer;
import com.example.painting.music.MusicTruth;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button music;
    private Button show;

    private Intent canon;
    private Intent dream;
    private Intent story;
    private Intent kiss;
    private Intent river;
    private Intent truth;
    private Intent summer;

    private String song;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canon = new Intent(this, MusicCanon.class);
        dream = new Intent(this, MusicDream.class);
        story = new Intent(this, MusicStory.class);
        kiss = new Intent(this, MusicKiss.class);
        river = new Intent(this, MusicRiver.class);
        truth = new Intent(this, MusicTruth.class);
        summer = new Intent(this, MusicSummer.class);


        startService(canon);

        start = findViewById(R.id.button_start);
        music = findViewById(R.id.button_music);
        show = findViewById(R.id.button_show);


        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MusicActivity.class);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PaintActivity.class);
                startActivity(intent);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });


    }

    public void muteVolume(View view) {
        boolean checked = ((ToggleButton)view).isChecked();
        if(checked){
            stopService(canon);
            stopService(dream);
            stopService(story);
            stopService(kiss);
            stopService(river);
            stopService(truth);
            stopService(summer);
        }else{
            sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
            song = sharedPreferences.getString("song","");
            if (song.equalsIgnoreCase("canon")){
                startService(canon);
            }else if (song.equalsIgnoreCase("dream")){
                startService(dream);
            }else if (song.equalsIgnoreCase("story")){
                startService(story);
            }else if (song.equalsIgnoreCase("kiss")){
                startService(kiss);
            }else if (song.equalsIgnoreCase("river")){
                startService(river);
            }else if (song.equalsIgnoreCase("truth")){
                startService(truth);
            }else if (song.equalsIgnoreCase("summer")){
                startService(summer);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Warning")
                    .setMessage("Are you sure to exit?")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            stopService(canon);
                            stopService(dream);
                            stopService(story);
                            stopService(kiss);
                            stopService(river);
                            stopService(truth);
                            stopService(summer);
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();

        }
        return true;
    }
}
