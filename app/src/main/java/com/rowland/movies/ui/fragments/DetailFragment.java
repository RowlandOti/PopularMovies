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

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rowland.movies.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    // Logging Identifier for class
    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    // The Movie ID Identifier Key
    public static final String MOVIE_ID_KEY = "_id";
    // The Movie ID Identifier Value
    private long mMovieIdKey;

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
    ProgressBar mTrailersProgressBar;

    @Bind(R.id.review_progress_bar)
    ProgressBar mReviewsProgressBar;

    @Bind(R.id.trailer_container)
    LinearLayout mTrailerLinearLayout;

    @Bind(R.id.reviews_container)
    LinearLayout mReviewLinearLayout;

    // Default constructor
    public DetailFragment() {
        // Don't destroy fragment across configuration change
        setRetainInstance(true);
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
        // Check if we have any arguments
        if (getArguments() != null) {
            // Acquire the selected movie identifier
            mMovieIdKey = getArguments().getLong(DetailFragment.MOVIE_ID_KEY, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        // Inflate all views
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }

}
