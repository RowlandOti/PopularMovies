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

package com.rowland.movies.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.SlidingTabStripLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.rowland.movies.R;
import com.rowland.movies.adapters.ListPopupWindowAdapter;
import com.rowland.movies.adapters.SmartNestedViewPagerAdapter;
import com.rowland.movies.objects.ListPopupMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // The class Log identifier
    private final String LOG_TAG = MainFragment.class.getSimpleName();
    // Selected tab key
    public static final String SELECTED_TAB_KEY = "SELECTED_TAB";
    // The Subfragment titles
    private String[] TITLES = {"Popular", "Highest Rated", "Favourite"};
    // The adapter that manages the subfragments
    private SmartNestedViewPagerAdapter pagerAdapter;
    // ListPopup max width
    private float mPopupMaxWidth;
    // The currently selected tab strip
    private int selectedTabStrip = 0;

    // ButterKnife injected views
    @Bind(R.id.slidingTabStrips)
    SlidingTabStripLayout mSlidingTabStrips;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    // Default constructor
    public MainFragment() {
        // Don't destroy fragment across configuration change
        setRetainInstance(true);
    }

    // Create a new Instance for this fragment
    public static MainFragment newInstance(Bundle args) {
        // Create the new fragment instance
        MainFragment fragmentInstance = new MainFragment();
        // Set arguments if it is not null
        if (args != null) {
            fragmentInstance.setArguments(args);
        }
        // Return the new fragment
        return fragmentInstance;
    }
    
    // A callback interface that all containing activities implement
    public interface onMovieSelectionCallback {
        // Call this when movie is selected.
        void onMovieSelected(int id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Get the maximum width of our ListPopupWindow
        this.mPopupMaxWidth = Math.max(this.getResources().getDisplayMetrics().widthPixels / 2,
                this.getResources().getDimensionPixelSize(R.dimen.config_prefListPopupWindowWidth));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Initialize the ViewPager and TabStripLayout
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }

    // Here you Save your data
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Acquire the currently selected tab
        selectedTabStrip = mViewPager.getCurrentItem();
        // Save the currently selected tab position
        outState.putInt(SELECTED_TAB_KEY, selectedTabStrip);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore states
        if (savedInstanceState != null) {
            // Acquire previously selected tab.
            selectedTabStrip = savedInstanceState.getInt(SELECTED_TAB_KEY, selectedTabStrip);
            // Restore previously selected tab
            mViewPager.setCurrentItem(selectedTabStrip, true);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the fragments adapter
        pagerAdapter = new SmartNestedViewPagerAdapter(getActivity().getSupportFragmentManager());
        // Set up the adapter
        mViewPager.setAdapter(pagerAdapter);
        // Set up the viewPager
        mSlidingTabStrips.setupWithViewPager(mViewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate new menu.
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_overflow:
                // Works as long as list item is always visible and does not go into the menu overflow
                final View menuItemView = getActivity().findViewById(R.id.action_overflow);
                onListPopUp(menuItemView);
                return true;
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public void onListPopUp(View anchor) {
        // This a sample dat to fill our ListView
        List<ListPopupMenu> menuItem = new ArrayList<>();
        menuItem.add(new ListPopupMenu(R.drawable.ic_popular_black_48dp, "Popular"));
        menuItem.add(new ListPopupMenu(R.drawable.ic_highest_rated_black_48dp, "Highest Rated"));
        menuItem.add(new ListPopupMenu(R.drawable.ic_favourite_black_48dp, "Favourite"));
        // Initialise our adapter
        ListPopupWindowAdapter mListPopUpAdapter = new ListPopupWindowAdapter(getActivity().getApplicationContext(), menuItem);
        // Initialise our ListPopupWindow instance
        final ListPopupWindow pop = new ListPopupWindow(getActivity().getApplicationContext());
        // Configure ListPopupWindow properties
        pop.setAdapter(mListPopUpAdapter);
        // Set the view below/above which ListPopupWindow dropdowns
        pop.setAnchorView(anchor);
        // Setting this enables window to be dismissed by click outside ListPopupWindow
        pop.setModal(true);
        // Sets the width of the ListPopupWindow
        pop.setContentWidth((int) this.mPopupMaxWidth);
        // Sets the Height of the ListPopupWindow
        pop.setHeight(ListPopupWindow.WRAP_CONTENT);
        // Set up a click listener for the ListView items
        pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // The overflow menu selected
                String menuName = ((ListPopupMenu) adapterView.getItemAtPosition(position)).getName();
                // Switch to the right ViewPager element at given position
                switch (menuName) {
                    case "Popular":
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case "Highest Rated":
                        mViewPager.setCurrentItem(1, true);
                        break;
                    case "Favourite":
                        mViewPager.setCurrentItem(2, true);
                        break;
                    default:
                        mViewPager.setCurrentItem(0, true);
                        break;
                }
                // Dismiss the LisPopupWindow when a list item is clicked
                pop.dismiss();
            }
        });
        pop.show();
    }

    public String[] getTITLES() {
        return TITLES;
    }
}
