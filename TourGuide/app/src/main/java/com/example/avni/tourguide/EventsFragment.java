package com.example.avni.tourguide;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public EventsFragment() {
        // Required empty public constructor

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.locations_list, container, false);
        final ArrayList<Location> locations = new ArrayList<Location>();

        locations.add(new Location("Express Live!"," 405 Neil Ave, Columbus, OH 43215","9.00AM-6PM",R.string.columbusMuseumShortDesc,R.string.columbusMuseumDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("The Ohio Expo Center & State Fair","717 E 17th Ave, Columbus, OH 43211","9.00AM-6PM",R.string.cosiShortDesc,R.string.cosiDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("Dublin Irish Festival","DCRC dublin OH","9.00AM-6PM",R.string.columbuszooShortDesc,R.string.columbuszooDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("Jazz and Rib Festival","Scioto Mile","9.00AM-6PM",R.string.shortnorthShortDesc,R.string.shortnorthDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("Red, White & BOOM!","Downtown Columbus","9.00AM-6PM",R.string.wexnerShortDesc,R.string.wexnerDesc,R.drawable.ic_launcher_background));

        LocationAdapter adapter = new LocationAdapter(this.getActivity(), locations);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
