package com.zhiyong.myapp.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhiyong.myapp.R;
import com.zhiyong.myapp.base.BaseDialogActivity;
import com.zhiyong.myapp.ui.qr.QRCodeRecognitionFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseDialogActivity implements MainMvpView {

    private static final long ERROR_MESSAGE_TIME_OUT_MS = 5000;

    @BindView(R.id.tv_error_message)
    TextView tvErrorMessage;

    @Inject
    MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        presenter.attachView(this);
        replaceFragment(QRCodeRecognitionFragment.newInstance(), R.id.container, IN_LEFT_OUT_RIGHT, false);
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
