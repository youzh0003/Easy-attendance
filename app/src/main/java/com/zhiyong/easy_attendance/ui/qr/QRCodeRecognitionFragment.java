package com.zhiyong.easy_attendance.ui.qr;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.Result;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.base.BaseDialogFragment;
import com.zhiyong.easy_attendance.consts.Consts;
import com.zhiyong.easy_attendance.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

public class QRCodeRecognitionFragment extends BaseDialogFragment implements QRCodeRecognitionMvpView,
        ZXingScannerView.ResultHandler {

    @BindView(R.id.rl_record)
    RelativeLayout rlRecord;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private static final int DEFAULT_CAMERA_ID = 0;
    RxPermissions rxPermissions;
    Disposable disposable;
    @Inject
    QRCodeRecognitionPresenter qrCodeRecognitionPresenter;
    @BindView(R.id.zx_scanner_view)
    ZXingScannerView zxScannerView;
    private int cameraId = DEFAULT_CAMERA_ID;
    String timezone;

    long tempTimeStap;
    String tempName;

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
        timezone = Calendar.getInstance().getTimeZone().toString();
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
        Date date = DateUtils.getCurrentTime(timezone);
        tempTimeStap = date.getTime()/1000;
        String dateString = DateUtils.formatDateTime(tempTimeStap, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS_U, timezone);
        tempName = rawResult.getText();

        rlRecord.setVisibility(View.VISIBLE);
        tvName.setText(tempName);
        tvDate.setText(dateString);
    }
    @OnClick({R.id.btn_in, R.id.btn_out})
    void onViewClick(View v){
        switch (v.getId()) {
            case R.id.btn_in:
                postRecordCreated();
                break;
            case R.id.btn_out:
                postRecordCreated();
                break;
        }
    }

    private void postRecordCreated() {
        rlRecord.setVisibility(View.GONE);
        tvName.setText(Consts.Empty);
        tvDate.setText(Consts.Empty);
        zxScannerView.startCamera();
        zxScannerView.setResultHandler(this);
    }
}
