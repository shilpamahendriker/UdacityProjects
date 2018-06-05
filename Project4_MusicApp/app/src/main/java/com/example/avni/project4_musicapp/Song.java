package com.example.avni.project4_musicapp;

public class Song {
    private  String mTitle;
    private String mAlbumName;
    private String mArtistName;
    private int mSongResourceId;

    public Song(String title,String albumName,String artistName,int songResourceId){
        this.mTitle = title;
        this.mAlbumName = albumName;
        this.mArtistName = artistName;
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

    public int getSongResourceId() {
        return mSongResourceId;
    }
}
