package com.example.avni.project4_musicapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private Context mContext;
    private ArrayList<Song> mSongs;
    private int mSongResourceId;

    public SongAdapter(Context context, ArrayList<Song> songs, int songResourceId) {
        super(context,0, songs);
        mContext = context;
        mSongs = songs;
        mSongResourceId = songResourceId;

    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        // Get the data item for this position
        View listItemView = convertView;

        // Check if an existing view is being reused, otherwise inflate the view
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final Song currentTrack = getItem(position);
        // Lookup view for data population

        TextView title = (TextView)listItemView.findViewById(R.id.textView_songtitle);

        TextView albumName = (TextView) listItemView.findViewById(R.id.textView_album);
        TextView artistName = (TextView) listItemView.findViewById(R.id.textView_artist);


        ImageView playImageView = (ImageView)listItemView.findViewById(R.id.imagePlay);
        if (currentTrack.getSongResourceId() == 0) {

            playImageView.setVisibility(View.GONE);
        } else {
            playImageView.setImageResource(currentTrack.getSongResourceId());
            playImageView.setVisibility(View.VISIBLE);
        }
        // Populate the data into the template view using the data object
        title.setText(currentTrack.getTitle());
        albumName.setText(currentTrack.getAlbumName());
        artistName.setText(currentTrack.getArtistName());
        playImageView.setImageResource(currentTrack.getSongResourceId());
        // Return the completed view to render on screen


        return listItemView;
    }
}
