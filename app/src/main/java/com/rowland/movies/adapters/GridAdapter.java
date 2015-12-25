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
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.R;
import com.rowland.movies.rest.enums.EBaseImageSize;
import com.rowland.movies.rest.enums.EBaseURlTypes;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.utilities.Utilities;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CustomViewHolder> {

    // The class Log identifier
    private static final String LOG_TAG = GridAdapter.class.getSimpleName();
    // A list of the movie items
    private SortedList<Movie> mMovieList;
    // A Calendar object to help in formatting time
    private Calendar mCalendar;
    // Context instance
    private Context mContext;

    public GridAdapter(List<Movie> movieList, Context context) {
        this.mMovieList = new SortedList<Movie>(Movie.class, new MovieSortedListAdapterCallBack(this));
        this.mContext = context;
        this.mCalendar = Calendar.getInstance();
        addAllMovies(movieList);
    }

    // Called when RecyclerView needs a new CustomViewHolder of the given type to represent an item.
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout to inflate for CustomViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        // Return new new CustomViewHolder
        return new CustomViewHolder(v);
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // Acquire Movie item at this position
        final Movie movie = mMovieList.get(position);

        holder.mGridItemContainer.setContentDescription(holder.mGridItemContainer.getContext().getString(R.string.movie_title, movie.getOriginalTitle()));
        holder.mTitleTextView.setText(movie.getOriginalTitle());

        if (movie.getReleaseDate() != null) {
            mCalendar.setTime(movie.getReleaseDate());
            holder.mReleaseDateTextView.setText(String.valueOf(mCalendar.get(Calendar.YEAR)));
            holder.mReleaseDateTextView.setContentDescription(holder.mReleaseDateTextView.getContext().getString(R.string.movie_year, String.valueOf(mCalendar.get(Calendar.YEAR))));
        }
        // Set the popularity
        if (movie.getIsPopular()) {
            holder.mSortTypeIconImageView.setImageResource(R.drawable.ic_popular_black_48dp);
            holder.mSortTypeValueTextView.setText(String.valueOf(Math.round(movie.getPopularity())) +" Votes");
        }
        // Set highest rated
        if (movie.getIsHighestRated()) {
            holder.mSortTypeIconImageView.setImageResource(R.drawable.ic_highest_rated_black_48dp);
            holder.mSortTypeValueTextView.setText(String.valueOf(Math.round(movie.getVoteAverage())) +"/10");
        }
        // Set Favourites
        if (movie.getIsFavourite()) {
            holder.mSortTypeIconImageView.setImageResource(R.drawable.ic_favourite_black_48dp);
            holder.mSortTypeValueTextView.setText(String.valueOf(Math.round(movie.getPopularity())));
        }


        String imageUrl = EBaseURlTypes.MOVIE_API_IMAGE_BASE_URL.getUrlType() + EBaseImageSize.IMAGE_SIZE_W154.getImageSize() + movie.getPosterPath();
        final LinearLayout container = holder.mMovieTitleContainer;
        // Use Picasso to load the images
        Picasso.with(holder.mMovieImageView.getContext())
                .load(imageUrl)
                .networkPolicy(Utilities.NetworkUtility.isNetworkAvailable(mContext) ? NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.ic_movie_placeholder)
                .into(holder.mMovieImageView);
    }

    // What's the size of the movie List
    @Override
    public int getItemCount() {
        // Check size of List first
        if (mMovieList != null) {
            // Check wether we are in debug mode
            if (BuildConfig.IS_DEBUG_MODE) {
                Log.d(LOG_TAG, "List Count: " + mMovieList.size());
            }
            return mMovieList.size();
        }
        return 0;
    }

    // Takes care of the overhead of recycling and gives better performance and scrolling
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.grid_type_text_view)
        TextView mSortTypeValueTextView;

        @Bind(R.id.grid_release_date_text_view)
        TextView mReleaseDateTextView;

        @Bind(R.id.grid_title_text_view)
        TextView mTitleTextView;

        @Bind(R.id.poster_image_view)
        ImageView mMovieImageView;

        @Bind(R.id.grid_type_image_view)
        ImageView mSortTypeIconImageView;

        @Bind(R.id.grid_title_container)
        LinearLayout mMovieTitleContainer;

        @Bind(R.id.container)
        FrameLayout mGridItemContainer;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    // Handy method for passing the list to the adapter
    public void addAllMovies(List<Movie> movieList){
        // Add each movie to the sorted list
        mMovieList.beginBatchedUpdates();
        for (Movie movie : movieList) {
            mMovieList.add(movie);
        }
        mMovieList.beginBatchedUpdates();
        // Notify others of the data changes
    }
}
