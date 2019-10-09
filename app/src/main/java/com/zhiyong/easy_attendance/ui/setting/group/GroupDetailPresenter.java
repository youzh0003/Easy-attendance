package com.zhiyong.easy_attendance.ui.setting.group;

import com.zhiyong.easy_attendance.base.BasePresenter;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.domain.CreateGroupUseCase;
import com.zhiyong.easy_attendance.domain.GetGroupsUseCase;
import com.zhiyong.easy_attendance.domain.UpdateGroupUseCase;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

public class GroupDetailPresenter extends BasePresenter<GroupDetailMvpView> {
    private final GetGroupsUseCase getGroupsUseCase;
    private final CreateGroupUseCase createGroupUseCase;
    private final UpdateGroupUseCase updateGroupUseCase;

    @Inject
    public GroupDetailPresenter(GetGroupsUseCase getGroupsUseCase,
                                CreateGroupUseCase createGroupUseCase,
                                UpdateGroupUseCase updateGroupUseCase) {
        this.getGroupsUseCase = getGroupsUseCase;
        this.createGroupUseCase = createGroupUseCase;
        this.updateGroupUseCase = updateGroupUseCase;
    }

    boolean isGroupNameExist(String groupName) {
        return getGroupsUseCase.isGroupExist(groupName);
    }

    void createNewGroup(String groupName, String remark) {
        createGroupUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                if(getMvpView() != null){
                    getMvpView().createGroupSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(getMvpView() != null){
                    getMvpView().createGroupFail();
                }
            }
        }, new CreateGroupUseCase.Params(groupName, remark));
    }

    void updateGroup(Group group) {
        updateGroupUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                if(getMvpView() != null){
                    getMvpView().updateGroupSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(getMvpView() != null){
                    getMvpView().updateGroupFail();
                }
            }
        }, new UpdateGroupUseCase.Params(group));
    }

    @Override
    public void detachView() {
        super.detachView();
        getGroupsUseCase.dispose();
        createGroupUseCase.dispose();
        updateGroupUseCase.dispose();
    }
}
