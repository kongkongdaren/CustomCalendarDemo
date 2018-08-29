package com.software.wen.customcalendardemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wen on 2018/8/27.
 */

public class CalendarItemTextView extends TextView {

    public boolean isToday=false;
    private Paint mPaint=new Paint();
    public CalendarItemTextView(Context context) {
        super(context);
    }

    public CalendarItemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }
    public CalendarItemTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }
    private void initControl() {
       mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth((float)2.6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday){
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,mPaint);
        }
    }
}
