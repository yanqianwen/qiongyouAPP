package com.qianfeng.qiongyou.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/3/23.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        if (parent.getChildPosition(view) != parent.getChildCount() - 1) {
            outRect.bottom = space;
        }
        if (parent.getChildPosition(view) != 0) {
            outRect.top = space;
        }
    }
}
