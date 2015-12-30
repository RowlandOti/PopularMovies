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

package com.rowland.movies.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.R;
import com.rowland.movies.data.loaders.ReviewLoader;
import com.rowland.movies.data.loaders.TrailerLoader;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.models.Review;
import com.rowland.movies.rest.models.Trailer;
import com.rowland.movies.rest.services.ReviewIntentService;
import com.rowland.movies.rest.services.TrailerIntentService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Display Movie Detail
 */
public class DetailFragment extends Fragment {

    // Logging Identifier for class
    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    // The Movie ID Identifier Key
    public static final String MOVIE_KEY = "movie_key";
    // The Movie model
    private Serializable mMovie;
    // Reviews LoaderCallBack
    private LoaderManager.LoaderCallbacks mReviewLoaderCallBack;
    // Trailers LoaderCallBack
    private LoaderManager.LoaderCallbacks mTrailerLoaderCallBack;
    // A List of the reviews
    protected List<Review> mReviewList;
    // A List of the trailers
    protected List<Trailer> mTrailerList;

    // ButterKnife injected views
    @Bind(R.id.movie_rate_image_view)
    ImageView mDetailRateImageView;

    @Bind(R.id.movie_rate_text_view)
    TextView mDetailRateTextView;

    @Bind(R.id.movie_title_text_view)
    TextView mDetailMovieTitle;

    @Bind(R.id.movie_release_year_text_view)
    TextView mDetailMovieYear;

    @Bind(R.id.movie_overview_text_view)
    TextView mDetailMovieSynopsis;

    @Bind(R.id.trailer_empty_text_view)
    TextView mDetailMovieEmptyTrailers;

    @Bind(R.id.review_empty_text_view)
    TextView mDetailMovieEmptyReviews;

    @Bind(R.id.favorite_fab)
    FloatingActionButton mFavoriteFab;

    @Bind(R.id.trailer_progress_bar)
    ProgressBar mTrailerProgressBar;

    @Bind(R.id.review_progress_bar)
    ProgressBar mReviewProgressBar;

    @Bind(R.id.trailer_container)
    LinearLayout mTrailerLinearLayout;

    @Bind(R.id.reviews_container)
    LinearLayout mReviewLinearLayout;

    // Default constructor
    public DetailFragment() {

    }

    // Create a new Instance for this fragment
    public static DetailFragment newInstance(Bundle args) {
        // The DetailFragment instance
        DetailFragment fragmentInstance = new DetailFragment();
        // Check for null arguments
        if (args != null) {
            // Set fragment arguments
            fragmentInstance.setArguments(args);
        }
        // Return the fragment
        return fragmentInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Let the fragment handle its menu items
        setHasOptionsMenu(true);
        // Don't destroy fragment across orientation change
        setRetainInstance(true);
        // Check if we have any arguments
        if (getArguments() != null) {
            // Acquire the selected movie identifier
            mMovie = getArguments().getSerializable(DetailFragment.MOVIE_KEY);
            startReviewIntentService();
            startTrailerIntentService();
        }
    }

    // Called to create the fragment's view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        // Inflate all views
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }

    // Called after the fragment's view has been created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Review LoaderCallBack implementation
        mReviewLoaderCallBack = new LoaderManager.LoaderCallbacks<List<Trailer>>() {
            @Override
            public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
                // Create new loader
                ReviewLoader movieLoader = new ReviewLoader(getActivity(), (Movie) mMovie);
                // Return new loader
                return movieLoader;
            }

            @Override
            public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {
                // Set ProgressBar refresh off
                mReviewProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoaderReset(Loader<List<Trailer>> loader) {
                // Set ProgressBar refresh off
                mReviewProgressBar.setVisibility(View.GONE);
            }
        };
        // Trailer LoaderCallBack implementation
        mTrailerLoaderCallBack = new LoaderManager.LoaderCallbacks<List<Review>>() {
            @Override
            public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
                // Create new loader
                TrailerLoader movieLoader = new TrailerLoader(getActivity(), (Movie) mMovie);
                // Return new loader
                return movieLoader;
            }

            @Override
            public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
                // Set ProgressBar refresh off
                mTrailerProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoaderReset(Loader<List<Review>> loader) {
                // Set ProgressBar refresh off
                mTrailerProgressBar.setVisibility(View.GONE);
            }
        };
    }

    // Called when the containing activity is created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialize the lists
        mReviewList = new ArrayList<>();
        mTrailerList = new ArrayList<>();
        // Initialize the Loader
        getLoaderManager().initLoader(0, null, mReviewLoaderCallBack);
        getLoaderManager().initLoader(1, null, mTrailerLoaderCallBack);
    }

    // Called to destroy this fragment
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    // Create menu item
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    // Start the review service
    private void startReviewIntentService() {
        // Set ProgressBar refresh on
        mReviewProgressBar.setVisibility(View.VISIBLE);
        // Create an Intent object
        Intent i = new Intent(getActivity(), ReviewIntentService.class);
        // Set any extras to pass over
        i.putExtra(ReviewIntentService.REQUEST_MOVIE_REMOTE_ID, ((Movie) mMovie).getId_());
        i.putExtra(ReviewIntentService.REQUEST_PAGE_NO_INT, 1);
        // Start the service
        getActivity().startService(i);
        // Check whether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "REVIEW SERVICE STARTED");
        }
    }

    // Start the trailer service
    private void startTrailerIntentService() {
        // Set ProgressBar refresh on
        mTrailerProgressBar.setVisibility(View.VISIBLE);
        // Create an Intent object
        Intent i = new Intent(getActivity(), TrailerIntentService.class);
        // Set any extras to pass over
        i.putExtra(TrailerIntentService.REQUEST_MOVIE_REMOTE_ID, ((Movie) mMovie).getId_());
        i.putExtra(TrailerIntentService.REQUEST_PAGE_NO_INT, 1);
        // Start the service
        getActivity().startService(i);
        // Check whether we are in debug mode
        if (BuildConfig.IS_DEBUG_MODE) {
            Log.d(LOG_TAG, "TRAILER SERVICE STARTED");
        }
    }
}
