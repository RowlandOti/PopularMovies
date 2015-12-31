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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.R;
import com.rowland.movies.data.loaders.ReviewLoader;
import com.rowland.movies.data.loaders.TrailerLoader;
import com.rowland.movies.rest.enums.EBaseImageSize;
import com.rowland.movies.rest.enums.EBaseURlTypes;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.models.Review;
import com.rowland.movies.rest.models.Trailer;
import com.rowland.movies.rest.services.ReviewIntentService;
import com.rowland.movies.rest.services.TrailerIntentService;
import com.rowland.movies.ui.activities.DetailActivity;
import com.rowland.movies.ui.adapters.ReviewAdapter;
import com.rowland.movies.ui.adapters.TrailerAdapter;
import com.rowland.movies.utilities.Utilities;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Display Movie Detail
 */
public class DetailFragment extends Fragment {

    // The Movie ID Identifier Key
    public static final String MOVIE_KEY = "movie_key";
    // Logging Identifier for class
    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    // ButterKnife injected views
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.movie_detail_poster_image_view)
    ImageView mPosterMovie;

    @Bind(R.id.movie_detail_backdrop_image_view)
    ImageView mBackdropMovie;

    @Bind(R.id.movie_rate_image_view)
    ImageView mDetailRateImageView;

    @Bind(R.id.movie_rate_text_view)
    TextView mDetailRateTextView;

    @Bind(R.id.movie_title_text_view)
    TextView mDetailMovieTitle;

    @Bind(R.id.movie_release_year_text_view)
    TextView mDetailMovieReleaseDate;

    @Bind(R.id.movie_overview_text_view)
    TextView mDetailMovieOverview;

    @Bind(R.id.favorite_fab)
    FloatingActionButton mFavoriteFab;

    @Bind(R.id.trailer_empty_text_view)
    TextView mDetailMovieEmptyTrailers;

    @Bind(R.id.review_empty_text_view)
    TextView mDetailMovieEmptyReviews;

    @Bind(R.id.trailer_progress_bar)
    ProgressBar mTrailerProgressBar;

    @Bind(R.id.review_progress_bar)
    ProgressBar mReviewProgressBar;

    @Bind(R.id.trailer_recycle_view)
    RecyclerView mTrailerRecycleView;

    @Bind(R.id.review_recycle_view)
    RecyclerView mReviewRecycleView;

    // The Movie model
    private Serializable mMovie;
    // Reviews LoaderCallBack
    private LoaderManager.LoaderCallbacks mReviewLoaderCallBack;
    // Trailers LoaderCallBack
    private LoaderManager.LoaderCallbacks mTrailerLoaderCallBack;
    // A List of the reviews
    private List<Review> mReviewList;
    // A List of the trailers
    private List<Trailer> mTrailerList;
    // The Review adapter
    private ReviewAdapter mReviewAdapter;
    // The Trailer adapter
    private TrailerAdapter mTrailerAdapter;

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

    // Called to do initial creation of fragment
    // Initialize and set up the fragment's non-view hierarchy
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
            // Start services
            startReviewIntentService();
            startTrailerIntentService();
        }
        // Initialize the review list
        mReviewList = new ArrayList<>();
        // Initialize the trailer list
        mTrailerList = new ArrayList<>();
    }

    // Called to instantiate the fragment's view hierarchy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        // Inflate all views
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }

    // Called after onCreateView() is done i.e the fragment's view has been created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Update FAB icon drawable
        updateFabDrawable();
        // Initialize layout manager
        final LinearLayoutManager mLinearLayoutManger = new LinearLayoutManager(getContext());
        // Set the RecycleView's layout manager
        mReviewRecycleView.setLayoutManager(mLinearLayoutManger);
        // Set the RecycleView's size fixing
        mReviewRecycleView.setHasFixedSize(false);
        // Set the RecycleView's ItemAnimators
        mReviewRecycleView.setItemAnimator(new DefaultItemAnimator());
        // Initialize new Review adapter
        mReviewAdapter = new ReviewAdapter(mReviewList);
        // Set RecycleView's adapter
        mReviewRecycleView.setAdapter(mReviewAdapter);
        // Review LoaderCallBack implementation
        mReviewLoaderCallBack = new LoaderManager.LoaderCallbacks<List<Review>>() {
            @Override
            public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
                // Set ProgressBar refresh on
                mReviewProgressBar.setVisibility(View.VISIBLE);
                // Create new loader
                ReviewLoader movieLoader = new ReviewLoader(getActivity(), (Movie) mMovie);
                // Return new loader
                return movieLoader;
            }

            @Override
            public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviewList) {
                // Set ProgressBar refresh off
                mReviewProgressBar.setVisibility(View.GONE);
                // Set mReviewList
                mReviewList = reviewList;
                // Pass reviews list to our adapter
                mReviewAdapter.addAll(mReviewList);
                // Update the Empty View
                updateReviewsEmptyView();
                // Check whether we are in debug mode
                if (BuildConfig.IS_DEBUG_MODE) {
                    Log.d(LOG_TAG, "Review: " + reviewList.size());
                    Log.d(LOG_TAG, "Review: " + mReviewList.size());
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Review>> loader) {
                // Set ProgressBar refresh off
                mReviewProgressBar.setVisibility(View.GONE);
                // We reset the loader, nullify old data
                mReviewAdapter.addAll(null);
            }
        };

        // Initialize layout manager
        final StaggeredGridLayoutManager mStaggeredLayoutManger = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        // Set the RecycleView's layout manager
        mTrailerRecycleView.setLayoutManager(mStaggeredLayoutManger);
        // Set the RecycleView's size fixing
        mTrailerRecycleView.setHasFixedSize(false);
        // Set the RecycleView's ItemAnimators
        mTrailerRecycleView.setItemAnimator(new DefaultItemAnimator());
        // Initialize new Trailer adapter
        mTrailerAdapter = new TrailerAdapter(mTrailerList);
        // Set RecycleView's adapter
        mTrailerRecycleView.setAdapter(mTrailerAdapter);
        // Trailer LoaderCallBack implementation
        mTrailerLoaderCallBack = new LoaderManager.LoaderCallbacks<List<Trailer>>() {
            @Override
            public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
                // Set ProgressBar refresh on
                mTrailerProgressBar.setVisibility(View.VISIBLE);
                // Create new loader
                TrailerLoader movieLoader = new TrailerLoader(getActivity(), (Movie) mMovie);
                // Return new loader
                return movieLoader;
            }

            @Override
            public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> trailerList) {
                // Set ProgressBar refresh off
                mTrailerProgressBar.setVisibility(View.GONE);
                // Set mTrailerList
                mTrailerList = trailerList;
                // Add trailers
                mTrailerAdapter.addAll(mTrailerList);
                // Update the Empty View
                updateTrailersEmptyView();
                // Check whether we are in debug mode
                if (BuildConfig.IS_DEBUG_MODE) {
                    Log.d(LOG_TAG, "Trailer: " + trailerList.size());
                    Log.d(LOG_TAG, "Trailer: " + mTrailerList.size());
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Trailer>> loader) {
                // Set ProgressBar refresh off
                mTrailerProgressBar.setVisibility(View.GONE);
                // We reset the loader, nullify old data
                mTrailerAdapter.addAll(null);
            }
        };
        // Bind data to views
        bindTo();
    }

    // Called when the containing activity onCreate() is done, and after onCreateView() of fragment
    // Do final modification on the hierarchy e.g modify view elements and restore previous state
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set the ToolBar
        ((DetailActivity) getActivity()).setToolbar(mToolbar, true, false, R.drawable.ic_logo_48px);
        // Initialize the Loader
        getLoaderManager().initLoader(0, null, mReviewLoaderCallBack);
        getLoaderManager().initLoader(1, null, mTrailerLoaderCallBack);
        // Create an Animation
        Animation simpleGrowAnimation = AnimationUtils.loadAnimation(mFavoriteFab.getContext(), R.anim.grow_bigger);
        // Animate the Floating action button
        mFavoriteFab.startAnimation(simpleGrowAnimation);
    }

    // Called to destroy this fragment
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    // Called to create menu item
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    // Bind data to the views
    private void bindTo() {
        // Build the image url
        String imageUrl = EBaseURlTypes.MOVIE_API_IMAGE_BASE_URL.getUrlType() + EBaseImageSize.IMAGE_SIZE_W500.getImageSize() + ((Movie) mMovie).getBackdropPath();
        // Use Picasso to load the images
        Picasso.with(mBackdropMovie.getContext())
                .load(imageUrl)
                .networkPolicy(Utilities.NetworkUtility.isNetworkAvailable(mBackdropMovie.getContext()) ? NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.ic_movie_placeholder)
                .into(mBackdropMovie);
        // Set the title
        mDetailMovieTitle.setText(((Movie) mMovie).getOriginalTitle());
        // Set the rating
        mDetailRateTextView.setText(String.format("%d/10", Math.round(((Movie) mMovie).getVoteAverage())));
        // Set the overview
        mDetailMovieOverview.setText(((Movie) mMovie).getOverview());
        // Set the release date
        if (((Movie) mMovie).getReleaseDate() != null) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTime(((Movie) mMovie).getReleaseDate());
            mDetailMovieReleaseDate.setText(String.valueOf(mCalendar.get(Calendar.YEAR)));
        }
    }

    // Start the review service
    private void startReviewIntentService() {
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

    // Update the Review's empty view
    private void updateReviewsEmptyView() {
        // Update Reviews
        if (mReviewAdapter.getItemCount() == 0) {
            // Show Empty TextView
            mReviewRecycleView.setVisibility(View.GONE);
            mDetailMovieEmptyTrailers.setVisibility(View.VISIBLE);
        } else {
            // Show RecycleView filled with movies
            mReviewRecycleView.setVisibility(View.VISIBLE);
            mDetailMovieEmptyReviews.setVisibility(View.GONE);
        }
    }

    // Update the Trailer's empty view
    private void updateTrailersEmptyView() {
        // Update Trailers
        if (mTrailerAdapter.getItemCount() == 0) {
            // Show Empty TextView
            mTrailerRecycleView.setVisibility(View.GONE);
            mDetailMovieEmptyTrailers.setVisibility(View.VISIBLE);
        } else {
            // Show RecycleView filled with movies
            mTrailerRecycleView.setVisibility(View.VISIBLE);
            mDetailMovieEmptyTrailers.setVisibility(View.GONE);
        }
    }

    // Update the Fab icon drawable
    private void updateFabDrawable() {
        // Is movie Favourite
        boolean isFavourite = ((Movie)mMovie).getIsFavourite();
        // Toggle drawable
        mFavoriteFab.setImageResource(isFavourite ? R.drawable.ic_heart_full_red_48dp : R.drawable.ic_heart_full_white_48dp);
    }

    // Attack click listener to FAB
    @OnClick(R.id.favorite_fab)
    public void onFavoriteMovie() {
        // Set movie as a favourite
        ((Movie)mMovie).setIsFavourite(true);
        // Update the drawable
        updateFabDrawable();
        // Create an Animation
        Animation simpleRotateAnimation = AnimationUtils.loadAnimation(mFavoriteFab.getContext(), R.anim.rotate_backward);
        // Animate the Floating action button
        mFavoriteFab.startAnimation(simpleRotateAnimation);
    }

}
