package com.qianfeng.qiongyou.Sortlistview;
//Download by http://www.codefans.net
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 右侧字母表，快速定位
 *
 * @author zuo
 * @email 782287169@qq.com
 */
public class QuickLocationRightTool extends View
{
    private String[] b = null;

    public void setB(String[] b)
    {
        this.b = b;
    }

    OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    int choose = -1;
    Paint paint = new Paint();
    boolean showBkg = false;

    public QuickLocationRightTool(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public QuickLocationRightTool(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public QuickLocationRightTool(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (showBkg) {
           // canvas.drawColor(Color.parseColor("#10000000"));
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            if (i == choose) {
                //paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            paint.setTextSize(30);
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);    //字母位置

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != c && listener != null) {
                    if (c > 0 && c <= b.length) { //如果第一个字母是#，无效点击的话，条件变为c>0
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;                  //处理重复
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c > 0 && c <= b.length) {  //如果第一个字母是#，无效点击的话，条件变为c>0
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener)
    {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener
    {
        public void onTouchingLetterChanged(String s);
    }

}