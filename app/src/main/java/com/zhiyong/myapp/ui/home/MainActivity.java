package com.zhiyong.myapp.ui.home;

import android.os.Bundle;

import com.zhiyong.myapp.R;
import com.zhiyong.myapp.base.BaseDialogActivity;

public class MainActivity extends BaseDialogActivity implements MainMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getmActivityComponent().inject(this);
    }
}
