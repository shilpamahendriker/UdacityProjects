package com.example.avni.project4_musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class FullPlaylist extends AppCompatActivity {
    final ArrayList<Song> songs = new ArrayList<Song>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

       songs.add(new Song("Perfect","÷","Ed Shareen",R.drawable.ic_play_arrow));
        songs.add(new Song("Shape of you","÷","Ed Shareen",R.drawable.ic_play_arrow));
        songs.add(new Song("Thinking out loud","x","Ed Shareen",R.drawable.ic_play_arrow));
        songs.add(new Song("Photogragh","x","Ed Shareen",R.drawable.ic_play_arrow));
        songs.add(new Song("Roar","Prism","Katy Perry",R.drawable.ic_play_arrow));
        songs.add(new Song("Legendary Lovers","Prism","Katy Perry",R.drawable.ic_play_arrow));
        songs.add(new Song("Birthday","Prism","Katy Perry",R.drawable.ic_play_arrow));
        songs.add(new Song("Let it go","Frozen","Idina Menzel",R.drawable.ic_launcher_background));
        songs.add(new Song("In My blood","Shawn Mendes","Shawn Mendes",R.drawable.ic_launcher_background));
        songs.add(new Song("God's Plan","Scary Hours","Drake",R.drawable.ic_launcher_background));
        songs.add(new Song("Can't Stop the Feeling","Trolls","Justin TimberLake",R.drawable.ic_play_arrow));
        songs.add(new Song("Move your feet","Trolls","Anna Kendrick",R.drawable.ic_play_arrow));
        songs.add(new Song("True Colors","Trolls","Anna Kendrick",R.drawable.ic_play_arrow));
        songs.add(new Song("How Far I'll Go","Moana","Auli'i Cravalho",R.drawable.ic_play_arrow));
        songs.add(new Song("I am Moana","Moana","Auli'i Cravalho",R.drawable.ic_play_arrow));
        songs.add(new Song("They say go slow","Lego Ninjago","The Fold",R.drawable.ic_play_arrow));
        songs.add(new Song("Who is the (Bat)Man","Lego Batman","Patric Stump",R.drawable.ic_play_arrow));
        songs.add(new Song("My Hips dont Lie","Oral Fixation Vol.2","Shakira",R.drawable.ic_play_arrow));
        songs.add(new Song("Try Everything","Zootopia","Shakira",R.drawable.ic_play_arrow));
        songs.add(new Song("Perro Fiel","El Dorado","Shakira",R.drawable.ic_play_arrow));






        SongAdapter itemsAdapter = new SongAdapter(this,songs);
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

    }

}
