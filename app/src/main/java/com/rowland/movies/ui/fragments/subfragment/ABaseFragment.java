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
import com.rowland.movies.rest.pojos.Movies;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class ABaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // Logging tracker for this class
    private final String LOG_TAG = ABaseFragment.class.getSimpleName();
    // AN arrayList of the movies
    private ArrayList<Movies> mMovieLists;
    // The grid adapter
    private GridAdapter mMovieAdapter;

    // ButterKnife injected Views
    @Bind(R.id.sw_refresh_layout)
    SwipeRefreshLayout swRefreshLayout;
    @Bind(R.id.grid_recycle_view)
    RecyclerView mGridRecycleView;

    public static ABaseFragment newInstance(ABaseFragment fragment, Bundle args) {

        // Create the new fragment instance
        ABaseFragment  fragmentInstance = fragment;
        // Set arguments if it is not null
        if (args != null) {
            fragmentInstance.setArguments(args);
        }
        // Return the new fragment
        return fragmentInstance;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        swRefreshLayout.setColorSchemeResources(R.color.apptheme_accent_teal);
        swRefreshLayout.setProgressViewOffset(true, 100, 400);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mGridRecycleView.setLayoutManager(mLayoutManager);
        mGridRecycleView.setHasFixedSize(true);

        mMovieAdapter = new GridAdapter(mMovieLists);

        mGridRecycleView.setAdapter(mMovieAdapter);
        swRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
    }
}
