package com.hddevelopers.mumineenshop.Helpers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space*2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space/2;
        outRect.right = space/2;
        outRect.bottom = space / 2;

        if (parent.getChildLayoutPosition(view) == 0 ||parent.getChildLayoutPosition(view) == 1) {
            outRect.top = space / 2;
        } else {
            outRect.top = 0;
        }

        if(parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.right = space/2;
        }

        if(parent.getChildLayoutPosition(view) % 2 != 0){
            outRect.left = 0;
        }
    }
}