package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.GroupRepository;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetGroupsUseCase  extends FlowableUseCase<List<Group>, GetGroupsUseCase.Params> {
    private final GroupRepository groupRepository;

    @Inject
    GetGroupsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GroupRepository groupRepository) {
        super(threadExecutor, postExecutionThread);
        this.groupRepository = groupRepository;
    }

    @Override
    Flowable<List<Group>> buildUseCaseObservable(Params params) {
        return groupRepository.getGroupList();
    }

    public boolean isGroupExist(String groupName){
        return groupRepository.isGroupExist(groupName);
    }

    public static class Params{
        public Params() {
        }
    }
}
