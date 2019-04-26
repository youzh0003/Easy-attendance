package com.zhiyong.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhiyong.customviews.CustomViews.BaseCustomTextView;
import com.zhiyong.customviews.CustomViews.CustomView;

public class MainActivity extends AppCompatActivity {
    LinearLayout llParent;
    CustomView tvMarquee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llParent = findViewById(R.id.ll_parent);

        //set up marquee text view
        tvMarquee = new CustomView(this);
        tvMarquee.setmText("My text");
        tvMarquee.setTextSize(50);
        tvMarquee.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        llParent.addView(tvMarquee);
    }
}
