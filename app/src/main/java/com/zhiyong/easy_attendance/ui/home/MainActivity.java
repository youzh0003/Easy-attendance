package com.zhiyong.easy_attendance.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.base.BaseDialogActivity;
import com.zhiyong.easy_attendance.ui.qr.QRCodeRecognitionFragment;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zhiyong.easy_attendance.consts.Consts.DEFAULT_ANIMATION;

public class MainActivity extends BaseDialogActivity implements MainMvpView {

    private static final long ERROR_MESSAGE_TIME_OUT_MS = 5000;

    @BindView(R.id.tv_error_message)
    TextView tvErrorMessage;

    @Inject
    MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        presenter.attachView(this);
        replaceFragment(QRCodeRecognitionFragment.newInstance(), R.id.container, DEFAULT_ANIMATION, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setLanguage() {

        //读取SharedPreferences数据，默认选中第一项
        SharedPreferences preferences = getSharedPreferences("language", Context.MODE_PRIVATE);
        int language = preferences.getInt("language", 0);

        //根据读取到的数据，进行设置
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();

        switch (language) {
            case 0:
                configuration.setLocale(Locale.getDefault());
                break;
            case 1:
                configuration.setLocale(Locale.ENGLISH);
                break;
            case 2:
                configuration.setLocale(Locale.CHINESE);
                break;
            case 3:
                configuration.setLocale(Locale.JAPANESE);
                break;
            default:
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @Override
    protected void setupDialogTitle() {
    }

    public void showErrorMessage(int messageResId) {
        tvErrorMessage.setText(messageResId);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.postDelayed(() -> tvErrorMessage.setVisibility(View.GONE), ERROR_MESSAGE_TIME_OUT_MS);
    }

    public void showErrorMessage(String message) {
        tvErrorMessage.setText(message);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.postDelayed(() -> tvErrorMessage.setVisibility(View.GONE), ERROR_MESSAGE_TIME_OUT_MS);
    }
}
