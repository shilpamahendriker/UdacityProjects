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


public class EatFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public EatFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.locations_list, container, false);
        final ArrayList<Location> locations = new ArrayList<Location>();

        locations.add(new Location("Hot Chicken Takeover","4203 N High St, Columbus, OH 43214","9.00AM-6PM",R.string.columbusMuseumShortDesc,R.string.columbusMuseumDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("The Refectory Restaurant and Bistro","1092 Bethel Rd, Columbus, OH 43220","9.00AM-6PM",R.string.cosiShortDesc,R.string.cosiDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("Z Cucina di Spirito","1368 Grandview Ave, Columbus, OH 43212","9.00AM-6PM",R.string.columbuszooShortDesc,R.string.columbuszooDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("Hyde Park Prime Steakhouse","6360 Frantz Rd, Dublin, OH 43017","9.00AM-6PM",R.string.shortnorthShortDesc,R.string.shortnorthDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("Northstar Café in Beechwold","4241 N High St, Columbus, OH 43214","9.00AM-6PM",R.string.wexnerShortDesc,R.string.wexnerDesc,R.drawable.ic_launcher_background));

        locations.add(new Location("The Pearl","641 N High St, Columbus, OH 43215","9.00AM-6PM",R.string.franklinartShortDesc,R.string.franklinartDesc,R.drawable.ic_launcher_background));
        locations.add(new Location("Mozart's","4784 N High St, Columbus, OH 43214","9.00AM-6PM",R.string.gatewayShortDesc,R.string.gatewayDesc,R.drawable.ic_launcher_background));

        LocationAdapter adapter = new LocationAdapter(this.getActivity(), locations);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;


    }

}
