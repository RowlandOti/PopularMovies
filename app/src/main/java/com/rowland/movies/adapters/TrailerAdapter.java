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
import com.rowland.movies.rest.models.Trailer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/30/2015.
 */
public class TrailerAdapter extends BaseAdapter {

    // A Context instance
    private Context context;
    // The list of menu items
    private List<Trailer> mTrailerList;

    // Default constructor
    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        this.context = context;
        this.mTrailerList = trailerList;
    }

    // Get the view at the position
    public View getView(int position, View convertView, ViewGroup parent) {
        // Don't operate on method parameter create new variable
        View view = convertView;
        // Get menu at position
        Trailer trailer = mTrailerList.get(position);
        // Unique view tag
        String tag = trailer.getName();
        // Check for null
        if (view == null) {
            // Acquire a context from parent
            Context context = parent.getContext();
            // Acquire the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Create new view
            view = inflater.inflate(R.layout.toolbar_overflow_item, parent, false);
        } else {
            // Get tag from given view
            String viewTag = (String) view.getTag();
            // Check if tag matches our code
            if (viewTag != null && viewTag.equals(tag)) {
                // Return View
                return view;
            }
        }
        //Otherwise the view is newly inflated
        ViewHolder viewholder = new ViewHolder(view);
        // Set the row icons
        viewholder.icon.setImageResource(mTrailerList.get(position).getProfilePic());
        // Set the row texts
        viewholder.name.setText(mTrailerList.get(position).getName());
        // Set tag to new view
        view.setTag(tag);
        // Return the view
        return view;
    }


    // ----------------------------------------
    //  Implemented
    // ----------------------------------------
    @Override
    public Object getItem(int index) {
        return mTrailerList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mTrailerList.size();
    }

    // Handy method for passing the list to the adapter
    public void addAll(List<Trailer> trailerList) {
        // Check for  null
        if (trailerList != null) {
            // Check for null
            if (mTrailerList == null) {
                // Create a new instance
                mTrailerList = new ArrayList<>();
            }
            // Add each movie to the initial list
            for (Trailer trailer : trailerList) {
                // Add movies
                mTrailerList.add(trailer);
            }

        }
        // Notify others of the data changes
    }

    static class ViewHolder {
        // The icon
        @Bind(R.id.icon_image_view)
        ImageView icon;
        @Bind(R.id.name_text_view)
        TextView name;

        public ViewHolder(View itemView) {
            // Instantiate all the views
            ButterKnife.bind(this, itemView);
        }
    }
}