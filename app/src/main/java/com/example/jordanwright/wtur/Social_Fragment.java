package com.example.jordanwright.wtur;

import android.app.Fragment;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListAdapter;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by jordan on 10/14/15.
 */
public class Social_Fragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new timelineBuilder().execute();
        return inflater.inflate(R.layout.social_layout, container, false);
    }

    class timelineBuilder extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            final UserTimeline searchTimeline = new UserTimeline.Builder()
                    .screenName("TaylorWTUR")
                    .build();
            final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                    .setTimeline(searchTimeline)
                    .build();
            runner(adapter);


            return null;
        }
    }

    private void runner(ListAdapter adpater){
        final ListAdapter adapter = adpater;
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                setListAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
