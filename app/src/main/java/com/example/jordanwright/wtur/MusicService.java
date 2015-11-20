package com.example.jordanwright.wtur;

import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

            if (isDataConnected()) {
                mMediaPlayer = MediaPlayer.create(getBaseContext(), Uri.parse("http://184.18.181.12:8038/;stream/1"));
                if (mMediaPlayer != null) {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setOnPreparedListener(this);
                }
            }




        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean isDataConnected() {
        try {
            Log.d("TRYING","TRYING");
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            Log.d("NEWNETWORK", Boolean.toString(networkInfo.isConnected()));
            return networkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

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
