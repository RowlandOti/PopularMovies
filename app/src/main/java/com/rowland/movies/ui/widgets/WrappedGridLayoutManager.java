/*
* Copyright 2015 David Cesarino de Sousa
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.rowland.movies.ui.widgets;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.MeasureSpec.UNSPECIFIED;

/**
 * <p>Provides a usable {@link GridLayoutManager} for {@link RecyclerView}, handling
 * {@code wrap_content} layouts properly.</p>
 *
 * @author davidcesarino@gmail.com
 * @version 2015.1210
 * @see <a href="https://gist.github.com/davidcesarino/7e85b5eee82307c06e08">This on Github</a>
 * @see <a href="https://gist.github.com/c0nnector/5f80e19d9ba6d562fbd5">Original on Github</a>
 * @see <a href="https://code.google.com/p/android/issues/detail?id=74772">Issue 74772</a>
 */
public class WrappedGridLayoutManager extends GridLayoutManager {

    public WrappedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                          int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        final int addIndex = getOrientation() == VERTICAL ? 1 : 0;
        final int maxIndex = getOrientation() == VERTICAL ? 0 : 1;

        int[] childMeasure = new int[2];
        int[] rowMeasure = {0, 0};
        int[] rowsSized = {0, 0};

        for (int i = 0; i < state.getItemCount(); i++) {
            // Measure the child.
            measureScrapChild(recycler, i, UNSPECIFIED, UNSPECIFIED, childMeasure);

            // Computes the row, with "row" according to orientation.
            //
            // TO WHOEVER READS THIS ON THE INTERNET:
            //
            // Pay attention to this. The reason why I used max calculations becomes pretty
            // obvious when you realize you may have rows in which the later items are taller
            // (when VERTICAL, or wider on HORIZONTAL) then the first item in the row. Some
            // approaches I've seen on the Internet only account for the size of the first
            // item in the row, which is why everything can appear cropped if the mentioned
            // scenario happens.
            //
            // As of December 08, I didn't have enough opportunity to test or improve this,
            // so if you have something to share, please do. If you can also contact me,
            // I'd be especially glad.
            if (getOrientation() == VERTICAL) {
                rowMeasure[1] = Math.max(rowMeasure[1], childMeasure[1]);
                rowMeasure[0] += childMeasure[0];
            } else {
                rowMeasure[0] = Math.max(rowMeasure[0], childMeasure[0]);
                rowMeasure[1] += childMeasure[1];
            }

            // When finishing the row (last item of row), adds the row dimensions to the view.
            if (i % getSpanCount() == (getSpanCount() - 1)) {
                rowsSized[addIndex] += rowMeasure[addIndex];
                rowsSized[maxIndex] = Math.max(rowsSized[maxIndex], rowMeasure[maxIndex]);
                rowMeasure[addIndex] = 0;
                rowMeasure[maxIndex] = 0;
            }
        }
        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                rowsSized[0] = widthSize;
            case View.MeasureSpec.AT_MOST:
            case UNSPECIFIED:
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                rowsSized[1] = heightSize;
            case View.MeasureSpec.AT_MOST:
            case UNSPECIFIED:
        }

        setMeasuredDimension(rowsSized[0], rowsSized[1]);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                   int heightSpec, int[] measuredDimension) {
        // See code at getViewForPosition(int, boolean) to see if/how this may be problematic.
        View view = recycler.getViewForPosition(position);
        if (view != null) {
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                    getPaddingLeft() + getPaddingRight(), p.width);
            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                    getPaddingTop() + getPaddingBottom(), p.height);
            view.measure(childWidthSpec, childHeightSpec);
            measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
            measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
            recycler.recycleView(view);
        }
    }
}