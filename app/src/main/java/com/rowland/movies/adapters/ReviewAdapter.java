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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rowland.movies.R;
import com.rowland.movies.rest.models.Review;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Oti Rowland on 12/30/2015.
 */
public class ReviewAdapter extends BaseAdapter {

    // A Context instance
    private Context context;
    // The list of menu items
    private List<Review> mReviewList;
    // The parent layout
    private LinearLayout mReviewLinearLayout;

    // Default constructor
    public ReviewAdapter(Context context, List<Review> reviewList, LinearLayout reviewLinearLayout) {
        this.context = context;
        this.mReviewList = reviewList;
        this.mReviewLinearLayout = reviewLinearLayout;
    }

    // Get the view at the position
    public View getView(int position, View convertView, ViewGroup parent) {
        // Don't operate on method parameter create new variable
        View reviewView = convertView;
        // Get menu at position
        Review review = mReviewList.get(position);
        // Unique view tag
        String tag = review.getAuthor();
        // Check for null
        if (reviewView == null) {
            // Acquire a context from parent
            Context context = parent.getContext();
            // Acquire the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Create new view
            reviewView = inflater.inflate(R.layout.inc_review_detail, mReviewLinearLayout, false);
        } else {
            // Get tag from given view
            String viewTag = (String) reviewView.getTag();
            // Check if tag matches our code
            if (viewTag != null && viewTag.equals(tag)) {
                // Return View
                return reviewView;
            }
        }
        //Otherwise the view is newly inflated
        ViewHolder viewHolder = new ViewHolder(reviewView);
        // Bind the data to the view holder
        viewHolder.bindTo(review);
        // Add the view to parent
        mReviewLinearLayout.addView(reviewView);
        // Set tag to new view
        reviewView.setTag(tag);
        // Return the view
        return reviewView;
    }


    // ----------------------------------------
    //  Implemented
    // ----------------------------------------
    @Override
    public Object getItem(int index) {
        return mReviewList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mReviewList.size();
    }

    // Handy method for passing the list to the adapter
    public void addAll(List<Review> reviewList) {
        // Check for  null
        if (reviewList != null) {
            // Check for null
            if (mReviewList == null) {
                // Create a new instance
                mReviewList = new ArrayList<>();
            }
            // Add each movie to the initial list
            for (Review review : reviewList) {
                // Add movies
                mReviewList.add(review);
            }

        }
        // Notify others of the data changes
    }

    static class ViewHolder {
        // The icon
        @Bind(R.id.review_author_text_view)
        TextView mAuthorTextView;
        @Bind(R.id.review_content_text_view)
        TextView mContentTextView;

        public ViewHolder(View itemView) {
            // Instantiate all the views
            ButterKnife.bind(this, itemView);
        }
        // Bind the data to the holder views
        private void bindTo(final Review review) {
            // Set the row icons
            mAuthorTextView.setText(review.getAuthor());
            mContentTextView.setText(review.getContent());
        }
    }
}