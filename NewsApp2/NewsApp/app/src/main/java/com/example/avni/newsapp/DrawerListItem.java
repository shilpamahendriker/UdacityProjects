package com.example.avni.newsapp;


/*Create class for navigation drawer items*/
public class DrawerListItem {
    public int drawerListItemIcon;
    public int drawerListItemName;

    /*Create Constructor*/
    public DrawerListItem(int icon, int name) {

        this.drawerListItemIcon = icon;
        this.drawerListItemName = name;
    }

    /*Create getter methods*/
    public int getDrawerListItemIcon() {
        return drawerListItemIcon;
    }

    public int getDrawerListItemName() {
        return drawerListItemName;
    }
}
