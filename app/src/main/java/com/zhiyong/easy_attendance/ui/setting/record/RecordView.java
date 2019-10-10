package com.zhiyong.easy_attendance.ui.setting.record;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.base.BaseDialogFragment;
import com.zhiyong.easy_attendance.consts.Consts;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.utils.AppUtils;
import com.zhiyong.easy_attendance.utils.CsvUtils;
import com.zhiyong.easy_attendance.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

import static com.zhiyong.easy_attendance.utils.DateUtils.PATTERN_REPORT;

public class RecordView extends BaseDialogFragment implements RecordMvpView {

    public  static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_date_filter)
    TextView tvDateFilter;
    @BindView(R.id.tv_group_filter)
    TextView tvGroupFilter;
    
    @Inject
    RecordPresenter presenter;
    private CompositeDisposable compositeDisposable;

    private List<Record> recordList;
    private RecordAdapter adapter;
    private String timezone;

    /*Dialog group filter*/
    private Dialog dialogGroups;
    private RecyclerView rvGroups;
    private ChooseGroupAdapter chooseGroupAdapter;

    public static RecordView newInstance() {
        Bundle args = new Bundle();
        RecordView fragment = new RecordView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
        }
        timezone = TimeZone.getDefault().getDisplayName();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_view, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        presenter.attachView(this);
        compositeDisposable = new CompositeDisposable();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
        setupListeners();
        loadData();
    }

    private void loadData() {
        presenter.getGroups();
//        presenter.getRecords();
    }

    private void setupListeners() {
        compositeDisposable.add(adapter.getOnRecordClick().subscribe(record -> {
//            replaceFragment(RecordDetailView.newInstance(record), R.id.container, DEFAULT_ANIMATION, true);
        }));

        compositeDisposable.add(adapter.getOnRecordDelete().subscribe(record -> {
            presenter.deleteRecord(record);
        }));

        compositeDisposable.add(RxTextView.textChanges(etSearch)
                .subscribe(charSequence -> {
                    if(!AppUtils.validateString(charSequence.toString())){
                        if(recordList == null) recordList = new ArrayList<>();
                        adapter.setList(recordList);
                    }else {
                        searchFilterRecords(charSequence.toString());
                    }
                }));

        compositeDisposable.add(RxTextView.textChanges(tvDateFilter)
                .subscribe(charSequence -> {
                    filterRecords(charSequence.toString(), tvGroupFilter.getText().toString());
                }));

        compositeDisposable.add(RxTextView.textChanges(tvGroupFilter)
                .subscribe(charSequence -> {
                    filterRecords(tvDateFilter.getText().toString(), charSequence.toString());
                }));
    }

    private void filterRecords(String date, String groupName) {
        presenter.getRecords(date, groupName);
    }

    private void searchFilterRecords(String query) {
        Observable.fromIterable(recordList)
                .filter(group -> group.getPerson().getName().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(new DisposableSingleObserver<List<Record>>() {
                    @Override
                    public void onSuccess(List<Record> users) {
                        adapter.setList(users);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void setupViews() {
        setCurrentDate();
        setupRecyclerView();
        setupGroupFilterView();
    }

    private void setCurrentDate() {
        tvDateFilter.setText(DateUtils.formatDateTime(Calendar.getInstance().getTimeInMillis()/1000, PATTERN_REPORT, timezone));
    }

    private void setupGroupFilterView() {
        /*init dialog group*/
        dialogGroups = AppUtils.initDialog(dialogGroups, R.layout.popup_units, getActivity());
        chooseGroupAdapter = new ChooseGroupAdapter();
        rvGroups = dialogGroups.findViewById(R.id.recycler_view);
        rvGroups.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvGroups.setAdapter(chooseGroupAdapter);
        compositeDisposable.add(chooseGroupAdapter.getOnClickChooseGroup().subscribe(group -> {
            setCurrentGroup(group);
            dialogGroups.dismiss();
        }));
    }

    private void setCurrentGroup(Group group) {
        if (group.getName() != null) {
            tvGroupFilter.setText(group.getName());
        }
        chooseGroupAdapter.refreshData(group);
    }

    private void setupRecyclerView() {
        adapter = new RecordAdapter();
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);
    }

    @Override
    protected void setupDialogTitle() {
        ivAdd.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.records));
    }

    @OnClick({R.id.iv_back, R.id.iv_print,R.id.tv_date_filter,R.id.iv_clear,R.id.tv_group_filter})
    void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.iv_print:
                checkPermission();
                break;
            case R.id.tv_date_filter:
                String date = Consts.Empty;
                if(AppUtils.validateString(tvDateFilter.getText().toString())) date = tvDateFilter.getText().toString();
                else date = DateUtils.formatDateTime(Calendar.getInstance().getTimeInMillis()/1000, PATTERN_REPORT, timezone);
                AppUtils.showCalendar(getActivity(), date, stringReturn -> {
                    tvDateFilter.setText(stringReturn);
                });
                break;
            case R.id.iv_clear:
                tvDateFilter.setText(Consts.Empty);
                break;
            case R.id.tv_group_filter:
                dialogGroups.show();
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }else {
            printCsv();
        }
    }

    private void printCsv() {
        try {
            CsvUtils.writeRecordsToCsv(recordList, tvDateFilter.getText().toString(), timezone);
            AppUtils.showToast(getContext(), getString(R.string.message_report_generated_success));
        }catch (IOException e){
            e.printStackTrace();
            AppUtils.showToast(getContext(), getString(R.string.report_generated_failed));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                printCsv();
            } else {
                AppUtils.showToast(getContext(), getString(R.string.message_permission_denied));
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detachView();
        }
        compositeDisposable.clear();
        AppUtils.hideKeyboard(getActivity());
        super.onDestroyView();
    }

    @Override
    public void getRecordSuccess(List<Record> records) {
        this.recordList = records;
        adapter.setList(recordList);
    }

    @Override
    public void getRecordsFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_get_records_failed));
    }

    @Override
    public void getGroupsSuccess(List<Group> groups) {
        if(groups == null || groups.size() <=0) return;
        chooseGroupAdapter.setData(groups);
        setCurrentGroup(groups.get(0));
    }

    @Override
    public void getGroupsFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_get_groups_failed));
    }
}
