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

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.R;
import com.rowland.movies.data.callbacks.MovieSortedListAdapterCallBack;
import com.rowland.movies.rest.enums.EBaseImageSize;
import com.rowland.movies.rest.enums.EBaseURlTypes;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.ui.activities.MainActivity;
import com.rowland.movies.utilities.Utilities;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CustomViewHolder> {

    // The class Log identifier
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    // A list of the movie items
    private SortedList<Movie> mMovieList;
    // A Calendar object to help in formatting time
    private Calendar mCalendar;
    // Context instance
    private Context mContext;
    // The container Activity
    private MainActivity mActivity;

    public MovieAdapter(List<Movie> movieList, Context context, FragmentActivity activity) {
        // Acquire the context
        this.mContext = context;
        // Acquire a Calendar object
        this.mCalendar = Calendar.getInstance();
        // Acquire the containing activity
        this.mActivity = (MainActivity) activity;
        // Initially add local movies to list
        addAll(movieList);
    }

    // Called when RecyclerView needs a new CustomViewHolder of the given type to represent an item.
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout to inflate for CustomViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        // Return new new CustomViewHolder
        return new CustomViewHolder(v);
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // Acquire Movie item at this position
        final Movie movie = mMovieList.get(position);
        // Bind the data to the view holder
        holder.bindTo(movie);
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

    // Handy method for passing the list to the adapter
    public void addAll(List<Movie> movieList) {

        if (movieList != null) {
            // Check for null
            if (mMovieList == null) {
                // Create a new instance
                mMovieList = new SortedList<>(Movie.class, new MovieSortedListAdapterCallBack(this));
            }
            // Begin
            mMovieList.beginBatchedUpdates();
            // Add each movie to the sorted list
            for (Movie movie : movieList) {
                // Add movies
                mMovieList.add(movie);
            }
            // End
            mMovieList.endBatchedUpdates();
        }
        // Notify others of the data changes
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

        @Bind(R.id.container_item)
        FrameLayout mGridItemContainer;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        // Bind the data to the holder views
        private void bindTo(final Movie movie) {
            // Set click listener on card view
            mGridItemContainer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Execute Callback
                    mActivity.onMovieSelected(movie);
                }
            });
            // Set movie title
            mTitleTextView.setText(movie.getOriginalTitle());
            // Set the release date
            if (movie.getReleaseDate() != null) {
                mCalendar.setTime(movie.getReleaseDate());
                mReleaseDateTextView.setText(String.valueOf(mCalendar.get(Calendar.YEAR)));
                mReleaseDateTextView.setContentDescription(mReleaseDateTextView.getContext().getString(R.string.movie_year, String.valueOf(mCalendar.get(Calendar.YEAR))));
            }
            // Set the popularity
            if (movie.getIsPopular()) {
                mSortTypeIconImageView.setImageResource(R.drawable.ic_popular_black_48dp);
                mSortTypeValueTextView.setText(String.format("%d Votes", Math.round(movie.getPopularity())));
            }
            // Set highest rated
            if (movie.getIsHighestRated()) {
                mSortTypeIconImageView.setImageResource(R.drawable.ic_highest_rated_black_48dp);
                mSortTypeValueTextView.setText(String.format("%d/10", Math.round(movie.getVoteAverage())));
            }
            // Set Favourites
            if (movie.getIsFavourite()) {
                mSortTypeIconImageView.setImageResource(R.drawable.ic_favourite_black_48dp);
            }
            // Build the image url
            String imageUrl = EBaseURlTypes.MOVIE_API_IMAGE_BASE_URL.getUrlType() + EBaseImageSize.IMAGE_SIZE_W154.getImageSize() + movie.getPosterPath();
            // Use Picasso to load the images
            Picasso.with(mMovieImageView.getContext())
                    .load(imageUrl)
                    .networkPolicy(Utilities.NetworkUtility.isNetworkAvailable(mContext) ? NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .into(mMovieImageView);
        }
    }
}
