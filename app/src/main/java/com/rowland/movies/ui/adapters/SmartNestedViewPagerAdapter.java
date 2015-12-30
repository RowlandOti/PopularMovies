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

package com.rowland.movies.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rowland.movies.ui.fragments.MainFragment;
import com.rowland.movies.ui.fragments.subfragment.FavouriteFragment;
import com.rowland.movies.ui.fragments.subfragment.HighestRatedFragment;
import com.rowland.movies.ui.fragments.subfragment.PopularFragment;


/**
 * Created by Rowland on 6/11/2015.
 */
public class SmartNestedViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private MainFragment ht = new MainFragment();

    public SmartNestedViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0: {
                // Popular fragment
                PopularFragment popularFragment = new PopularFragment().newInstance(null);
                return popularFragment;
            }
            case 1: {
                // Highest Rated fragment
                HighestRatedFragment highestRatedFragment = new HighestRatedFragment().newInstance(null);
                return highestRatedFragment;
            }
            case 2: {
                // Favourite fragment
                FavouriteFragment favouriteFragment = new FavouriteFragment().newInstance(null);
                return favouriteFragment;
            }

        }

        return null;
    }


    @Override
    public int getCount() {

        // get item count - equal to number of tabs

        return ht.getTITLES().length;

    }


    @Override
    public CharSequence getPageTitle(int position) {

        return ht.getTITLES()[position];
    }
}
