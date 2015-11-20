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
    PhoneStateListener phoneStateListener;
    AudioManager.OnAudioFocusChangeListener listener;.
    Boolean serviceRunning;

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;



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
        serviceRunning = false;

        listener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int i) {
                switch (i)
                {
                    case AudioManager.AUDIOFOCUS_GAIN:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        getActivity().getBaseContext().startService(new Intent(getActivity().getBaseContext(), MusicService.class));
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));
                        break;
                }
            }
        };

        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

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
        //Boolean network = true;
            Log.d("NETWORKSTUFF", Boolean.toString(network));
        if (network) {

            if (!manager.isMusicActive()) {
                btn.setBackgroundResource(R.drawable.trans_pause);
                manager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                getActivity().getBaseContext().startService(new Intent(getActivity().getBaseContext(), MusicService.class));
                Log.d("HELLO", "TRIEDTOSTART");

            } else {
                Log.d("BROKEN", "DOWQN");
                btn.setBackgroundResource(R.drawable.trans_play);
                getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No network connection available", Toast.LENGTH_LONG).show();
            btn.setBackgroundResource(R.drawable.trans_play);
            getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));

        }

        }
    };


    public boolean isDataConnected() {
        try {
            Log.d("TRYING","TRYING");
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo serviceInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            //Log.d("NEWNETWORK", Boolean.toString(networkInfo.isConnected()));
            return (wifiInfo.isConnectedOrConnecting() || serviceInfo.isConnectedOrConnecting());
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        TelephonyManager mgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));

    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = Player_Fragment.getConnectivityStatus(context);
        String status = null;
        if (conn == Player_Fragment.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == Player_Fragment.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == Player_Fragment.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }


}
