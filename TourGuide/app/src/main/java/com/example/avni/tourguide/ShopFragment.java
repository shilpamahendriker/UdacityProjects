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


public class ShopFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public ShopFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.locations_list, container, false);
        final ArrayList<Location> locations = new ArrayList<Location>();

        locations.add(new Location("Easton Town Center","160 Easton Town Center, Columbus, OH 43219","9.00AM-6PM","614-222-2222",R.string.eastonShortDesc,R.string.eastonDesc,R.drawable.eastoncenter));
        locations.add(new Location("The Mall at Tuttle Crossing","5043 Tuttle Crossing Blvd, Dublin, OH 43016","9.00AM-6PM","614-222-2222",R.string.tuttleShortDesc,R.string.tuttleDesc,R.drawable.tuttlecrossing));
        locations.add(new Location("Polaris Fashion Place","1500 Polaris Pkwy, Columbus, OH 43240","9.00AM-6PM","614-222-2222",R.string.polarisShortDesc,R.string.polarisDesc,R.drawable.polarisfashion));
        locations.add(new Location("Eastland Mall","2740 Eastland Mall B, Columbus, OH 43232","9.00AM-6PM","614-222-2222",R.string.eastonShortDesc,R.string.eastonDesc,R.drawable.eastland));
        locations.add(new Location("Tanger Outlets","400 South Wilson Road, Sunbury, OH 43074","9.00AM-6PM","614-222-2222",R.string.tangerShortDesc,R.string.tangerDesc,R.drawable.tangeroutlet));

        LocationAdapter adapter = new LocationAdapter(this.getActivity(), locations);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }







}
