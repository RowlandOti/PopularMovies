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

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.rowland.movies.R;
import com.rowland.movies.rest.models.Movie;
import com.rowland.movies.ui.fragments.DetailFragment;

import java.io.Serializable;

import butterknife.ButterKnife;

public class DetailActivity extends BaseToolBarActivity {

    // Logging Identifier for class
    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    // The DetailFragment
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout
        setContentView(R.layout.activity_detail);
        // Inject all the views
        ButterKnife.bind(this);
        // Check that the activity is using the layout with the fragment_container id
        if (findViewById(R.id.detail_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            else {
                Serializable movie = getIntent().getSerializableExtra(DetailFragment.MOVIE_KEY);
                // Create a Bundle object
                Bundle args = new Bundle();
                // Set arguments on Bundle
                args.putSerializable(DetailFragment.MOVIE_KEY, movie);
                // Pass bundle to the fragment
                showDetailFragment(args);
            }
        }

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
}
