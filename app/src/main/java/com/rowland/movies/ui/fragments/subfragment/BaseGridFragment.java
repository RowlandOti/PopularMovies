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
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.rowland.movies.R;
import com.rowland.movies.adapters.GridAdapter;
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
public class BaseGridFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // Logging tracker for this class
    private final String LOG_TAG = BaseGridFragment.class.getSimpleName();
    // An arrayList of the movies
    protected List<Movie> mMovieList;
    // The grid adapter
    protected GridAdapter mGridAdapter;
    // Sort Order for thid fragment
    protected ESortOrder mSortOrder;

    // ButterKnife injected Views
    @Bind(R.id.sw_refresh_layout)
    protected SwipeRefreshLayout mSwRefreshLayout;
    @Bind(R.id.grid_recycle_view)
    protected RecyclerView mGridRecycleView;

    // Default constructor
    public BaseGridFragment() {
        //Don't destroy fragment across orientation change
        setRetainInstance(true);
    }

    protected static BaseGridFragment newInstance(BaseGridFragment fragment, Bundle args) {
        // Create the new fragment instance
        BaseGridFragment fragmentInstance = fragment;
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
        // Configure the refreshlayout look
        mSwRefreshLayout.setColorSchemeResources(R.color.apptheme_accent_teal);
        mSwRefreshLayout.setProgressViewOffset(true, 100, 400);
        // Create new instance of layout manager
        final StaggeredGridLayoutManager mStaggeredLayoutManger = new StaggeredGridLayoutManager(getNumberOfColumns(), StaggeredGridLayoutManager.VERTICAL);
        // Set the layout manger
        mGridRecycleView.setLayoutManager(mStaggeredLayoutManger);
        mGridRecycleView.setHasFixedSize(false);
        // Call is actually only necessary with custom ItemAnimators
        mGridRecycleView.setItemAnimator(new DefaultItemAnimator());
        // Create new adapter
        mGridAdapter = new GridAdapter(mMovieList, getContext());
        // Associate RecycleView with adapter
        mGridRecycleView.setAdapter(mGridAdapter);
        // Set the refreshlayout's listener
        mSwRefreshLayout.setOnRefreshListener(this);
    }

    // When RefreshLayout is triggered reload the loader
    @Override
    public void onRefresh() {
        // Check if we are online
        if(Utilities.NetworkUtility.isNetworkAvailable(getContext())){
            // ToDo: Do not query online if its favourites -- override this method in FavouriteFragment
            Intent i = new Intent(getActivity(), MovieIntentService.class);
            i.putExtra(MovieIntentService.REQUEST_STRING, mSortOrder.getSortOrder());
            getActivity().startService(i);
        }
        else {
            mSwRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected int getNumberOfColumns() {
        // The number of grid columns
        int numberColumns = 2;
        // Check if we are in landscape
        if(ScreenUtility.isInLandscapeOrientation(getContext())){
            numberColumns = 3;
        }
        // Return the no. of columns
        return numberColumns;
    }
}
