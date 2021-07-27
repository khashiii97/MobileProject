package com.example.finalproject.Utils;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ZoomRecyclerLayout extends LinearLayoutManager {


    private float mShrinkAmount = 0.15f;
    private float mShrinkDistance = 0.9f;

    public ZoomRecyclerLayout(Context context) {
        super(context);
    }

    public ZoomRecyclerLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if(orientation == RecyclerView.HORIZONTAL){
            int scrolled = super.scrollHorizontallyBy(dx,recycler,state);
            float midpoint = getWidth() / 2f ;
            float d0 = 0f;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1f;
            float s1 = 1f - mShrinkAmount;
            for(int i = 0; i < getChildCount();i ++){
                View child = getChildAt(i);
                if (child != null){
                    float childmidpoint = (getDecoratedRight(child) +getDecoratedLeft(child)) /2f;
                    float temp = Math.abs(midpoint - childmidpoint);
                    float d;
                    if (d1 > temp)
                        d = temp;
                    else
                        d = d1;
                    float scale = s0 + (s1 - s0) * (d - d0)/(d1 - d0);
                    child.setScaleX(scale);
                    child.setScaleY(scale);

                }

            }
        return scrolled;
        }
        else
            return 0;

    }
}

