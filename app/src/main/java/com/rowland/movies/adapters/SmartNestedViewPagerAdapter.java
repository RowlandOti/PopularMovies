package com.rowland.movies.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rowland.ui.fragments.MainFragment;
import com.rowland.ui.fragments.subfragment.FavouriteFragment;
import com.rowland.ui.fragments.subfragment.HighestRatedFragment;
import com.rowland.ui.fragments.subfragment.PopularFragment;


/**
 * Created by Rowland on 6/11/2015.
 */
public class SmartNestedViewPagerAdapter extends SmartFragmentStatePagerAdapter  {

    private MainFragment ht = new MainFragment();
    private final Context mContext;

    public SmartNestedViewPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int index)
    {
        switch (index)
        {
            case 0:
            {
                // Home -Tweet fragment activity
                return new PopularFragment().newInstance(null);
            }
            case 1:
            {
                // Favourite fragment activity
                return new HighestRatedFragment().newInstance(null);
            }
            case 2:
            {
                // Graph fragment activity
                return new FavouriteFragment().newInstance(null);
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
