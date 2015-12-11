package com.rowland.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rowland.ui.fragments.DetailsFragment;
import com.rowland.ui.fragments.MainFragment;


public class MainActivity extends AppCompatActivity {

    // Class Variables
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private boolean mTwoPane;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the mToolbar and set it as an ActionBar
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.ic_logo_48px);
        this.mToolbar.setLogo(R.drawable.ic_logo_48px);

        if (findViewById(R.id.detail_container) != null)
        {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // If we're being restored from a previous state, don't need to do anything
            // and should return or else we could end up with overlapping fragments.
            if (savedInstanceState != null)
            {
                return;
            }
            else
            {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a fragment transaction.
                showDetailFragment(null);
            }
        }
        else
        {
            mTwoPane = false;
        }

        showMainFragment(null);

    }

    // Insert the DetailFragment
    private void showDetailFragment(Bundle args)
    {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        DetailsFragment fragment = DetailsFragment.newInstance(args);

        ft.replace(R.id.detail_container, fragment);
        ft.commit();
    }
    // Inser the MainFragment
    private void showMainFragment(Bundle args)
    {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        MainFragment fragment  = MainFragment.newInstance(args);

        ft.add(R.id.fragment_container, fragment);
        ft.commit();
    }
}
