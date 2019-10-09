package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.UserRepository;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteUserUseCase extends CompletableUseCase<DeleteUserUseCase.Params> {
    private final UserRepository userRepository;

    @Inject
    DeleteUserUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Completable buildUseCaseObservable(DeleteUserUseCase.Params params) {
        return userRepository.deleteUser(params.person);
    }

    public static class Params{
        private Person person;

        public Params(Person person) {
            this.person = person;
        }
    }
}
