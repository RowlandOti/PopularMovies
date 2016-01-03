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

package com.rowland.movies.ui.fragments.subfragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.movies.R;
import com.rowland.movies.data.loaders.MovieLoader;
import com.rowland.movies.rest.enums.ESortOrder;
import com.rowland.movies.rest.models.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Display Favourite Movie
 */
public class FavouriteFragment extends BaseMovieFragment implements LoaderManager.LoaderCallbacks<List<Movie>> {

    // Logging tracker for this class
    private final String LOG_TAG = FavouriteFragment.class.getSimpleName();

    // Default constructor
    public FavouriteFragment() {
        super();
    }

    public static FavouriteFragment newInstance(Bundle args) {
        // Create the new fragment instance
        FavouriteFragment fragmentInstance = (FavouriteFragment) newInstance(new FavouriteFragment(), args);
        // Return the new fragment
        return fragmentInstance;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if we have arguments
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        // Initialize the Views
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialize the list
        mMovieList = new ArrayList<>();
        // Initialize the sort order
        mSortOrder = ESortOrder.FAVOURITE_DESCENDING;
        // Call service if first launch of fragment
        if (isLaunch) {
            startMovieIntentService();
            isLaunch = false;
        }
        // Initialize the Loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        // Create new loader
        MovieLoader movieLoader = new MovieLoader(getActivity(), mSortOrder);
        // Return new loader
        return movieLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {
        // Set refreshing off, when done loading
        mSwRefreshLayout.setRefreshing(false);
        // Fill our movies list with data
        mMovieList = movieList;
        // Pass it on to our adapter
        mMovieAdapter.addAll(movieList);
        // Update the Empty View
        updateEmptyView();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Set refreshing off, when resetting
        mSwRefreshLayout.setRefreshing(false);
        // We reset the loader, nullify old data
        mMovieAdapter.addAll(null);
    }

    // When RefreshLayout is triggered reload the loader
    @Override
    public void onRefresh() {
        // Refresh the Loader
        getLoaderManager().restartLoader(0, null, this);
        // Set refreshing to false
        mSwRefreshLayout.setRefreshing(false);
    }
}