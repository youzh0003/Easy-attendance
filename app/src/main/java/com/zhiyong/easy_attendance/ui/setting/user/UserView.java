package com.zhiyong.easy_attendance.ui.setting.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.base.BaseDialogFragment;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

import static com.zhiyong.easy_attendance.consts.Consts.DEFAULT_ANIMATION;

public class UserView extends BaseDialogFragment implements UserMvpView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_print)
    ImageView iv_print;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;

    @Inject
    UserPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private UserAdapter adapter;
    private List<Person> userList;

    public static UserView newInstance() {
        Bundle args = new Bundle();
        UserView fragment = new UserView();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_users_view, container, false);
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

    private void setupListeners() {
        compositeDisposable.add(adapter.getOnUserClick().subscribe(user -> {
            replaceFragment(UserDetailView.newInstance(user), R.id.container, DEFAULT_ANIMATION, true);
        }));

        compositeDisposable.add(adapter.getOnUserDelete().subscribe(user -> {
            presenter.deleteUser(user);
        }));

        compositeDisposable.add(RxTextView.textChanges(etSearch)
                .subscribe(charSequence -> {
                    if(!AppUtils.validateString(charSequence.toString())){
                        if(userList == null) userList = new ArrayList<>();
                        adapter.setList(userList);
                    }else {
                        filterGroups(charSequence.toString());
                    }
                }));
    }

    private void filterGroups(String query) {
        Observable.fromIterable(userList)
                .filter(group -> group.getName().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(new DisposableSingleObserver<List<Person>>() {
                    @Override
                    public void onSuccess(List<Person> users) {
                        adapter.setList(users);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadData() {
        presenter.getUsers();
    }

    private void setupViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new UserAdapter();
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);
    }

    @Override
    protected void setupDialogTitle() {
        tvTitle.setText(getString(R.string.users));
        iv_print.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.iv_add})
    void onViewClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.iv_add:
                replaceFragment(UserDetailView.newInstance(null), R.id.container, DEFAULT_ANIMATION, true);
                break;
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
    public void getUsersSuccess(List<Person> users) {
        this.userList = users;
        if(this.userList != null) adapter.setList(userList);
    }

    @Override
    public void getUsersFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_get_uses_failed));
    }
}
