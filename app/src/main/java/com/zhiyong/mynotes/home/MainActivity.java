package com.zhiyong.mynotes.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhiyong.mynotes.R;
import com.zhiyong.mynotes.base.BaseDialogActivity;
import javax.inject.Inject;

public class MainActivity extends BaseDialogActivity implements MainMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getmActivityComponent().inject(this);
    }
}
