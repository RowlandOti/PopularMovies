package com.rowland.ui.fragments.subfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.movies.R;

/**
 *
 */
public class FavouriteFragment extends Fragment {


    private static FavouriteFragment fragmentInstance = null;
    private final String LOG_TAG = FavouriteFragment.class.getSimpleName();

    public static FavouriteFragment newInstance(Bundle args)
    {
        fragmentInstance = new FavouriteFragment();
        if(args != null)
        {
            fragmentInstance.setArguments(args);
        }
        return fragmentInstance;

    }
    public FavouriteFragment()
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
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }
}
