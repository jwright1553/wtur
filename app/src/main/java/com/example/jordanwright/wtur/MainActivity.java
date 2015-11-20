package com.example.jordanwright.wtur;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private static final String TWITTER_KEY = "dpTIRbmwH4q1pwInn2eijtLQ5";
    private static final String TWITTER_SECRET = "0mCAx9leXVxdPvCPzRR6PiNP4fXKo5p5ZQocxA0aZthpftN6k9";



    Boolean playPauseMain;
    ViewPager pageSwitcher;
    fragPager herosSidekick;

    PhoneStateListener phoneStateListener;

    public Boolean getPlayPauseMain(){
        return playPauseMain;
    }

    public void setPlayPauseMain(Boolean changed){
        playPauseMain = changed;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        if (iAmAPhone()) {
            setContentView(R.layout.activity_main);


            playPauseMain = false;
            final android.support.v7.app.ActionBar lastActionHero = getSupportActionBar();

            lastActionHero.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

            herosSidekick = new fragPager(getSupportFragmentManager());

            pageSwitcher = (ViewPager) findViewById(R.id.book);
            pageSwitcher.setAdapter(herosSidekick);

            pageSwitcher.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int spot) {
                    lastActionHero.setSelectedNavigationItem(spot);
                }
            });

            for (int tab = 0; tab < herosSidekick.getCount(); tab++) {
                lastActionHero.addTab(lastActionHero.newTab().setText(herosSidekick.getPageTitle(tab)).setTabListener(new android.support.v7.app.ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                        pageSwitcher.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

                    }

                    @Override
                    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

                    }
                }));
            }

        }
        else{
            setContentView(R.layout.activity_main);
        }

        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //A call is dialing, active or on hold
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft){
        pageSwitcher.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    public boolean iAmAPhone(){
        String devType = getResources().getString(R.string.dev_type);

        if(devType.equalsIgnoreCase("PHONE")){
            return true;
        }

        else{
            return false;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class fragPager extends android.support.v4.app.FragmentPagerAdapter{
        public fragPager(FragmentManager mrBossMan) {super(mrBossMan);}


        @Override
        public android.support.v4.app.Fragment getItem(int position){
            android.support.v4.app.Fragment fragment = null;
            switch(position){
                case 0:
                    fragment = new Player_Fragment();
                    break;
                case 1:
                    fragment = new About_Fragment();
                    break;
                case 2:
                    fragment = new Social_Fragment();
                    break;
            }
            return fragment;

        }

        @Override
        public CharSequence getPageTitle(int tab) {
            Locale title = Locale.getDefault();
            if(tab == 0){ return "Player";}
            else if (tab == 2){ return "Social"; }
            else {return "About Us";}
        }

        @Override
        public int getCount(){
            return 3;
        }
    }





}



