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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rowland.movies.R;
import com.rowland.movies.adapters.GridAdapter;
import com.rowland.movies.rest.models.Movies;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class BaseGridFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // Logging tracker for this class
    private final String LOG_TAG = BaseGridFragment.class.getSimpleName();
    // AN arrayList of the movies
    protected ArrayList<Movies> mMovieLists;
    // The grid adapter
    protected GridAdapter mMovieAdapter;

    // ButterKnife injected Views
    @Bind(R.id.sw_refresh_layout)
    protected SwipeRefreshLayout mSwRefreshLayout;
    @Bind(R.id.grid_recycle_view)
    protected RecyclerView mGridRecycleView;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwRefreshLayout.setColorSchemeResources(R.color.apptheme_accent_teal);
        mSwRefreshLayout.setProgressViewOffset(true, 100, 400);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mGridRecycleView.setLayoutManager(mLayoutManager);
        mGridRecycleView.setHasFixedSize(true);

        mMovieAdapter = new GridAdapter(mMovieLists);

        mGridRecycleView.setAdapter(mMovieAdapter);
        mSwRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
    }
}
