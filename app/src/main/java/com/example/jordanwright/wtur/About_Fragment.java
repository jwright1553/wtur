package com.example.jordanwright.wtur;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by jordan on 10/14/15.
 */
public class About_Fragment extends android.support.v4.app.Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);




        final View aboutView = inflater.inflate(R.layout.about_layout, container, false);

        // get the listview
        expListView = (ExpandableListView) aboutView.findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return aboutView;

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Monday");
        listDataHeader.add("Tuesday");
        listDataHeader.add("Wednesday");
        listDataHeader.add("Thursday");
        listDataHeader.add("Friday");

        // Adding child data
        List<String> monday = new ArrayList<String>();
        monday.add("8pm: A Day From Sunday");
        monday.add("9pm: Echoes");
        monday.add("9:30pm DJ Emily Nosek");


        List<String> tuesday = new ArrayList<String>();
        tuesday.add("6pm: Three Guys and a Hat");
        tuesday.add("7pm: Landry and Andy");
        tuesday.add("9pm: From Way Downtown");
        tuesday.add("10pm: Livin' Large");


        List<String> wednesday = new ArrayList<String>();
        wednesday.add("6pm: Three Guys and a Hat");
        wednesday.add("8pm: K9");

        List<String> thursday = new ArrayList<String>();
        thursday.add("4pm: The Finer Things");
        thursday.add("6pm: Just a Bit Outside");
        thursday.add("9pm: DJ Alex");

        List<String> friday = new ArrayList<String>();
        friday.add("9pm: From Way Downtown");
        listDataChild.put(listDataHeader.get(0), monday); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tuesday);
        listDataChild.put(listDataHeader.get(2), wednesday);
        listDataChild.put(listDataHeader.get(3), thursday);
        listDataChild.put(listDataHeader.get(4), friday);
    }

}
