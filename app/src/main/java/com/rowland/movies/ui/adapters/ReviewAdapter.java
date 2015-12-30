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

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rowland.movies.BuildConfig;
import com.rowland.movies.R;
import com.rowland.movies.rest.models.Review;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/31/2015.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CustomViewHolder> {

    // The class Log identifier
    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();
    // A list of the movie items
    private List<Review> mReviewList;

    // Default constructor
    public ReviewAdapter(List<Review> reviewList) {
        this.mReviewList = reviewList;
    }

    // Called when RecyclerView needs a new CustomViewHolder of the given type to represent an item.
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Layout to inflate for CustomViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inc_review_detail, parent, false);
        // Return new new CustomViewHolder
        return new CustomViewHolder(v);
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // Acquire Review item at this position
        final Review review = mReviewList.get(position);
        // Bind the data to the view holder
        holder.bindTo(review);
    }

    // What's the size of the movie List
    @Override
    public int getItemCount() {
        // Check size of List first
        if (mReviewList != null) {
            // Check wether we are in debug mode
            if (BuildConfig.IS_DEBUG_MODE) {
                Log.d(LOG_TAG, "List Count: " + mReviewList.size());
            }
            return mReviewList.size();
        }
        return 0;
    }

    // Handy method for passing the list to the adapter
    public void addAll(List<Review> reviewList) {

        if (reviewList != null) {
            // Check for null
            if (mReviewList == null) {
                // Create a new instance
                mReviewList = new ArrayList<>();
            }
            // Assign
            mReviewList = reviewList;
        }

    }

    // Takes care of the overhead of recycling and gives better performance and scrolling
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.review_author_text_view)
        TextView mReviewAuthorTextView;

        @Bind(R.id.review_content_text_view)
        TextView mReviewContentTextView;


        public CustomViewHolder(View itemView) {
            super(itemView);
            // Initialize the views
            ButterKnife.bind(this, itemView);
        }

        // Bind the data to the holder views
        private void bindTo(final Review review) {
            // Set the review's author
            mReviewAuthorTextView.setText(review.getAuthor());
            // Set the review's content
            mReviewContentTextView.setText(review.getContent());

        }
    }
}
