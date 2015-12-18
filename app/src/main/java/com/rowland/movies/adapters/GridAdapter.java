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

package com.rowland.movies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rowland.movies.R;
import com.rowland.movies.rest.pojos.Movies;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>{

    // A list of the movie items
    private List<Movies> movieList = new ArrayList<>();

    public GridAdapter(ArrayList<Movies> mMovieLists) {
        this.movieList = mMovieLists;
    }

    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout to inflate for ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_column, parent, false);
        // Return new new ViewHolder
        return new ViewHolder(v);
    }
    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Movies movies = movieList.get(position);

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    // Takes care of the overhead of recycling and gives better performance and scrolling
    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.grid_sort_type_text_view)
        TextView mSortTypeValueTextView;

        @Bind(R.id.grid_release_date_text_view)
        TextView mReleaseDateTextView;

        @Bind(R.id.grid_poster_image_view)
        ImageView mMovieImageView;

        @Bind(R.id.grid_sort_type_image_view)
        ImageView mSortTypeIconImageView;

        @Bind(R.id.grid_title_container)
        RelativeLayout mMovieTitleContainer;

        @Bind(R.id.grid_container)
        FrameLayout mGridItemContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
