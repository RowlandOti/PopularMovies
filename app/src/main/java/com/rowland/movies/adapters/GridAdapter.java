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

import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rowland.movies.R;
import com.rowland.movies.enums.EBaseImageSize;
import com.rowland.movies.enums.EBaseURlTypes;
import com.rowland.movies.rest.pojos.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/18/2015.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CustomViewHolder>{

    // A list of the movie items
    private List<Movies> movieList = new ArrayList<>();
    // A Calendar object to help in formatting time
    private Calendar mCalendar;

    public GridAdapter(ArrayList<Movies> mMovieLists) {
        this.movieList = mMovieLists;
    }

    // Called when RecyclerView needs a new CustomViewHolder of the given type to represent an item.
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout to inflate for CustomViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_column, parent, false);
        // Return new new CustomViewHolder
        return new CustomViewHolder(v);
    }
    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // Movie item at this position
        final Movies movie = movieList.get(position);

        holder.mGridItemContainer.setContentDescription(holder.mGridItemContainer.getContext().getString(R.string.movie_title, movie.getOriginalTitle()));

        if (movie.getReleaseDate() != null) {
            mCalendar.setTime(movie.getReleaseDate());
            holder.mReleaseDateTextView.setText(String.valueOf(mCalendar.get(Calendar.YEAR)));
            holder.mReleaseDateTextView.setContentDescription(holder.mReleaseDateTextView.getContext().getString(R.string.movie_year, String.valueOf(mCalendar.get(Calendar.YEAR))));
        }


        String imageUrl = EBaseURlTypes.MOVIE_API_IMAGE_BASE_URL.getUrlType() + EBaseImageSize.IMAGE_SIZE_W185.getImageSize() + movie.getPosterPath();
        final RelativeLayout container = holder.mMovieTitleContainer;

        Picasso.with(holder.mMovieImageView.getContext()).load(imageUrl).placeholder(R.drawable.ic_movie_placeholder).
                into(holder.mMovieImageView);

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    // Takes care of the overhead of recycling and gives better performance and scrolling
    public class CustomViewHolder extends RecyclerView.ViewHolder {

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

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
