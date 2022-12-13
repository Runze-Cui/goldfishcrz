package com.example.goldfish;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * file：MyItemDecoration
 * describe：RecycleView /
 */
public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public RecyclerViewDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
    }

}
