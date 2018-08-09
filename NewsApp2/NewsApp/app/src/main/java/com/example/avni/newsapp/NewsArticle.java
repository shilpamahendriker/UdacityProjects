package com.example.avni.newsapp;

public class NewsArticle {

    //create new class for news article
    private String Title;
    private String Section;
    private String PublishedDate;
    private String Url;
    private String Author;

    //create constructor

    public NewsArticle(String title, String section, String publishedDate, String url, String author) {
        Title = title;
        Section = section;
        PublishedDate = publishedDate;
        Url = url;
        Author = author;
    }

    //create getter methods
    public String getTitle() {
        return Title;
    }

    public String getSection() {
        return Section;
    }

    public String getPublishedDate() {
        return PublishedDate;
    }

    public String getUrl() {
        return Url;
    }

    public String getAuthor() {
        return Author;
    }
}
