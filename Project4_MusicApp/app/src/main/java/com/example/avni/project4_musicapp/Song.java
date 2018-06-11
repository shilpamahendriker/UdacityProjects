package com.example.avni.project4_musicapp;

public class Song {
    private  String mTitle;
    private String mAlbumName;
    private String mArtistName;
    private String mGenre;
    private int mSongResourceId;

    public Song(String title,String albumName,String artistName,String genre,int songResourceId){
        this.mTitle = title;
        this.mAlbumName = albumName;
        this.mArtistName = artistName;
        this.mGenre = genre;
        this.mSongResourceId = songResourceId;

    }

    public Song(String albumName){
        mAlbumName = albumName;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getAlbumName(){
        return mAlbumName;
    }
    public String getArtistName(){
        return mArtistName;
    }
    public String getGenre(){
        return mGenre;
    }

    public int getSongResourceId() {
        return mSongResourceId;
    }
}
