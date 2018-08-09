package com.example.avni.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        listItemView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        //Assign Title,Section, Author and publishedDate with respective data from the adpater
        final NewsArticle currentRow = getItem(position);
        TextView Title = listItemView.findViewById(R.id.titleView);
        Title.setText(currentRow.getTitle());

        TextView Section = listItemView.findViewById(R.id.sectionView);
        Section.setText(currentRow.getSection());

        TextView Author = listItemView.findViewById(R.id.authorView);
        Author.setText(currentRow.getAuthor());


        TextView PublishedDate = listItemView.findViewById(R.id.dateView);
        PublishedDate.setText(convertDateFormat(currentRow.getPublishedDate()));

        return listItemView;
    }

    /* Method to convert date format*/

      private String convertDateFormat(String dateString) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date = null;
        try {
            date = originalFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);  // 20120821
        return formattedDate;
    }
}
