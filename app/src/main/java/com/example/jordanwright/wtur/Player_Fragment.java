package com.example.jordanwright.wtur;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by jordan on 10/14/15.
 */
public class Player_Fragment extends android.support.v4.app.Fragment{

    Button btn;
    Button refresh;
    AudioManager manager;
    MainActivity mainActivity;


    private Boolean playPause;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View playerView = inflater.inflate(R.layout.media_player_view, container, false);
        btn = (Button) playerView.findViewById(R.id.button1);
        refresh = (Button) playerView.findViewById(R.id.refresh);
        Log.d("THAGHASLD","WE MADE IT HERE");
        manager = (AudioManager) this.getActivity().getSystemService(Context.AUDIO_SERVICE);
        btn.setOnClickListener(pausePlay);
        refresh.setOnClickListener(refresher);
        return playerView;


    }

    private View.OnClickListener refresher = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (manager.isMusicActive()) {
                getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));
                getActivity().getBaseContext().startService(new Intent(getActivity().getBaseContext(), MusicService.class));
            }
        }
    };

    private View.OnClickListener pausePlay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub
        Boolean network = isDataConnected();
        Log.d("NETWORKSTUFF", Boolean.toString(network));
        if (network) {

            if (!manager.isMusicActive()) {
                btn.setBackgroundResource(R.drawable.trans_pause);
                getActivity().getBaseContext().startService(new Intent(getActivity().getBaseContext(), MusicService.class));
                Log.d("HELLO", "TRIEDTOSTART");

            } else {
                Log.d("BROKEN", "DOWQN");
                btn.setBackgroundResource(R.drawable.trans_play);
                getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No network connection available", Toast.LENGTH_LONG).show();
        }

        }
    };


    private boolean isDataConnected() {
        try {
            Log.d("TRYING","TRYING");
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            Log.d("NEWNETWORK", Boolean.toString(networkInfo.isConnected()));
            return networkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    @Override
    public void onResume() {
        Log.d("RESELECTED", String.valueOf(manager.isMusicActive()));
        if (manager.isMusicActive()){
            btn.setBackgroundResource(R.drawable.trans_pause);
        }
        else {
            btn.setBackgroundResource(R.drawable.trans_play);
        }
        super.onResume();
    }




}
