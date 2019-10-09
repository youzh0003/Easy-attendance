package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.UserRepository;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class CreateUserUseCase extends CompletableUseCase<CreateUserUseCase.Params> {
    private final UserRepository userRepository;

    @Inject
    CreateUserUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Completable buildUseCaseObservable(Params params) {
        return userRepository.createUser(params.name, params.id, params.group);
    }

    public static class Params{
        private String name;
        private String id;
        private Group group;

        public Params(String name, String id, Group group) {
            this.name = name;
            this.id = id;
            this.group = group;
        }
    }
}
