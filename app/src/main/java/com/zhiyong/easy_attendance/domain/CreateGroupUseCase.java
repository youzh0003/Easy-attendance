package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.GroupRepository;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class CreateGroupUseCase extends CompletableUseCase<CreateGroupUseCase.Params> {
    private final GroupRepository groupRepository;

    @Inject
    CreateGroupUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GroupRepository groupRepository) {
        super(threadExecutor, postExecutionThread);
        this.groupRepository = groupRepository;
    }

    @Override
    Completable buildUseCaseObservable(Params params) {
        return groupRepository.createGroup(params.groupName, params.remark);
    }

    public static class Params{
        private String groupName;
        private String remark;

        public Params(String groupName, String remark) {
            this.groupName = groupName;
            this.remark = remark;
        }
    }
}
