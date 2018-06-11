package com.example.avni.project4_musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;


public class FullPlaylist extends AppCompatActivity {
    public final ArrayList<Song> songs = new ArrayList<Song>();
    String value,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            category = extras.getString("category");

        }
        //Adding data to the Song ArrayList
       songs.add(new Song("Perfect","÷","Ed Shareen","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Shape of you","÷","Ed Shareen","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Thinking out loud","x","Ed Shareen","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Photogragh","x","Ed Shareen","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Roar","Prism","Katy Perry","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Legendary Lovers","Prism","Katy Perry","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Birthday","Prism","Katy Perry","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Let it go","Frozen","Idina Menzel","Pop",R.drawable.ic_launcher_background));
        songs.add(new Song("In My blood","Shawn Mendes","Shawn Mendes","Pop",R.drawable.ic_launcher_background));
        songs.add(new Song("God's Plan","Scary Hours","Drake","Pop",R.drawable.ic_launcher_background));
        songs.add(new Song("Can't Stop the Feeling","Trolls","Justin TimberLake","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("Move your feet","Trolls","Anna Kendrick","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("True Colors","Trolls","Anna Kendrick","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("How Far I'll Go","Moana","Auli'i Cravalho","Pop",R.drawable.ic_play_arrow));
        songs.add(new Song("I am Moana","Moana","Auli'i Cravalho","Jazz",R.drawable.ic_play_arrow));
        songs.add(new Song("They say go slow","Lego Ninjago","The Fold","Jazz",R.drawable.ic_play_arrow));
        songs.add(new Song("Who is the (Bat)Man","Lego Batman","Patric Stump","Classical",R.drawable.ic_play_arrow));
        songs.add(new Song("My Hips dont Lie","Oral Fixation Vol.2","Shakira","Rock",R.drawable.ic_play_arrow));
        songs.add(new Song("Try Everything","Zootopia","Shakira","Rock",R.drawable.ic_play_arrow));
        songs.add(new Song("Perro Fiel","El Dorado","Shakira","Classical",R.drawable.ic_play_arrow));

        //Extracting Artist Names only from Song ArrayList and
       final ArrayList<Song> artistNames = new ArrayList<>();
        int songscount = songs.size();
        artistNames.add(new Song("","",songs.get(0).getArtistName(),"",0));
        for (int i = 1; i < songscount; i++) {
            if(songs.get(i).getArtistName() !=songs.get(i-1).getArtistName()){
               artistNames.add(new Song("","",songs.get(i).getArtistName(),"",0));
            }

        }

        final ArrayList<Song> artistSongs = new ArrayList<>();
        for (int i = 0; i < songscount; i++) {
            if(songs.get(i).getArtistName().equals(value)){
                artistSongs.add(songs.get(i));
            }

        }



        final ArrayList<Song> genreNames = new ArrayList<>();

        genreNames.add(new Song("","","",songs.get(0).getGenre(),0));
        for (int i = 1; i < songscount; i++) {
            if(songs.get(i).getGenre() !=songs.get(i-1).getGenre()) {
                genreNames.add(new Song("", "", "", songs.get(i).getGenre(), 0));
            }
        }
        final ArrayList<Song> genreSongs = new ArrayList<>();
        for (int i = 0; i < songscount; i++) {
            if(songs.get(i).getGenre().equals(value)){
                genreSongs.add(songs.get(i));
            }

        }
        final ArrayList<Song> albumNames = new ArrayList<>();

        albumNames.add(new Song("",songs.get(0).getAlbumName(),"","",0));
        for (int i = 1; i < songscount; i++) {
            if(songs.get(i).getAlbumName() !=songs.get(i-1).getAlbumName()) {
                albumNames.add(new Song("", songs.get(i).getAlbumName(), "", "", 0));
            }
        }
        final ArrayList<Song> albumSongs = new ArrayList<>();
        for (int i = 0; i < songscount; i++) {
            if(songs.get(i).getAlbumName().equals(value)){
                albumSongs.add(songs.get(i));
            }

        }



        ListView listView = (ListView) findViewById(R.id.list);
        if(value.equals("TopCharts")){
              SongAdapter itemsAdapter = new SongAdapter(this,songs, R.drawable.ic_play_arrow);

                listView.setAdapter(itemsAdapter);

        }else if (value.equals("Artist")){
            SongAdapter itemsAdapter = new SongAdapter(this,artistNames, R.drawable.ic_play_arrow);

            listView.setAdapter(itemsAdapter);
        }else if(value.equals("Album")){
            SongAdapter itemsAdapter = new SongAdapter(this,albumNames, R.drawable.ic_play_arrow);

            listView.setAdapter(itemsAdapter);
        }else if(value.equals("Genre")){
            SongAdapter itemsAdapter = new SongAdapter(this,genreNames, R.drawable.ic_play_arrow);

            listView.setAdapter(itemsAdapter);


        }else {


            if (category.equals("artist")) {
                Log.v("msg", "1");
                SongAdapter itemsAdapter = new SongAdapter(this, artistSongs, R.drawable.ic_play_arrow);

                listView.setAdapter(itemsAdapter);
            } else if (category.equals("genre")) {
                Log.v("msg", "2");
                SongAdapter itemsAdapter = new SongAdapter(this, genreSongs, R.drawable.ic_play_arrow);

                listView.setAdapter(itemsAdapter);
            } else if (category.equals("album")) {
                Log.v("msg", "3");
                SongAdapter itemsAdapter = new SongAdapter(this, albumSongs, R.drawable.ic_play_arrow);

                listView.setAdapter(itemsAdapter);
            /*}else{
                Log.v("msg",value);
                SongAdapter itemsAdapter = new SongAdapter(this, songs, R.drawable.ic_play_arrow);

                listView.setAdapter(itemsAdapter);
            }*/

            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(value.trim().equals("Artist")){
                    Song selection = artistNames.get(position);
                    Intent intent = new Intent(FullPlaylist.this, FullPlaylist.class);
                    intent.putExtra("key", selection.getArtistName());
                    intent.putExtra("category", "artist");
                    startActivity(intent);
                }
                if(value.trim().equals("Album")){
                    Song selection = albumNames.get(position);
                    Intent intent = new Intent(FullPlaylist.this, FullPlaylist.class);
                    intent.putExtra("key", selection.getAlbumName());
                    intent.putExtra("category", "album");
                    startActivity(intent);
                }
                if(value.trim().equals("Genre")){
                    Song selection = genreNames.get(position);
                    Intent intent = new Intent(FullPlaylist.this, FullPlaylist.class);
                    intent.putExtra("key", selection.getGenre());
                    intent.putExtra("category", "genre");
                    startActivity(intent);
                }else {
                    Song selection = songs.get(position);
                    Intent intent = new Intent(FullPlaylist.this, NowPlayingTrack.class);
                    intent.putExtra("TITLE", selection.getTitle());
                    intent.putExtra("ARTIST", selection.getArtistName());
                    intent.putExtra("ALBUM", selection.getAlbumName());
                    intent.putExtra("GENRE", selection.getGenre());
                    startActivity(intent);
                }

            }
        });


    }

}
