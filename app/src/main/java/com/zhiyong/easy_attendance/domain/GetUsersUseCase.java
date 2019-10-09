package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.UserRepository;
import com.zhiyong.easy_attendance.data.models.Person;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetUsersUseCase extends FlowableUseCase<List<Person>, GetUsersUseCase.Params> {
    private final UserRepository userRepository;

    @Inject
    GetUsersUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Flowable<List<Person>> buildUseCaseObservable(GetUsersUseCase.Params params) {
        return userRepository.getUserList();
    }

    public boolean isUserIdExist(String id){
        return userRepository.isUserExist(id);
    }

    public static class Params{
        public Params() {
        }
    }
}
