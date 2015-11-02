package com.example.jordanwright.wtur;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by jordan on 10/24/15.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener {
    private static final String ACTION_PLAY = "com.example.action.PLAY";
    MediaPlayer mMediaPlayer = null;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("THILSFKDLS:", intent.toString());

            mMediaPlayer = MediaPlayer.create(getBaseContext(), Uri.parse("http://184.18.181.12:8038/;stream/1"));
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// initialize it here
            mMediaPlayer.setOnPreparedListener(this);
            //mMediaPlayer.prepareAsync(); // prepare async to not block main thread



        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    public void onCreate() {
        super.onCreate();
        Log.d("SERV", "IWAS MADE");
    }

    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
    }
}
