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

import com.rowland.adapters.ListPopupWindowAdapter;
import com.rowland.adapters.SmartNestedViewPagerAdapter;
import com.rowland.movies.R;
import com.rowland.objects.ListPopupMenu;
import com.rowland.utilities.Utilities;

import java.util.ArrayList;

/**
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private final String LOG_TAG = MainFragment.class.getSimpleName();
    private String[] TITLES = { "Popular", "Highest Rated", "Favorite" };
    private static MainFragment fragmentInstance = null;
    private SmartNestedViewPagerAdapter pagerAdapter;
    private SlidingTabStripLayout slidingTabStrips;
    private ViewPager pager;
    private float mPopupMaxWidth;

    public MainFragment()
    {
        setRetainInstance(true);
    }


    public static MainFragment newInstance(Bundle args)
    {
        fragmentInstance = new MainFragment();
        if(args != null)
        {
            fragmentInstance.setArguments(args);
        }
        return fragmentInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Get the maximum width of our ListPopupWindow
        this.mPopupMaxWidth = Math.max(this.getResources().getDisplayMetrics().widthPixels/2,
                this.getResources().getDimensionPixelSize(R.dimen.config_prefListPopupWindowWidth));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize the ViewPager and TabStripLayout
        this.pager = (ViewPager) rootView.findViewById(R.id.viewPager);
        this.slidingTabStrips = (SlidingTabStripLayout) rootView.findViewById(R.id.slidingTabStrips);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        // Clear old menu.
        menu.clear();
        // Inflate new menu.
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_search:
                return true;
            case R.id.action_overflow:
                // Works as long as list item is always visible and does not go into the menu overflow
                final View menuItemView = getActivity().findViewById(R.id.action_overflow);
                onListPopUp(menuItemView);
                return true;
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public void onListPopUp(View anchor)
    {
        // This a sample dat to fill our ListView
        ArrayList<ListPopupMenu> menuItem = new ArrayList<ListPopupMenu>();
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
        pop.setContentWidth((int)this.mPopupMaxWidth);
        // Sets the Height of the ListPopupWindow
        pop.setHeight(ListPopupWindow.WRAP_CONTENT);
        // Set up a click listener for the ListView items
        pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Dismiss the LisPopupWindow when a list item is clicked

                String sortType;
                String menuName = ((ListPopupMenu) adapterView.getItemAtPosition(position)).getName();

                switch (menuName)
                {
                    case "Popular":
                        sortType = getResources().getString(R.string.sort_popular);
                        slidingTabStrips.setScrollPosition(0, 0.0F, true);
                        break;
                    case "Highest Rated":
                        sortType = getResources().getString(R.string.sort_highest_rated);
                        slidingTabStrips.setScrollPosition(1, 0.0F, true);
                        break;
                    case "Favourite":
                        sortType = getResources().getString(R.string.sort_favorited);
                        slidingTabStrips.setScrollPosition(2, 0.0F, true);
                        break;
                    default:
                        sortType = getResources().getString(R.string.sort_highest_rated);
                        slidingTabStrips.setScrollPosition(0, 0.0F, true);
                        break;
                }

                Utilities.PreferenceUtility.savePrefs(getActivity(), getResources().getString(R.string.sort_type), sortType);

                pop.dismiss();
            }
        });
        pop.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // Set an Adapter: pass data, etc.
        this.pagerAdapter = new SmartNestedViewPagerAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext());
        this.pager.setAdapter(pagerAdapter);
        //this.slidingTabStrips.setCustomTabView(R.layout.tab_custom, R.id.psts_tab_textview_title);
        // Bind the slidingTabStrips to the ViewPager
        this.slidingTabStrips.setupWithViewPager(pager);
    }

    public String[] getTITLES()
    {
        return TITLES;
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface onMainFragmentMovieSelectedCallback
    {
        /**
         *MovieItemFragmentCallback for when an item has been selected.
         */
        void onMainFragmentMovieSelected(String arg);
    }


}
