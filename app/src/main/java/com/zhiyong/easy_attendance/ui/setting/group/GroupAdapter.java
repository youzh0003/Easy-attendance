package com.zhiyong.easy_attendance.ui.setting.group;

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
import com.zhiyong.easy_attendance.data.models.Group;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.processors.PublishProcessor;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private PublishProcessor<Group> onGroupClick;
    private PublishProcessor<Group> onGroupDelete;
    private ViewBinderHelper viewBinderHelper;
    private List<Group> groupList;

    public GroupAdapter() {
        this.onGroupClick = PublishProcessor.create();
        this.onGroupDelete = PublishProcessor.create();
        this.viewBinderHelper = new ViewBinderHelper();
        this.viewBinderHelper.setOpenOnlyOne(true);
    }

    public PublishProcessor<Group> getOnGroupDelete() {
        return onGroupDelete;
    }

    public PublishProcessor<Group> getOnGroupClick() {
        return onGroupClick;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bindData(groupList.get(position));
    }

    @Override
    public int getItemCount() {
        if(groupList == null) return 0;
        return groupList.size();
    }

    public void setList(List<Group> groups) {
        this.groupList = groups;
        notifyDataSetChanged();
    }

    public void deleteItem(String name) {
        if(groupList == null) return;
        Iterator<Group> iterator = groupList.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getName().equalsIgnoreCase(name)) {
                iterator.remove();
                break;
            }
        }
        notifyDataSetChanged();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.swipe_reveal_layout)
        SwipeRevealLayout swipeRevealLayout;
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;

        GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Group group) {
            if(group.getName() != null) tvGroupName.setText(group.getName());
            tvGroupName.setOnClickListener(view -> {
                onGroupClick.onNext(group);
            });
            llDelete.setOnClickListener(view -> {
                onGroupDelete.onNext(group);
            });
            viewBinderHelper.bind(swipeRevealLayout, group.getName());
        }
    }
}
