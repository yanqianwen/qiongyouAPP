package com.qianfeng.qiongyou.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qianfeng.qiongyou.R;

/**
 * Created by aaa on 16-3-31.
 */
public class RecycleItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int mItemSize = 30 ;
    private Paint mPaint;
    private int mOrientation ;

    public RecycleItemDecoration(Context context,int mOrientation ){
        this.context = context;
        this.mOrientation = mOrientation;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(context.getResources().getColor(R.color.colorGray));
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.drawRect(0,0,parent.getMeasuredWidth(),parent.getMeasuredHeight(),mPaint);
        c.drawRect(mItemSize,mItemSize,parent.getMeasuredWidth()-mItemSize,parent.getMeasuredHeight()-mItemSize,mPaint);
        if(mOrientation == LinearLayoutManager.VERTICAL){
            drawVertical(c,parent) ;
        }else {
            drawHorizontal(c,parent) ;
        }
    }


    /**
     * 画竖线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int top = child.getTop();
            int bottom = top ;
            c.drawRect(left,top,right,bottom,mPaint);
        }
    }

    /**
     * 画横线
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom =parent.getMeasuredHeight() - parent.getPaddingBottom();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.bottomMargin;
            int right = left + mItemSize;
            c.drawRect(left,top,right,bottom,mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == OrientationHelper.HORIZONTAL){
                outRect.set(0, 0, 0, mItemSize);
            }else {
                outRect.set(0,0,mItemSize,0);
            }

    }
}
