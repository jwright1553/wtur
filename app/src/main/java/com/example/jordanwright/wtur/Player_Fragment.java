package com.example.jordanwright.wtur;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by jordan on 10/14/15.
 */
public class Player_Fragment extends android.support.v4.app.Fragment{

    Button btn;
    Boolean playPause = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View playerView = inflater.inflate(R.layout.media_player_view, container, false);
        btn = (Button) playerView.findViewById(R.id.button1);
        Log.d("THAGHASLD","WE MADE IT HERE");
        btn.setOnClickListener(pausePlay);
        return playerView;
    }

    private View.OnClickListener pausePlay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub

            if(playPause) {
                btn.setBackgroundResource(R.drawable.trans_pause);
                getActivity().getBaseContext().startService(new Intent(getActivity().getBaseContext(), MusicService.class));
                Log.d("HELLO","TRIEDTOSTART");
                playPause = false;
            }

            else {
                Log.d("BROKEN", "DOWQN");
                btn.setBackgroundResource(R.drawable.trans_play);
                getActivity().getBaseContext().stopService(new Intent(getActivity().getBaseContext(), MusicService.class));
                playPause = true;
            }

        }
    };


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    @Override
    public void onResume() {

        super.onResume();
    }


}
