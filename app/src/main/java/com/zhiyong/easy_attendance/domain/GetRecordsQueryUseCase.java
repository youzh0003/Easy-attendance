package com.zhiyong.easy_attendance.domain;

import com.zhiyong.easy_attendance.data.RecordReporsitory;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.executor.PostExecutionThread;
import com.zhiyong.easy_attendance.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetRecordsQueryUseCase extends FlowableUseCase<List<Record>, GetRecordsQueryUseCase.Params> {
    private final RecordReporsitory recordReporsitory;

    @Inject
    GetRecordsQueryUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, RecordReporsitory recordReporsitory) {
        super(threadExecutor, postExecutionThread);
        this.recordReporsitory = recordReporsitory;
    }

    @Override
    Flowable<List<Record>> buildUseCaseObservable(GetRecordsQueryUseCase.Params params) {
        return recordReporsitory.getRecordListQuery(params.date, params.groupName);
    }

    public static class Params{
        private String date;
        private String groupName;

        public Params(String date, String groupName) {
            this.date = date;
            this.groupName = groupName;
        }
    }
}
