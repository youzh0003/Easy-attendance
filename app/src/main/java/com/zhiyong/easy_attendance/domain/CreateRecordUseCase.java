package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.RecordReporsitory;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class CreateRecordUseCase extends CompletableUseCase<CreateRecordUseCase.Params> {
    private final RecordReporsitory recordReporsitory;

    @Inject
    CreateRecordUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, RecordReporsitory recordReporsitory) {
        super(threadExecutor, postExecutionThread);
        this.recordReporsitory = recordReporsitory;
    }

    @Override
    Completable buildUseCaseObservable(CreateRecordUseCase.Params params) {
        return recordReporsitory.createRecord(params.record);
    }

    public static class Params{
        private Record record;

        public Params(Record record) {
            this.record = record;
        }
    }
}

