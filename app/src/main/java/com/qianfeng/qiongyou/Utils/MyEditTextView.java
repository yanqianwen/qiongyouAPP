package com.qianfeng.qiongyou.Utils;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

import com.qianfeng.qiongyou.R;


/**
 * Created by aaa on 16-3-31.
 */
public class MyEditTextView extends EditText{
    public MyEditTextView(Context context) {
        super(context);
    }

    public MyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorGray));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        int leftX = 80;
        int rightX = getMeasuredWidth()-leftX;

        RectF oval1 = new RectF(40,40,120,120);
        RectF oval2 = new RectF(rightX-40,40,rightX+40,120);

        canvas.drawArc(oval1, 90, 180, false, mPaint);

        canvas.drawArc(oval2, -90, 180, false, mPaint);

        canvas.drawRect(leftX, leftX - 40, rightX, 120, mPaint);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_search_left);
//        int bitW = bitmap.getWidth();
//        canvas.drawBitmap(bitmap, (rightX - leftX) / 2, 60, null);
//        mPaint.setTextSize(50);
//        canvas.drawText("搜索国家/城市",(rightX-leftX)/2+bitW,60,mPaint);

        Path path = new Path();
        Path path1 = new Path();
        path.moveTo(leftX, 40);
        path.lineTo(rightX, 40);
//        path.close();
        path1.moveTo(leftX, 120);
        path1.lineTo(rightX, 120);
//        path1.close();
        canvas.drawPath(path, mPaint);
        canvas.drawPath(path1,mPaint);
    }
}
