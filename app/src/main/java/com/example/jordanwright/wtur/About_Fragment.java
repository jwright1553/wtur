package com.example.jordanwright.wtur;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jordan on 10/14/15.
 */
public class About_Fragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View aboutView = inflater.inflate(R.layout.about_layout, container, false);
        return aboutView;
    }

}
