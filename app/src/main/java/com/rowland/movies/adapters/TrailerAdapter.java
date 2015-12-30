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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.R;
import com.rowland.movies.rest.enums.EBaseURlTypes;
import com.rowland.movies.rest.models.Trailer;
import com.rowland.movies.utilities.Utilities;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/30/2015.
 */
public class TrailerAdapter extends BaseAdapter {

    // Logging Identifier for class
    private final String LOG_TAG = TrailerAdapter.class.getSimpleName();
    // A Context instance
    private Context context;
    // The list of menu items
    private List<Trailer> mTrailerList;
    // The parent layout
    private LinearLayout mTrailerLinearLayout;

    // Default constructor
    public TrailerAdapter(Context context, List<Trailer> trailerList, LinearLayout trailerLinearLayout) {
        this.context = context;
        this.mTrailerList = trailerList;
        this.mTrailerLinearLayout = trailerLinearLayout;
    }

    // Get the view at the position
    public View getView(int position, View convertView, ViewGroup parent) {
        // Don't operate on method parameter create new variable
        View trailerView = convertView;
        // Get menu at position
        Trailer trailer = mTrailerList.get(position);
        // Unique view tag
        String tag = trailer.getKey();
        // Check for null
        if (trailerView == null) {
            // Acquire a context from parent
            Context context = parent.getContext();
            // Acquire the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Create new view
            trailerView = inflater.inflate(R.layout.inc_trailer_detail, mTrailerLinearLayout, false);
        } else {
            // Get tag from given view
            String viewTag = (String) trailerView.getTag();
            // Check if tag matches our code
            if (viewTag != null && viewTag.equals(tag)) {
                // Return View
                return trailerView;
            }
        }
        //Otherwise the view is newly inflated
        ViewHolder viewHolder = new ViewHolder(trailerView);
        // Bind the data to the view holder
        viewHolder.bindTo(trailer);
        // Add the view to parent
        mTrailerLinearLayout.addView(trailerView);
        // Set tag to new view
        trailerView.setTag(tag);
        // Return the view
        return trailerView;
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
            // Add movies
            mTrailerList = trailerList;
        }
        // Check whether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "Movie: " + mTrailerList.size());
        }
    }

    static class ViewHolder {
        // The trailer thumbnail
        @Bind(R.id.trailer_thumbnail_image_view)
        ImageView mTrailerThumbnailImageView;

        public ViewHolder(View itemView) {
            // Instantiate all the views
            ButterKnife.bind(this, itemView);
        }

        // Bind the data to the holder views
        private void bindTo(final Trailer trailer) {
            // Build the image url
            String imageUrl = String.format(EBaseURlTypes.YOUTUBE_THUMNAIL_URL.getUrlType(), trailer.getKey());
            // Use Picasso to load the trailer thumbnail
            Picasso.with(mTrailerThumbnailImageView.getContext())
                    .load(imageUrl)
                    .networkPolicy(Utilities.NetworkUtility.isNetworkAvailable(mTrailerThumbnailImageView.getContext()) ? NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .into(mTrailerThumbnailImageView);
            // Set a click listener
            mTrailerThumbnailImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Use Intents to play trailer
                }
            });
        }
    }
}