package com.example.jordanwright.wtur;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


/**
 * Created by jordan on 11/20/15.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    AudioManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        String networkStatus = Player_Fragment.getConnectivityStatusString(context);
        if (networkStatus == "Wifi enabled" || networkStatus == "Mobile data enabled"){
            if (!manager.isMusicActive()){
                context.startService(new Intent(context, MusicService.class));
            }
        }
        else {
            context.stopService(new Intent(context, MusicService.class));
        }
    }
}
