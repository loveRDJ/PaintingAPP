package com.example.painting.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.painting.R;

//https://www.youtube.com/watch?v=p2ffzsCqrs8&t=552s
//https://www.youtube.com/watch?v=C_Ka7cKwXW0&t=197s
//https://www.youtube.com/watch?v=nwPmuIrrsoA&t=186s
public class MusicCanon extends Service {

    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (player==null){
            player = MediaPlayer.create(this, R.raw.canon);
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
