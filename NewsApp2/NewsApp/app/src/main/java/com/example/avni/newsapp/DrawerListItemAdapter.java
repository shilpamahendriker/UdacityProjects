package com.example.avni.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/*Create adapter for naviagtion drawer array*/
public class DrawerListItemAdapter extends ArrayAdapter<DrawerListItem>{
    public DrawerListItemAdapter(@NonNull Context context, int layoutResourceId, DrawerListItem[] itemsList) {
        super(context, 0, itemsList);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*inflate listview with nav_drawer_item_listview layout*/
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.nav_drawer_item_listview, parent, false);
        } else
            listItemView = convertView;

        final DrawerListItem currentRow = getItem(position);
        ImageView imageViewIcon = listItemView.findViewById(R.id.imageViewIcon);
        imageViewIcon.setImageResource(currentRow.getDrawerListItemIcon());
        TextView textViewName = listItemView.findViewById(R.id.textViewName);
        textViewName.setText(currentRow.getDrawerListItemName());
        return listItemView;
    }

}
