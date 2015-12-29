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
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.rowland.movies.R;
import com.rowland.movies.ui.fragments.DetailFragment;
import com.rowland.movies.ui.fragments.MainFragment;

import butterknife.ButterKnife;


public class MainActivity extends BaseToolBarActivity implements MainFragment.IMovieSelectionCallBack {

    // Logging Identifier for class
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    // The MainFragment
    private MainFragment mainFragment;
    // The DetailFragment
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout
        setContentView(R.layout.activity_main);
        // Inject all the views
        ButterKnife.bind(this);
        // Setup the inc_toolbar
        setToolbar(false, false, R.drawable.ic_logo_48px);
        // Check that the activity is using the layout with the fragment_container id
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

        showMainFragment(null);
    }

    // Insert the DetailFragment
    private void showDetailFragment(Bundle args) {
        // Acquire the Fragment manger
        FragmentManager fm = getSupportFragmentManager();
        // Begin the transaction
        FragmentTransaction ft = fm.beginTransaction();
        // Check if we already have a fragment
        if (detailFragment == null) {
            // Create new fragment
            detailFragment = DetailFragment.newInstance(args);
            // Prefer replace() over add() see <a>https://github.com/RowlandOti/PopularMovies/issues/1</a>
            ft.replace(R.id.detail_container, detailFragment);
            ft.commit();
        }
    }

    // Insert the MainFragment
    private void showMainFragment(Bundle args) {
        // Acquire the Fragment manger
        FragmentManager fm = getSupportFragmentManager();
        // Begin the transaction
        FragmentTransaction ft = fm.beginTransaction();
        // Check if we already have a fragment
        if (mainFragment == null) {
            // Create new fragment
            mainFragment = MainFragment.newInstance(args);
            // Prefer replace() over add() see <a>https://github.com/RowlandOti/PopularMovies/issues/1</a>
            ft.replace(R.id.fragment_container, mainFragment);
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Acquire the search manager
        SearchManager SManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // Acquire the relevant search menu item
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        // Acquire the search view
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        // Set the Search activity class
        mSearchView.setSearchableInfo(SManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchActivity.class)));
        // Set if should use an icon
        mSearchView.setIconifiedByDefault(true);
        // return whether menu was succesfully created
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMovieSelected(int idKey) {
        // Check for two-pane
        if (mIsTwoPane)
        {
            // Show the DetailFragment
            Bundle args = new Bundle();
            //args.putString(DetailsFragment.ID_KEY, date);
            args.putInt(DetailFragment.MOVIE_ID_KEY, idKey);
            // Show the DetailFragment
            showDetailFragment(args);
        }
        else
        {
            // Create an Intent object
            Intent intent = new Intent(this, DetailActivity.class);
            // Set extras - pass MOVIE_ID_KEY
            intent.putExtra(DetailFragment.MOVIE_ID_KEY, idKey);
            // Start the DetailActivity
            startActivity(intent);
        }

    }
}
