package com.zhiyong.easy_attendance.ui.setting.group;

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
import com.zhiyong.easy_attendance.data.models.Group;
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

public class GroupView extends BaseDialogFragment implements GroupMvpView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_print)
    ImageView iv_print;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;

    @Inject
    GroupPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private GroupAdapter adapter;
    private List<Group> groupList;

    public static GroupView newInstance() {
        Bundle args = new Bundle();
        GroupView fragment = new GroupView();
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
        View view = inflater.inflate(R.layout.fragment_group_view, container, false);
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
        compositeDisposable.add(adapter.getOnGroupClick().subscribe(group -> {
            replaceFragment(GroupDetailView.newInstance(group), R.id.container, DEFAULT_ANIMATION, true);
        }));

        compositeDisposable.add(adapter.getOnGroupDelete().subscribe(group -> {
            presenter.deleteGroup(group);
        }));

        compositeDisposable.add(RxTextView.textChanges(etSearch)
        .subscribe(charSequence -> {
            if(!AppUtils.validateString(charSequence.toString())){
                if(groupList == null) groupList = new ArrayList<>();
                adapter.setList(groupList);
            }else {
                filterGroups(charSequence.toString());
            }
        }));
    }

    private void filterGroups(String query) {
        Observable.fromIterable(groupList)
                .filter(group -> group.getName().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(new DisposableSingleObserver<List<Group>>() {
                    @Override
                    public void onSuccess(List<Group> groups) {
                        adapter.setList(groups);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadData() {
        presenter.getGroups();
    }

    private void setupViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new GroupAdapter();
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);
    }

    @Override
    protected void setupDialogTitle() {
        tvTitle.setText(getString(R.string.groups));
        iv_print.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.iv_add})
    void onViewClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.iv_add:
                replaceFragment(GroupDetailView.newInstance(null), R.id.container, DEFAULT_ANIMATION, true);
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
    public void getGroupsSuccess(List<Group> groups) {
        this.groupList = groups;
        if(groups != null){
            adapter.setList(groups);
        }
    }

    @Override
    public void getGroupsFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_get_groups_failed));
    }

    @Override
    public void deleteGroupsSuccess(String name) {
        AppUtils.showToast(getContext(), getString(R.string.deleted));
        adapter.deleteItem(name);
    }

    @Override
    public void deleteGroupsFailed() {
        AppUtils.showToast(getContext(), getString(R.string.message_delete_group_failed));
    }
}
