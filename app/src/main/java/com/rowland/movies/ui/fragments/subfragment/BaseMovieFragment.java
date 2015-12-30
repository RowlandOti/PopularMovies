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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.rowland.movies.R;
import com.rowland.movies.ui.adapters.MovieAdapter;
import com.rowland.movies.rest.enums.ESortOrder;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.rest.services.MovieIntentService;
import com.rowland.movies.utilities.ScreenUtility;
import com.rowland.movies.utilities.Utilities;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class BaseMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // Logging tracker for this class
    private final String LOG_TAG = BaseMovieFragment.class.getSimpleName();
    // An arrayList of the movies
    protected List<Movie> mMovieList;
    // The grid adapter
    protected MovieAdapter mMovieAdapter;
    // Sort Order for thid fragment
    protected ESortOrder mSortOrder;
    // Is it first launch of fragment?
    protected boolean isLaunch = true;
    // Page no. of request
    protected int mRequestPageNo = 1;

    // ButterKnife injected Views
    @Bind(R.id.sw_refresh_layout)
    protected SwipeRefreshLayout mSwRefreshLayout;
    @Bind(R.id.movie_recycle_view)
    protected RecyclerView mMovieRecycleView;
    @Bind(R.id.empty_text_view)
    protected TextView mEmptyTextView;

    // Default constructor
    public BaseMovieFragment() {

    }

    protected static BaseMovieFragment newInstance(BaseMovieFragment fragment, Bundle args) {
        // Create the new fragment instance
        BaseMovieFragment fragmentInstance = fragment;
        // Set arguments if it is not null
        if (args != null) {
            fragmentInstance.setArguments(args);
        }
        // Return the new fragment
        return fragmentInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Don't destroy fragment across orientation change
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Configure the refresh layout look
        mSwRefreshLayout.setColorSchemeResources(R.color.apptheme_accent_teal);
        mSwRefreshLayout.setProgressViewOffset(true, 100, 400);
        // Create new instance of layout manager
        final StaggeredGridLayoutManager mStaggeredLayoutManger = new StaggeredGridLayoutManager(getNumberOfColumns(), StaggeredGridLayoutManager.VERTICAL);
        // Set the layout manger
        mMovieRecycleView.setLayoutManager(mStaggeredLayoutManger);
        mMovieRecycleView.setHasFixedSize(false);
        // Call is actually only necessary with custom ItemAnimators
        mMovieRecycleView.setItemAnimator(new DefaultItemAnimator());
        // Create new adapter
        mMovieAdapter = new MovieAdapter(mMovieList, getContext(), getActivity());
        // Associate RecycleView with adapter
        mMovieRecycleView.setAdapter(mMovieAdapter);
        // Set the refreshlayout's listener
        mSwRefreshLayout.setOnRefreshListener(this);
    }

    // When RefreshLayout is triggered reload the loader
    @Override
    public void onRefresh() {
        // Check if we are online
        if (Utilities.NetworkUtility.isNetworkAvailable(getContext())) {
            startMovieIntentService();
        } else {
            // Set refreshing
            mSwRefreshLayout.setRefreshing(false);
            // Tell user of no connectivity
            Snackbar.make(getView(), R.string.status_no_internet, Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            isLaunch = savedInstanceState.getBoolean("IS_LAUNCH", isLaunch);
        }
    }

    // Here you Save your data
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IS_LAUNCH", isLaunch);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    // Start the service
    protected void startMovieIntentService() {
        // ToDo: Do not query online if its favourites -- override this method in FavouriteFragment
        Intent i = new Intent(getActivity(), MovieIntentService.class);
        i.putExtra(MovieIntentService.REQUEST_SORT_TYPE_STRING, mSortOrder.getSortOrder());
        i.putExtra(MovieIntentService.REQUEST_PAGE_NO_INT, mRequestPageNo);
        getActivity().startService(i);
        // Increment requestPage no.
        mRequestPageNo++;
    }

    // Get the no. of grid columns to use
    protected int getNumberOfColumns() {
        // The number of grid columns
        int numberColumns = 2;
        // Check if we are in landscape
        if (ScreenUtility.isInLandscapeOrientation(getContext())) {
            numberColumns = 3;
        }
        // Return the no. of columns
        return numberColumns;
    }

    // Update the empty view
    public void updateEmptyView() {
        if (mMovieAdapter.getItemCount() == 0) {
            // Show Empty TextView
            mMovieRecycleView.setVisibility(View.GONE);
            mEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            // Show RecycleView filled with movies
            mMovieRecycleView.setVisibility(View.VISIBLE);
            mEmptyTextView.setVisibility(View.GONE);
        }
    }
}
