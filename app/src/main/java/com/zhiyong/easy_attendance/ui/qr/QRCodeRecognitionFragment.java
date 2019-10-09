package com.zhiyong.easy_attendance.ui.qr;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.google.zxing.Result;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.base.BaseDialogFragment;
import com.zhiyong.easy_attendance.consts.Consts;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.ui.setting.group.GroupView;
import com.zhiyong.easy_attendance.ui.setting.record.RecordView;
import com.zhiyong.easy_attendance.ui.setting.user.UserView;
import com.zhiyong.easy_attendance.utils.AppUtils;
import com.zhiyong.easy_attendance.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

import static com.zhiyong.easy_attendance.consts.Consts.DEFAULT_ANIMATION;
import static com.zhiyong.easy_attendance.utils.DateUtils.PATTERN_REPORT;

public class QRCodeRecognitionFragment extends BaseDialogFragment implements QRCodeRecognitionMvpView,
        ZXingScannerView.ResultHandler {

    @BindView(R.id.rl_record)
    RelativeLayout rlRecord;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_group)
    TextView tvGroup;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;

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
    Person tempUser;
    private List<Person> userList;

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
        qrCodeRecognitionPresenter.getUsers();
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
        ivBack.setVisibility(View.INVISIBLE);
        ivMenu.setVisibility(View.VISIBLE);
        tvTitle.setText(getResources().getString(R.string.app_title));
    }

    @Override
    public void handleResult(Result rawResult) {
        // rawResult MUST be same as the person's id
        if(this.userList == null || this.userList.size() <= 0){
            AppUtils.showToast(getContext(), getString(R.string.message_unknow_user_detected));
            zxScannerView.startCamera();
            zxScannerView.setResultHandler(this);
            return;
        }
        // Time
        Date date = DateUtils.getCurrentTime(timezone);
        tempTimeStap = date.getTime()/1000;
        String dateString = DateUtils.formatDateTime(tempTimeStap, DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS_U, timezone);
        
        // Person
        boolean hasFound = false;
        for (Person p:userList
             ) {
            if(p.getPersonalId().equalsIgnoreCase(rawResult.getText())){
                tempUser = p;
                hasFound = true;
                break;
            }
        }
        if(hasFound){
            rlRecord.setVisibility(View.VISIBLE);
            if(tempUser.getName() != null) tvName.setText(tempUser.getName());
            if(tempUser != null && tempUser.getGroup()!= null && tempUser.getGroup().getName() != null) tvGroup.setText(tempUser.getGroup().getName());
            tvDate.setText(dateString);
        }else {
            zxScannerView.startCamera();
            zxScannerView.setResultHandler(this);
            AppUtils.showToast(getContext(), getString(R.string.message_unknow_user_detected));
        }
    }
    @OnClick({R.id.btn_in, R.id.btn_out})
    void onViewClick(View v){
        switch (v.getId()) {
            case R.id.btn_in:
                Record recordIn = new Record(tempTimeStap,DateUtils.formatDateTime(tempTimeStap, PATTERN_REPORT, timezone), tempUser, getString(R.string.in));
                qrCodeRecognitionPresenter.saveRecord(recordIn);
                postRecordCreated();
                break;
            case R.id.btn_out:
                Record recordOut = new Record(tempTimeStap,DateUtils.formatDateTime(tempTimeStap, PATTERN_REPORT, timezone), tempUser, getString(R.string.out));
                qrCodeRecognitionPresenter.saveRecord(recordOut);
                postRecordCreated();
                break;
        }
    }

    @OnClick(R.id.iv_menu)
    void didTapMenu() {
        PopupMenu menu = new PopupMenu(getContext(), getActivity().findViewById(R.id.iv_menu));
        menu.getMenuInflater().inflate(R.menu.admin_actions, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_groups:
                        replaceFragment(GroupView.newInstance(), R.id.container, DEFAULT_ANIMATION, true);
                        return true;
                    case R.id.menu_item_users:
                        replaceFragment(UserView.newInstance(), R.id.container, DEFAULT_ANIMATION, true);
                        return true;
                    case R.id.menu_item_records:
                        replaceFragment(RecordView.newInstance(), R.id.container, DEFAULT_ANIMATION, true);
                        return true;
                    default:
                        return false;
                }
            }
        });
        menu.show();
    }

    private void postRecordCreated() {
        rlRecord.setVisibility(View.GONE);
        tvName.setText(Consts.Empty);
        tvDate.setText(Consts.Empty);
        zxScannerView.startCamera();
        zxScannerView.setResultHandler(this);
    }

    @Override
    public void getUsersSuccess(List<Person> people) {
        this.userList = people;
    }

    @Override
    public void getUsersFailed() {
        AppUtils.showToast(getContext(), "Get users failed");
    }

    @Override
    public void createRecordSuccess() {
        AppUtils.showToast(getContext(), "Recorded !");
    }

    @Override
    public void createRecordFailed() {
        AppUtils.showToast(getContext(), "Create record failed");
    }
}
