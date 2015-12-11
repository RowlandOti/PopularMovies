package com.rowland.movies.ui.fragments.subfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.movies.R;


public class HighestRatedFragment extends Fragment {

    private static HighestRatedFragment fragmentInstance = null;
    private final String LOG_TAG = HighestRatedFragment.class.getSimpleName();

    public static HighestRatedFragment newInstance(Bundle args) {
        fragmentInstance = new HighestRatedFragment();
        if (args != null) {
            fragmentInstance.setArguments(args);
        }
        return fragmentInstance;
    }

    public HighestRatedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_highestrated, container, false);
    }
}
