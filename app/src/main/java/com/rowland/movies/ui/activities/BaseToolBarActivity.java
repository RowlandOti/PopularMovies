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
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.facebook.stetho.Stetho;
import com.rowland.movies.R;

import butterknife.Bind;

/**
 * Created by Oti Rowland on 12/20/2015.
 */
public class BaseToolBarActivity extends AppCompatActivity {

    // Class Variables
    private final String LOG_TAG = BaseToolBarActivity.class.getSimpleName();
    // The toolbar

    // ButterKnife injected Views
    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Derived classes have acess to this method
    protected void setToolbar(boolean showHomeUp, boolean showTitle, int iconResource) {
        setToolbar(mToolbar, showHomeUp, showTitle,iconResource);
    }

    // Derived methods have no direct access to this class
    private void setToolbar(Toolbar mToolbar, boolean isShowHomeUp, boolean isShowTitle,int iconResource) {
        // Does the toolbar exist?
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            // Should we set up home-up button navigation?
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHomeUp);
            // Should we display the title on the toolbar?
            getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle);
            // Should we set logo to appear in toolbar?
            getSupportActionBar().setIcon(iconResource);
            //this.mToolbar.setLogo(R.drawable.ic_logo_48px);
        }
    }
    // Network monitoring using facebook's lethal Stetho
    protected void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

}