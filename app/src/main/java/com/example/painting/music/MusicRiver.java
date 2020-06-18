package com.example.painting.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.painting.R;

public class MusicRiver extends Service {
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (player==null){
            player = MediaPlayer.create(this, R.raw.river_flows_in_you);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    player.start();
                    player.setLooping(true);
                }
            });
        }
        player.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (player!=null){
            player.stop();
            player.release();
            player=null;
        }
    }
}
