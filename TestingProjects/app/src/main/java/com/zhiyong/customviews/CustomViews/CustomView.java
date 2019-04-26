package com.zhiyong.customviews.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Random;

/**
 * Created by Zhiyong on 2/4/2019.
 */

public class CustomView extends BaseCustomTextView {
    private Paint mPaint;
    private String mText = "text";
    private int textSize = 30;
    private float rx;
    private RectF rectF = new RectF(0,textSize,100,160);
    private int swipeAngle = 0;
    private Random random = new Random();

    public CustomView(Context context) {
        super(context);
        if(mPaint == null){
            mPaint = new Paint();
            mPaint .setTextSize(textSize);
        }
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if(mPaint == null){
            mPaint = new Paint();
            mPaint .setTextSize(textSize);
        }
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    protected void onDrawSub(Canvas canvas) {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        mPaint.setARGB(255,r,g,b);

        canvas.drawText(mText,rx, 30, mPaint);
        canvas.drawArc(rectF, 0, swipeAngle,true, mPaint);
    }

    @Override
    protected void logic()  {
        rx += 5;
        swipeAngle++;

        if(rx > getWidth()){
            rx = 0 - mPaint.measureText(mText);
        }
        if(swipeAngle >360){
            swipeAngle = 0;
        }
    }
}
