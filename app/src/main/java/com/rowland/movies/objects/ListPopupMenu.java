package com.rowland.movies.objects;

/**
 * Created by Rowland on 7/14/2015.
 */
public class ListPopupMenu {

    private String name;
    private int icon;

    public ListPopupMenu(int profilepicRes, String name)
    {
        this.icon = profilepicRes;
        this.name = name;
    }

    public int getProfilePic()
    {
        return this.icon;
    }
    public String getName()
    {
        return this.name;
    }
}
