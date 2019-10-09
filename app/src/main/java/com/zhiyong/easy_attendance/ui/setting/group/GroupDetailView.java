package com.zhiyong.easy_attendance.ui.setting.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.base.BaseDialogFragment;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

import static com.zhiyong.easy_attendance.consts.Consts.ARG_GROUP;

public class GroupDetailView extends BaseDialogFragment implements GroupDetailMvpView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Inject
    GroupDetailPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private Group group;

    public static GroupDetailView newInstance(Group group) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_GROUP, group);
        GroupDetailView fragment = new GroupDetailView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            group = arguments.getParcelable(ARG_GROUP);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_detail, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        presenter.attachView(this);
        compositeDisposable = new CompositeDisposable();
        return view;
    }

    @Override
    protected void setupDialogTitle() {
        if(group != null){
            btnRegister.setText(getString(R.string.update));
            etName.setText(group.getName());
            etName.setEnabled(false);// Not allow to edit of group name(Primary key)
            if(group.getRemark() != null) etRemark.setText(group.getRemark());
        }
    }

    @OnClick({R.id.btn_cancel, R.id.btn_register})
    void onViewClick(View v){
        switch (v.getId()){
            case R.id.btn_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.btn_register:
                registerGroup();
                break;
        }
    }

    private void registerGroup() {
        if(group == null){// Create new
            if(!AppUtils.validateString(etName.getText().toString())){
                etName.setError(getString(R.string.error_name_empty));
                return;
            }
            // Check if the name already exist
            if(presenter.isGroupNameExist(etName.getText().toString())){
                etName.setError(getString(R.string.error_group_exist));
                return;
            }

            String remark = etRemark.getText().toString();
            presenter.createNewGroup(etName.getText().toString(), remark);
        }else {// Update exisiting
            String remark = etRemark.getText().toString();
            group.setRemark(remark);
            presenter.updateGroup(group);
        }
    }

    @Override
    public void createGroupSuccess() {
        AppUtils.showToast(getContext(), getString(R.string.created));
        getActivity().onBackPressed();
    }

    @Override
    public void createGroupFail() {
        AppUtils.showToast(getContext(), getString(R.string.message_create_group_failed));
    }

    @Override
    public void updateGroupSuccess() {
        AppUtils.showToast(getContext(), getString(R.string.updated));
        getActivity().onBackPressed();
    }

    @Override
    public void updateGroupFail() {
        AppUtils.showToast(getContext(), getString(R.string.message_update_group_failed));
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
}
