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

package com.rowland.movies.ui.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rowland.movies.R;
import com.rowland.movies.ui.fragments.DetailsFragment;
import com.rowland.movies.ui.fragments.SearchFragment;

import butterknife.ButterKnife;

public class SearchActivity extends BaseToolBarActivity {

    // Class Variables
    private final String LOG_TAG = SearchActivity.class.getSimpleName();
    // The SearchFragment
    private SearchFragment searchFragment;
    // The DetailFragment
    private DetailsFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Inject all the views
        ButterKnife.bind(this);
        // Setup the toolbar
        setToolbar(true, false, R.drawable.ic_logo_48px);

        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            toggleShowTwoPane(true);
            // If we're being restored from a previous state, don't need to do anything
            // and should return or else we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            } else {
                // Get Facebook Stetho doing its job
                initStetho();
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a fragment transaction.
                showDetailFragment(null);
            }
        } else {
            toggleShowTwoPane(false);
        }
        // Use this Bundle object
        Bundle args = new Bundle();
        // to set arguments for this fragment
        args.putString(SearchManager.QUERY, getSearchQuery());
        // Show the SearchFragment
        showSearchFragment(args);
    }

    // Insert the DetailFragment
    private void showDetailFragment(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        if (detailFragment == null) {
            detailFragment = DetailsFragment.newInstance(args);

            ft.replace(R.id.detail_container, detailFragment);
            ft.commit();
        }
    }

    // Insert the MainFragment
    private void showSearchFragment(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance(args);
            // Prefer replace() over add() see <a>https://github.com/RowlandOti/PopularMovies/issues/1</a>
            ft.replace(R.id.fragment_container, searchFragment);
            ft.commit();
        }
    }
    // Get Search query from Intent
    private String getSearchQuery() {
        // The search query
        String searchQuery = "";
        // Filter the relevant Intent
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction()))
        {
            // Acquire the search query
            searchQuery = getIntent().getStringExtra(SearchManager.QUERY);
        }
        // Return the search string
        return  searchQuery;
    }

}
