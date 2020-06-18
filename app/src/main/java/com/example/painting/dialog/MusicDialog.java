package com.example.painting.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.painting.object.Music;
import com.example.painting.adapter.MusicAdapter;
import com.example.painting.R;
import com.example.painting.music.MusicCanon;
import com.example.painting.music.MusicDream;
import com.example.painting.music.MusicKiss;
import com.example.painting.music.MusicRiver;
import com.example.painting.music.MusicStory;
import com.example.painting.music.MusicSummer;
import com.example.painting.music.MusicTruth;

import java.util.ArrayList;

public class MusicDialog extends AppCompatDialogFragment {

    private ListView listView;

    private String song;

    private SharedPreferences sharedPreferences;

    private ArrayList<Music> list = new ArrayList<>();

    private MusicDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        initMusic();
        final View view = inflater.inflate(R.layout.dialog_music,null);

        MusicAdapter adapter = new MusicAdapter(getContext(),R.layout.music,list);
        ListView listView = view.findViewById(R.id.list_view_music);
        listView.setAdapter(adapter);

        sharedPreferences = getContext().getSharedPreferences("name", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        final Intent canon = new Intent(getContext(), MusicCanon.class);
        final Intent dream = new Intent(getContext(), MusicDream.class);
        final Intent story = new Intent(getContext(), MusicStory.class);
        final Intent kiss = new Intent(getContext(), MusicKiss.class);
        final Intent river = new Intent(getContext(), MusicRiver.class);
        final Intent truth = new Intent(getContext(), MusicTruth.class);
        final Intent summer = new Intent(getContext(), MusicSummer.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music music = list.get(i);
                if (music.getTitle().equalsIgnoreCase("Canon")) {
                    getContext().stopService(canon);
                    getContext().stopService(dream);
                    getContext().stopService(story);
                    getContext().stopService(kiss);
                    getContext().stopService(river);
                    getContext().stopService(truth);
                    getContext().stopService(summer);

                    getContext().startService(canon);
                    song = "canon";
                } else if (music.getTitle().equalsIgnoreCase("Dream Wedding")) {
                    getContext().stopService(canon);
                    getContext().stopService(dream);
                    getContext().stopService(story);
                    getContext().stopService(kiss);
                    getContext().stopService(river);
                    getContext().stopService(truth);
                    getContext().stopService(summer);

                    getContext().startService(dream);
                    song = "dream";
                } else if (music.getTitle().equalsIgnoreCase("A Little Story")) {
                    getContext().stopService(canon);
                    getContext().stopService(dream);
                    getContext().stopService(story);
                    getContext().stopService(kiss);
                    getContext().stopService(river);
                    getContext().stopService(truth);
                    getContext().stopService(summer);

                    getContext().startService(story);
                    song = "story";
                } else if (music.getTitle().equalsIgnoreCase("Kiss The Rain")) {
                    getContext().stopService(canon);
                    getContext().stopService(dream);
                    getContext().stopService(story);
                    getContext().stopService(kiss);
                    getContext().stopService(river);
                    getContext().stopService(truth);
                    getContext().stopService(summer);

                    getContext().startService(kiss);
                    song = "kiss";
                } else if (music.getTitle().equalsIgnoreCase("River Flows In You")) {
                    getContext().stopService(canon);
                    getContext().stopService(dream);
                    getContext().stopService(story);
                    getContext().stopService(kiss);
                    getContext().stopService(river);
                    getContext().stopService(truth);
                    getContext().stopService(summer);

                    getContext().startService(river);
                    song = "river";
                } else if (music.getTitle().equalsIgnoreCase("The Truth That You Leave")) {
                    getContext().stopService(canon);
                    getContext().stopService(dream);
                    getContext().stopService(story);
                    getContext(). stopService(kiss);
                    getContext().stopService(river);
                    getContext().stopService(truth);
                    getContext().stopService(summer);

                    getContext(). startService(truth);
                    song = "truth";
                } else if (music.getTitle().equalsIgnoreCase("Summer")) {
                    getContext().stopService(canon);
                    getContext().stopService(dream);
                    getContext().stopService(story);
                    getContext().stopService(kiss);
                    getContext().stopService(river);
                    getContext().stopService(truth);
                    getContext().stopService(summer);

                    getContext().startService(summer);
                    song = "summer";
                }

                editor.putString("song",song);
                editor.apply();
            }
        });

        builder.setView(view)
                .setTitle("Music")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        listener.applyMusic(song);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
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

    public interface MusicDialogListener{
//        void applyMusic(String music);
    }
}
