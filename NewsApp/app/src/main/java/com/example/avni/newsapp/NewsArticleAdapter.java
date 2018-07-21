package com.example.avni.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {
    public NewsArticleAdapter(@NonNull Context context, ArrayList<NewsArticle> newsArticles) {
        super(context, 0, newsArticles);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflate listview with list_item layout
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        } else
            listItemView = convertView;

        //Assign Title,Section, Author and publishedDate with respective data from the adpater
        final NewsArticle currentRow = getItem(position);
        TextView Title = listItemView.findViewById(R.id.titleView);
        Title.setText(currentRow.getTitle());

        TextView Section = listItemView.findViewById(R.id.sectionView);
        Section.setText(currentRow.getSection());

        TextView Author = listItemView.findViewById(R.id.authorView);
        Author.setText(currentRow.getAuthor());


        TextView PublishedDate = listItemView.findViewById(R.id.dateView);
        PublishedDate.setText(formatDate(currentRow.getPublishedDate()));

        return listItemView;
    }

    private String formatDate(String dateString) {
        // Method to format date string
        String year = dateString.substring(2, 4);
        String month = dateString.substring(5, 7);
        String date = dateString.substring(8, 10);

        switch (month) {
            case "01":
                month = "Jan";
                break;
            case "02":
                month = "Feb";
                break;
            case "03":
                month = "Mar";
                break;
            case "04":
                month = "Apr";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "Jun";
                break;
            case "07":
                month = "Jul";
                break;
            case "08":
                month = "Aug";
                break;
            case "09":
                month = "Sep";
                break;
            case "10":
                month = "Oct";
                break;
            case "11":
                month = "Nov";
                break;
            case "12":
                month = "Dec";
                break;

        }
        dateString = month + " " + date + ", " + year;
        return dateString;
    }
}
