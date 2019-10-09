package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.RecordReporsitory;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetRecordsUseCase extends FlowableUseCase<List<Record>, GetRecordsUseCase.Params> {
    private final RecordReporsitory recordReporsitory;

    @Inject
    GetRecordsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, RecordReporsitory recordReporsitory) {
        super(threadExecutor, postExecutionThread);
        this.recordReporsitory = recordReporsitory;
    }

    @Override
    Flowable<List<Record>> buildUseCaseObservable(GetRecordsUseCase.Params params) {
        return recordReporsitory.getRecordList();
    }

    public static class Params{
        public Params() {
        }
    }
}
