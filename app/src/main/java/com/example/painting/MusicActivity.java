package com.example.painting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.painting.adapter.MusicAdapter;
import com.example.painting.music.MusicCanon;
import com.example.painting.music.MusicDream;
import com.example.painting.music.MusicKiss;
import com.example.painting.music.MusicRiver;
import com.example.painting.music.MusicStory;
import com.example.painting.music.MusicSummer;
import com.example.painting.music.MusicTruth;
import com.example.painting.object.Music;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    private ArrayList<Music> list = new ArrayList<>();
    private String song="canon";
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initMusic();

        final SharedPreferences.Editor editor = getSharedPreferences("name",MODE_PRIVATE).edit();
        editor.putString("song",song);
        editor.apply();
        MusicAdapter adapter = new MusicAdapter(MusicActivity.this,R.layout.music,list);
        ListView listView = findViewById(R.id.list_view_music);
        listView.setAdapter(adapter);

        final Intent canon = new Intent(MusicActivity.this, MusicCanon.class);
        final Intent dream = new Intent(MusicActivity.this, MusicDream.class);
        final Intent story = new Intent(MusicActivity.this, MusicStory.class);
        final Intent kiss = new Intent(MusicActivity.this, MusicKiss.class);
        final Intent river = new Intent(MusicActivity.this, MusicRiver.class);
        final Intent truth = new Intent(MusicActivity.this, MusicTruth.class);
        final Intent summer = new Intent(MusicActivity.this, MusicSummer.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music music = list.get(i);
                if (music.getTitle().equalsIgnoreCase("Canon")){
                    stopService(canon);
                    stopService(dream);
                    stopService(story);
                    stopService(kiss);
                    stopService(river);
                    stopService(truth);
                    stopService(summer);

                    startService(canon);
                    song = "canon";
                }else if (music.getTitle().equalsIgnoreCase("Dream Wedding")){
                    stopService(canon);
                    stopService(dream);
                    stopService(story);
                    stopService(kiss);
                    stopService(river);
                    stopService(truth);
                    stopService(summer);

                    startService(dream);
                    song="dream";
                }else if (music.getTitle().equalsIgnoreCase("A Little Story")){
                    stopService(canon);
                    stopService(dream);
                    stopService(story);
                    stopService(kiss);
                    stopService(river);
                    stopService(truth);
                    stopService(summer);

                    startService(story);
                    song= "story";
                }else if (music.getTitle().equalsIgnoreCase("Kiss The Rain")){
                    stopService(canon);
                    stopService(dream);
                    stopService(story);
                    stopService(kiss);
                    stopService(river);
                    stopService(truth);
                    stopService(summer);

                    startService(kiss);
                    song="kiss";
                }else if (music.getTitle().equalsIgnoreCase("River Flows In You")){
                    stopService(canon);
                    stopService(dream);
                    stopService(story);
                    stopService(kiss);
                    stopService(river);
                    stopService(truth);
                    stopService(summer);

                    startService(river);
                    song="river";
                }else if (music.getTitle().equalsIgnoreCase("The Truth That You Leave")){
                    stopService(canon);
                    stopService(dream);
                    stopService(story);
                    stopService(kiss);
                    stopService(river);
                    stopService(truth);
                    stopService(summer);

                    startService(truth);
                    song="truth";
                }else if (music.getTitle().equalsIgnoreCase("Summer")){
                    stopService(canon);
                    stopService(dream);
                    stopService(story);
                    stopService(kiss);
                    stopService(river);
                    stopService(truth);
                    stopService(summer);

                    startService(summer);
                    song="summer";
                }

                editor.putString("song",song);
                editor.apply();
            }
        });
    }

    public void initMusic(){
        Music canon = new Music("Canon", "Johann Pachelbel", R.drawable.canon);
        list.add(canon);
        Music dreamWedding = new Music("Dream Wedding","Richard Clayderman",R.drawable.dream_wedding);
        list.add(dreamWedding);
        Music aLittleStory = new Music("A Little Story","Valentin",R.drawable.a_little_story);
        list.add(aLittleStory);
        Music kissTheRain = new Music("Kiss The Rain","Yiruma",R.drawable.kiss_the_rain);
        list.add(kissTheRain);
        Music riverFlowsInYou = new Music("River Flows In You","Yiruma",R.drawable.river_flows_in_you);
        list.add(riverFlowsInYou);
        Music theTruthThatYouLeave = new Music("The Truth That You Leave","Pianoboy",R.drawable.the_truth_that_you_leave);
        list.add(theTruthThatYouLeave);
        Music summer = new Music("Summer","Joe Hisaishi",R.drawable.summer);
        list.add(summer);
    }

}
