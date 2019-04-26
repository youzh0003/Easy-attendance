package com.zhiyong.customviews.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Zhiyong on 2/4/2019.
 */

public abstract class BaseCustomTextView extends View {
    private MyThread thread;
    private boolean running = true;

    public BaseCustomTextView(Context context) {
        super(context);
    }

    public BaseCustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        running = false;
        super.onDetachedFromWindow();
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        if(thread == null){
            thread = new MyThread();
            thread.start();
        }else {
            onDrawSub(canvas);
        }
    }

    protected abstract void onDrawSub(Canvas canvas);

    private class MyThread extends Thread{
        @Override
        public void run() {
            while (running){
                logic();
                postInvalidate();
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract void logic();
}
