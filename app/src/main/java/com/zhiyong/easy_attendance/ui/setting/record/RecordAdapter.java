package com.zhiyong.easy_attendance.ui.setting.record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.data.models.Record;
import com.zhiyong.easy_attendance.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.processors.PublishProcessor;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private PublishProcessor<Record> onRecordClick;
    private PublishProcessor<Record> onRecordDelete;
    private ViewBinderHelper viewBinderHelper;
    private List<Record> recordList;

    public RecordAdapter() {
        this.onRecordClick = PublishProcessor.create();
        this.onRecordDelete = PublishProcessor.create();
        this.viewBinderHelper = new ViewBinderHelper();
        this.viewBinderHelper.setOpenOnlyOne(true);
    }

    public PublishProcessor<Record> getOnRecordDelete() {
        return onRecordDelete;
    }

    public PublishProcessor<Record> getOnRecordClick() {
        return onRecordClick;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.bindData(recordList.get(position));
    }

    @Override
    public int getItemCount() {
        if (recordList == null) return 0;
        return recordList.size();
    }

    public void setList(List<Record> recordList) {
        this.recordList = recordList;
        notifyDataSetChanged();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe_reveal_layout)
        SwipeRevealLayout swipeRevealLayout;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;

        RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Record record) {
            if (record.getPerson() != null && record.getPerson().getName()!= null) tvName.setText(record.getPerson().getName());
            if (record.getPerson() != null && record.getPerson().getGroup()!= null &&
            record.getPerson().getGroup().getName() != null) tvGroupName.setText(record.getPerson().getGroup().getName());
            tvTime.setText(DateUtils.formatDateTime(record.getRecordedAt(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS_U, "Asia/Singapore"));
            if(record.getType() != null) tvType.setText(record.getType());
            llItem.setOnClickListener(view -> {
                onRecordClick.onNext(record);
            });
            llDelete.setOnClickListener(view -> {
                onRecordDelete.onNext(record);
            });
            viewBinderHelper.bind(swipeRevealLayout, String.valueOf(record.getRecordedAt()));
        }
    }
}
