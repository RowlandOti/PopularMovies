/*
 * Copyright 2015 Oti Rowland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.rowland.movies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rowland.movies.R;
import com.rowland.movies.objects.ListPopupMenu;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Rowland on 7/13/2015.
 */
public class ListPopupWindowAdapter extends BaseAdapter {
    // A Context instance
    private Context context;
    // The list of menu items
    private ArrayList<ListPopupMenu> mPopupMenuList;
    // The icon
    @Bind(R.id.icon_image_view)
    ImageView icon;
    @Bind(R.id.name_text_view)
    TextView name;

    // Default constructor
    public ListPopupWindowAdapter(Context context, ArrayList<ListPopupMenu> popupMenuList) {
        this.context = context;
        this.mPopupMenuList = popupMenuList;
    }
    // Get the view at the position
    public View getView(int position, View convertView, ViewGroup parent) {
        // Small List View , no need to recycle views
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Layout for the top row with profile picture /Avatar
        convertView = inflater.inflate(R.layout.toolbar_overflow_item, parent, false);
        // Set the row icons
        icon.setImageResource(mPopupMenuList.get(position).getProfilePic());
        // Set the row texts
        name.setText(mPopupMenuList.get(position).getName());
        // Return the view
        return convertView;
    }


    // ----------------------------------------
    //  Implemented
    // ----------------------------------------
    @Override
    public Object getItem(int index) {
        return mPopupMenuList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mPopupMenuList.size();
    }

}
