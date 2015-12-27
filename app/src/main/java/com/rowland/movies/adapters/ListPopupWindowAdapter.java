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

/**
 * Created by Rowland on 7/13/2015.
 */
public class ListPopupWindowAdapter extends BaseAdapter {

    // ----------------------------------------
    // Variables
    // ----------------------------------------
    private Context context;
    private ArrayList<ListPopupMenu> mItem;
    // ----------------------------------------
    // Methods
    // ----------------------------------------

    public ListPopupWindowAdapter(Context context, ArrayList<ListPopupMenu> iTem) {
        this.context = context;
        this.mItem = iTem;
    }

    // ----------------------------------------

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView icon;
        TextView name;
        boolean isWithPicture = (mItem.get(position).getProfilePic() != 0);

        // Small List View , no need to recycle views
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Is this the row with the p.picture
        if (isWithPicture) {
            //Layout for the top row with profile picture /Avatar
            convertView = inflater.inflate(R.layout.toolbar_overflow_item_row, parent, false);

            icon = (ImageView) convertView.findViewById(R.id.imageProfilePic);
            icon.setImageResource(mItem.get(position).getProfilePic());
        } else {
            //Layout for the other layout without an images
            convertView = inflater.inflate(R.layout.toolbar_overflow_item, parent, false);
        }


        name = (TextView) convertView.findViewById(R.id.textViewName);
        name.setText(mItem.get(position).getName());


        return convertView;
    }


    // ----------------------------------------
    //  Implemented
    // ----------------------------------------
    @Override
    public Object getItem(int index) {
        return mItem.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

}
