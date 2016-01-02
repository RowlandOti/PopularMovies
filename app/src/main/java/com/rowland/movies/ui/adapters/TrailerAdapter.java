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

package com.rowland.movies.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
 * Created by Oti Rowland on 12/31/2015.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.CustomViewHolder> {

    // The class Log identifier
    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();
    // A list of the movie items
    private List<Trailer> mTrailerList;

    // Default constructor
    public TrailerAdapter(List<Trailer> trailerList) {
        this.mTrailerList = trailerList;
    }

    // Called when RecyclerView needs a new CustomViewHolder of the given type to represent an item.
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout to inflate for CustomViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inc_trailer_detail, parent, false);
        // Return new new CustomViewHolder
        return new CustomViewHolder(v);
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // Acquire Trailer item at this position
        final Trailer trailer = mTrailerList.get(position);
        // Bind the data to the view holder
        holder.bindTo(trailer);
    }

    // What's the size of the movie List
    @Override
    public int getItemCount() {
        // Check size of List first
        if (mTrailerList != null) {
            // Check wether we are in debug mode
            if (BuildConfig.IS_DEBUG_MODE) {
                Log.d(LOG_TAG, "List Count: " + mTrailerList.size());
            }
            return mTrailerList.size();
        }
        return 0;
    }

    // Handy method for passing the list to the adapter
    public void addAll(List<Trailer> trailerList) {

        if (trailerList != null) {
            // Check for null
            if (mTrailerList == null) {
                // Create a new instance
                mTrailerList = new ArrayList<>();
            }
            // Assign
            mTrailerList = trailerList;
        }

    }

    // Takes care of the overhead of recycling and gives better performance and scrolling
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.trailer_thumbnail_image_view)
        ImageView mTrailerThumbnailImageView;


        public CustomViewHolder(View itemView) {
            super(itemView);
            // Initialize the views
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
                    // Acquire the video url
                    String trailerUrl = String.format(EBaseURlTypes.YOUTUBE_VIDEO_URL.getUrlType(), trailer.getKey());
                    // Create a View Intent object
                    Intent youTubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                    // Set any data to send
                    youTubeIntent.putExtra("force_fullscreen", true);
                    // Use Intents to play trailer
                    //mActivity(youTubeIntent);
                }
            });

            // Check wether we are in debug mode
            if (BuildConfig.IS_DEBUG_MODE) {
                Log.d(LOG_TAG, "Current Trailer: " + mTrailerThumbnailImageView.getScaleType());
            }
        }
    }
}
