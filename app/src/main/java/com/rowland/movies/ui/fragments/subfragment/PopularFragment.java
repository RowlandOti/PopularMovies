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
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.movies.R;
import com.rowland.movies.rest.pojos.Movies;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class PopularFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<List<Movies>>{

    // ButterKnife injected Views
    @Bind(R.id.sw_refresh_layout) SwipeRefreshLayout swRefreshLayout;
    @Bind(R.id.grid_recycle_view) RecyclerView mRecycleView;
    // Logging tracker for this class
    private final String LOG_TAG = PopularFragment.class.getSimpleName();

    public static PopularFragment newInstance(Bundle args)
    {
        // Create the new fragment instance
        PopularFragment fragmentInstance = new PopularFragment();
        // Set arguments if it is not null
        if(args != null)
        {
            fragmentInstance.setArguments(args);
        }
        // Return the new fragment
        return fragmentInstance;

    }
    public PopularFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        // Initialize the ViewPager and TabStripLayout
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        swRefreshLayout.setColorSchemeResources(R.color.apptheme_accent_teal);
        swRefreshLayout.setProgressViewOffset(true, 100, 400);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setHasFixedSize(true);
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {

    }

    @Override
    public void onRefresh() {

    }
}
