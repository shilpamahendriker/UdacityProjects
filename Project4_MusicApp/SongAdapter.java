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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        View listItemView = convertView;

        // Check if an existing view is being reused, otherwise inflate the view
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final Song currentTrack = getItem(position);
        // Lookup view for data population

        TextView title = (TextView)listItemView.findViewById(R.id.textView_songtitle);
        if (currentTrack.getTitle().equals("")){
            title.setVisibility(View.GONE);
        }
        else {
            title.setText(currentTrack.getTitle());
            title.setVisibility(View.VISIBLE);
        }

        TextView albumName = (TextView) listItemView.findViewById(R.id.textView_album);
        if (currentTrack.getAlbumName().equals("")){
            albumName.setVisibility(View.GONE);
        }
        else {
            albumName.setText(currentTrack.getAlbumName());
            albumName.setVisibility(View.VISIBLE);
            albumName.setTextSize(16);
        }

        TextView artistName = (TextView) listItemView.findViewById(R.id.textView_artist);
        if (currentTrack.getArtistName().equals("")){
            artistName.setVisibility(View.GONE);
        }
        else {
            artistName.setText(currentTrack.getArtistName());
            artistName.setVisibility(View.VISIBLE);
            artistName.setTextSize(16);
        }
        TextView genre = listItemView.findViewById(R.id.textView_genre);
        if (currentTrack.getGenre().equals("")){
            genre.setVisibility(View.GONE);

        }
        else {
            genre.setText(currentTrack.getGenre());
            genre.setVisibility(View.VISIBLE);
            genre.setTextSize(16);
        }


        ImageView playImageView = (ImageView)listItemView.findViewById(R.id.imagePlay);
        if (currentTrack.getSongResourceId() == 0) {

            playImageView.setVisibility(View.GONE);
        } else {
            playImageView.setImageResource(currentTrack.getSongResourceId());
            playImageView.setVisibility(View.VISIBLE);
        }
        // Populate the data into the template view using the data object

        //albumName.setText(currentTrack.getAlbumName());
        //artistName.setText(currentTrack.getArtistName());
        //genre.setText(currentTrack.getGenre());
       // playImageView.setImageResource(currentTrack.getSongResourceId());
        // Return the completed view to render on screen


        return listItemView;
    }
}
