package com.zhiyong.easy_attendance.ui.setting.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.base.BaseDialogFragment;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.utils.AppUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

import static com.zhiyong.easy_attendance.consts.Consts.ARG_PERSON;

public class UserDetailView extends BaseDialogFragment implements UserDetailMvpView {
    private static final String TAG = "UserDetailView";

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.spinner_group)
    Spinner spinnerGroup;

    @Inject
    UserDetailPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private Person person;
    private GroupSpinnerAdapter spinnerAdapter;

    public static UserDetailView newInstance(Person person) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_PERSON, person);
        UserDetailView fragment = new UserDetailView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            person = arguments.getParcelable(ARG_PERSON);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
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
        loadData();
    }

    private void loadData() {
        loadGroups();
    }

    private void loadGroups() {
        presenter.getGroups();
    }

    private void setupViews() {
    }

    private void setupGroupSpinner(List<Group> groups) {
        spinnerAdapter = new GroupSpinnerAdapter(getContext(), R.layout.item_group_spinner, groups);
        spinnerGroup.setAdapter(spinnerAdapter);
        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void setupDialogTitle() {
        if(person != null){
            btnRegister.setText(getString(R.string.update));
            etName.setText(person.getName());
            etId.setText(person.getPersonalId());
            etId.setEnabled(false);// Not allow to edit of personal id(Primary key)
        }
    }

    @OnClick({R.id.btn_cancel, R.id.btn_register})
    void onViewClick(View v){
        switch (v.getId()){
            case R.id.btn_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        if(!AppUtils.validateString(etName.getText().toString())){
            etName.setError(getString(R.string.error_name_empty));
            return;
        }

        if(person == null){// Create new
            if(!AppUtils.validateString(etId.getText().toString())){
                etName.setError(getString(R.string.error_id_empty));
                return;
            }

            // Check if the name already exist
            if(presenter.isUserIdExist(etId.getText().toString())){
                etId.setError(getString(R.string.error_use_exist));
                return;
            }

            presenter.createNewUser(etName.getText().toString(), etId.getText().toString(), (Group) spinnerGroup.getSelectedItem());
        }else {// Update exisiting
            presenter.updateUser(etName.getText().toString(), person.getPersonalId(), (Group) spinnerGroup.getSelectedItem());
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
    public void getGroupsSuccess(List<Group> groups) {
        setupGroupSpinner(groups);
        if(person != null){
            int position = spinnerAdapter.getPosition(person.getGroup());
            if(position >= 0)  spinnerGroup.setSelection(position);
        }
    }

    @Override
    public void getGroupsFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_get_groups_failed));
    }

    @Override
    public void createUserSuccess() {
        AppUtils.showToast(getContext(), getString(R.string.created));
        getActivity().onBackPressed();
    }

    @Override
    public void createUserFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_create_user_failed));
    }

    @Override
    public void updateUserSuccess() {
        AppUtils.showToast(getContext(), getString(R.string.updated));
        getActivity().onBackPressed();
    }

    @Override
    public void updateUserFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_update_user_failed));
    }
}
