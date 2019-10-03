package com.zhiyong.myapp.ui.qr;

import com.zhiyong.myapp.base.BasePresenter;

import javax.inject.Inject;

public class QRCodeRecognitionPresenter extends BasePresenter<QRCodeRecognitionMvpView> {

    @Inject
    QRCodeRecognitionPresenter() {

    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
