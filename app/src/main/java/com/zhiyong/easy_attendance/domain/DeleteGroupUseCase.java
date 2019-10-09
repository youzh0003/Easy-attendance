package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.GroupRepository;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteGroupUseCase extends CompletableUseCase<DeleteGroupUseCase.Params> {
    private final GroupRepository groupRepository;

    @Inject
    DeleteGroupUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, GroupRepository groupRepository) {
        super(threadExecutor, postExecutionThread);
        this.groupRepository = groupRepository;
    }

    @Override
    Completable buildUseCaseObservable(Params params) {
        return groupRepository.deleteGroup(params.group);
    }

    public static class Params{
        private Group group;

        public Params(Group group) {
            this.group = group;
        }
    }
}
