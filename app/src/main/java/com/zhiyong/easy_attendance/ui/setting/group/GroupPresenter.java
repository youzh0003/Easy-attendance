package com.zhiyong.easy_attendance.ui.setting.group;

import com.zhiyong.easy_attendance.base.BasePresenter;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.domain.DeleteGroupUseCase;
import com.zhiyong.easy_attendance.domain.GetGroupsUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class GroupPresenter extends BasePresenter<GroupMvpView> {
    private final GetGroupsUseCase getGroupsUseCase;
    private final DeleteGroupUseCase deleteGroupUseCase;

    @Inject
    public GroupPresenter(GetGroupsUseCase getGroupsUseCase,
                          DeleteGroupUseCase deleteGroupUseCase) {
        this.getGroupsUseCase = getGroupsUseCase;
        this.deleteGroupUseCase = deleteGroupUseCase;
    }

    void getGroups() {
        getGroupsUseCase.execute(new DisposableSubscriber<List<Group>>() {
            @Override
            public void onNext(List<Group> groups) {
                if(getMvpView() != null){
                    getMvpView().getGroupsSuccess(groups);
                }
            }

            @Override
            public void onError(Throwable t) {
                if(getMvpView() != null){
                    getMvpView().getGroupsFailed();
                }
            }

            @Override
            public void onComplete() {

            }
        },new GetGroupsUseCase.Params());
    }

    void deleteGroup(Group group) {
        deleteGroupUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                if(getMvpView() != null){
                    getMvpView().deleteGroupsSuccess(group.getName());
                }
            }

            @Override
            public void onError(Throwable e) {
                if(getMvpView() != null){
                    getMvpView().deleteGroupsFailed();
                }
            }
        }, new DeleteGroupUseCase.Params(group));
    }

    @Override
    public void detachView() {
        super.detachView();
        getGroupsUseCase.dispose();
        deleteGroupUseCase.dispose();
    }
}
