package com.zhiyong.myapp.ui.qr;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.Result;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyong.myapp.R;
import com.zhiyong.myapp.base.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

public class QRCodeRecognitionFragment extends BaseDialogFragment implements QRCodeRecognitionMvpView,
        ZXingScannerView.ResultHandler {
    private static final int DEFAULT_CAMERA_ID = 0;
    RxPermissions rxPermissions;
    Disposable disposable;
    @Inject
    QRCodeRecognitionPresenter qrCodeRecognitionPresenter;
    @BindView(R.id.zx_scanner_view)
    ZXingScannerView zxScannerView;
    private int cameraId = DEFAULT_CAMERA_ID;

    public static QRCodeRecognitionFragment newInstance() {
        Bundle args = new Bundle();
        QRCodeRecognitionFragment qrCodeRecognitionFragment = new QRCodeRecognitionFragment();
        qrCodeRecognitionFragment.setArguments(args);
        return qrCodeRecognitionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrcode_recognition, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);

        rxPermissions = new RxPermissions(getActivity());
        qrCodeRecognitionPresenter.attachView(this);
        disposable = rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        zxScannerView.startCamera(cameraId);
                        zxScannerView.setResultHandler(this);
                    } else {
                        showAlertDialog(getString(R.string.request_camera_permission));
                    }
                }, throwable -> Timber.e(throwable, "unable to request permission"));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        zxScannerView.stopCamera();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public void handleResult(Result rawResult) {
        if (getParentFragment() != null) {
            zxScannerView.startCamera();
            zxScannerView.setResultHandler(this);
        }
    }
}
