package com.example.avni.tourguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {


    //this context we will use to inflate the layout
    private Context mContext;



    //we are storing all the Locations in a list
    private ArrayList<Location> locations;

    //getting the context and Locations list with constructor
    public LocationAdapter(Context mContext, ArrayList<Location> locations) {
        this.mContext = mContext;
        this.locations = locations;

    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, null,true);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, final int position) {
        //getting the location of the specified position
        final Location location = locations.get(position);

        //binding the data with the viewholder views

        holder.textName.setText(location.getLocationName().toString());
        holder.textAddress.setText(location.getLocationAddress());
        holder.textDesc.setText(location.getShortDescResId());
        if (location.getLocationImageId()== 0){
            holder.imgView.setVisibility(View.GONE);
        }
        else {
            holder.imgView.setImageResource(location.getLocationImageId());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location selection = locations.get(position);
                Intent intent = new Intent(mContext, LocationDetailsActivity.class);
                intent.putExtra("NAME", selection.getLocationName());
                intent.putExtra("ADDRESS", selection.getLocationAddress());
                intent.putExtra("HOURS",selection.getHours());
                intent.putExtra("DESC", selection.getmFullDescResId());
                intent.putExtra("IMAGEID", selection.getLocationImageId());
                intent.putExtra("PHONE", selection.getPhoneNo());


                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return locations.size();
    }


    class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textName,textAddress,textDesc;
        ImageView imgView;


        public LocationViewHolder(View itemView) {
            super(itemView);

             textName = itemView.findViewById(R.id.textLocationName);
            textAddress = itemView.findViewById(R.id.textLocationAddress);
            textDesc = itemView.findViewById(R.id.textLocationDesc);

            imgView = itemView.findViewById(R.id.imageResource);
        }
    }
}
