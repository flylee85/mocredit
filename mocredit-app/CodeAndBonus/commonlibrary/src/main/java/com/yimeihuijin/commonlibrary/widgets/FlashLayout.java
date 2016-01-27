package com.yimeihuijin.commonlibrary.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.yimeihuijin.commonlibrary.R;

/**
 * Created by Chanson on 2016/1/4.
 */
public class FlashLayout extends LinearLayout implements FlashButton.ParentDrawNotify{

    private Scroller scroller;
    private int[] location;
    private int cx,cy;
    private Paint paint;

    public FlashLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        int  color = getResources().getColor(R.color.theme_color1);
        paint.setColor(color);
    }

    @Override
    public void notifyParent(int cx, int cy, int radius) {
        location = new int[2];
        getLocationOnScreen(location);
        this.cx = cx;
        this.cy = cy;
        scroller.startScroll(0,0,radius,radius,200);
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(scroller.computeScrollOffset()) {
            canvas.drawCircle(cx - location[0], cy - location[1], scroller.getCurrX(), paint);
        }else{
            canvas.drawARGB(0,0,0,0);
        }
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            postInvalidate();
        }
    }
}
