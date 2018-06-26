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


public class SightsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public SightsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.locations_list, container, false);
        final ArrayList<Location> locations = new ArrayList<Location>();

        locations.add(new Location("Columbus Museum of art","480 E Broad St, Columbus, OH 43215","9.00AM-6PM","614-222-2222",R.string.columbusMuseumShortDesc,R.string.columbusMuseumDesc,R.drawable.columbusmuseum));
        locations.add(new Location("COSI","333 W Broad St, Columbus, OH 43215","9.00AM-6PM","614-222-2222",R.string.cosiShortDesc,R.string.cosiDesc,R.drawable.cosi));
        locations.add(new Location("Columbus Zoo","4850 W Powell Rd, Powell, OH 43065","9.00AM-6PM","614-222-2222",R.string.columbuszooShortDesc,R.string.columbuszooDesc,R.drawable.columbuszoo));
        locations.add(new Location("Short North Art District"," N High St, Columbus, OH 43215","9.00AM-6PM","614-222-2222",R.string.shortnorthShortDesc,R.string.shortnorthDesc,R.drawable.shortnorthartdistrict));
        locations.add(new Location("Wexner Center for Art","1871 N High St, Columbus, OH 43210","9.00AM-6PM","614-222-2222",R.string.wexnerShortDesc,R.string.wexnerDesc,R.drawable.wexnercenter));

        locations.add(new Location("Franklin Art","1777 E Broad St, Columbus, OH 43203","9.00AM-6PM","614-222-2222",R.string.franklinartShortDesc,R.string.franklinartDesc,R.drawable.franklinart));
        locations.add(new Location("Gateway film centre","1550 N High St, Columbus, OH 43201","9.00AM-6PM","614-222-2222",R.string.gatewayShortDesc,R.string.gatewayDesc,R.drawable.gatewayfilmcenter));

        LocationAdapter adapter = new LocationAdapter(this.getActivity(),locations);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
      return rootView;

    }








}
