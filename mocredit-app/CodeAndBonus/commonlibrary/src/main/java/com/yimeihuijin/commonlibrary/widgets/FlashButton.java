package com.yimeihuijin.commonlibrary.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Scroller;

import com.yimeihuijin.commonlibrary.R;

/**
 * Created by Chanson on 2016/1/4.
 */
public class FlashButton extends Button {

    private Scroller scroller;
    private LinearInterpolator interpolator;
    private Paint paint;
    private int[] location;
    private ParentDrawNotify parentDrawNotify;

    public void setParentView(ParentDrawNotify parentView){
        this.parentDrawNotify = parentView;
    }

    public FlashButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        interpolator = new LinearInterpolator();
        scroller = new Scroller(context,interpolator);
        paint = new Paint();
        paint.setAntiAlias(true);
        int colorRes = attrs.getAttributeIntValue(android.R.attr.background,-1);
        int  color = getResources().getColor(R.color.theme_color1);
        if(color != -1) {
            paint.setColor(color);
        }else{
            paint.setColor(Color.LTGRAY);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(parentDrawNotify != null){
            super.onDraw(canvas);
            return;
        }
        if (scroller.computeScrollOffset()) {
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, scroller.getCurrX(), paint);
        }else{
            canvas.drawARGB(0, 0, 0, 0);
        }
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(parentDrawNotify != null){
            return;
        }
        if(scroller.computeScrollOffset()) {
//            System.out.println("###### x = " + scroller.getCurrX() + "\n######y = " + scroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(parentDrawNotify != null){
                    location = new int[2];
                    getLocationOnScreen(location);
                    parentDrawNotify.notifyParent(location[0]+(getMeasuredWidth()/2),location[1]+(getMeasuredHeight()/2), (int) (getMeasuredHeight()*0.6));
                }else {
                    scroller.startScroll(0, 0, (int) (getMeasuredHeight() * 0.7), 100, 200);
                    postInvalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setFlashColor(int color){
        paint.setColor(color);
    }

    public interface ParentDrawNotify{
        public void notifyParent(int cx,int cy,int radius);
    }
}
